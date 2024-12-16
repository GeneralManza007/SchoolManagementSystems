import java.util.*;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.util.random.*;
import java.time.*;

public class HeadTeacher extends SchoolSystem {
    private static final String student = "Student.csv";
    private static final String courses = "Courses.csv";
    private static final String headTeacher = "Headteacher.csv";
    private static final Courses course = new Courses();
    private static final AttendanceRecorder recorder = new AttendanceRecorder();
    private static final Student studentMenu = new Student();
    private static final Classes classes = new Classes();
    private static final Grades grade = new Grades();
    private static final FinancialAdvisor advisor = new FinancialAdvisor();
    private static final String headteacherToDoList = "Headteacher.txt";
    private static final String SchoolsToDoList = "SchoolsToDoList.csv";

    public HeadTeacher() {
        super();
    }
    
    public void HeadTeacherMenu(Scanner scanner)throws IOException {
        boolean loop = true;

        while(loop){
            try{
                System.out.println("| 1) Manage students     |");
                System.out.println("| 2) Manage Courses      |");
                System.out.println("| 3) Manage Classes      |");
                System.out.println("| 4) View Attendences    |");
                System.out.println("| 5) View Grades         |");
                System.out.println("| 6) View Financials     |");
                System.out.println("| 7) Schools ToDo List   |");
                System.out.println("| 8) Your ToDo List      |");
                System.out.println("| 9) Go Back             |");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                            studentMenu.student(scanner);;
                            break;
                    case "2":
                            course.coursesMenu(scanner);   
                            break;
                    case "3":
                            classes.clasessMenu(scanner);
                            break;
                    case "4":
                            recorder.attendenceMenu(scanner);
                            break;
                    case "5": 
                            grade.GradesMenu(scanner);
                            break;
                    case "6":
                            advisor.manageTransactionsMenu(scanner);
                            break;
                    case "7":
                            ToDoList.ToDo(schoolToDoList);
                            break;
                    case "8":
                            ToDoList.ToDo(headteacherToDoList);
                            break;

                    case "9":
                             loop = false;
                             break;
                    default:
                            System.out.println("Please enter a valid choice!");
                            break;
                }
            } catch(NumberFormatException e){e.printStackTrace();}
        }
    }

    public void headTeacher(Scanner scanner) throws IOException {
            boolean loop = true;
    
            while(loop){
                try{
                    System.out.println("Please proceed to login!");
                    super.login(scanner, headTeacher);
                    HeadTeacherMenu(scanner);
                } catch(NumberFormatException e){}
            }
        }

}



