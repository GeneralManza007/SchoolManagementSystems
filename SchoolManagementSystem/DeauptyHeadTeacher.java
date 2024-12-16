import java.util.*;
import java.io.*;


public class DeauptyHeadTeacher extends SchoolSystem {
    private static final String deauptyHeadTeacher = "DeauptyHeadTeacher.csv";
    private static final String SchoolsToDoList = "SchoolsToDoList.csv";
    private static final String deauptyHeadTeachersToDoList = "DeauptyHeadTeacher.txt";
    private static final Courses course = new Courses();
    private static final AttendanceRecorder recorder = new AttendanceRecorder();
    private static final Student studentMenu = new Student();
    private static final Classes classes = new Classes();
    private static final Grades grade = new Grades();

    public DeauptyHeadTeacher(){
        super();
    }

    public void DeauptyHeadTeacherMenu(Scanner scanner) throws IOException{
        boolean loop = true;

        while(loop){

            try{
                
                System.out.println("| 1) Manage students     |");
                System.out.println("|                        |");
                System.out.println("| 2) Manage Courses      |");
                System.out.println("|                        |");
                System.out.println("| 3) Manage Classes      |");
                System.out.println("|                        |");
                System.out.println("| 4) View Attendences    |");
                System.out.println("|                        |");
                System.out.println("| 5) View Grades         |");
                System.out.println("|                        |");
                System.out.println("| 6) Your To DoList      |");
                System.out.println("|                        |");
                System.out.println("| 7) Schools To Do List: |");
                System.out.println("|                        |");
                System.out.println("| 8) Supervision Menu    |");
                System.out.println();

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
                            ToDoList.ToDo(deauptyHeadTeachersToDoList);
                            break;
                    case "7":
                            ToDoList.ToDo(schoolToDoList);
                            break;

                    case "8":
                            loop = false;
                            break;
                    default:
                            System.out.println("Please enter a valid choice!");
                            break;
                }
            } catch(NumberFormatException e){e.printStackTrace();}
        }
    }

    public void deauptyHeadTeacher(Scanner scanner)throws IOException{
        boolean loop = true;

        while(loop){
            try{
                System.out.println("Welcome To The Deaupty Head Page: ");
                System.out.println();
                System.out.println("Please proceed to login!");
                super.login(scanner, deauptyHeadTeacher);
                deauptyHeadTeacher(scanner);
            } catch(NumberFormatException e){}
        }
    }
}
