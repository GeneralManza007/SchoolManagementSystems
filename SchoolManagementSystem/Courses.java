import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Courses extends SchoolSystem {
    private static final String courses = "Courses.csv";
    private static final String student = "Student.csv";

    public void schedulecourse(String filename, String courseId, String startTime, String endTime, String day) throws IOException{

        try(FileWriter writer = new FileWriter(filename, true)) {

            writer.append(String.join(",", courseId, startTime, endTime, day));
            writer.append("\n");
        }

    }

    public boolean checkTeacherID(String teachersID) throws IOException {

        try(BufferedReader reader = new BufferedReader(new FileReader(teachers))) {
            String line;

            while((line = reader.readLine()) != null) {

                String [] details = line.split(",");
                
                if(details[0].equals(teachersID)) {
                    
                    return true;

                }
            }
        }

        return false;
    }

     public void addCourse(String filename, String courseId, String courseName, String teacherId)throws IOException{
        try(FileWriter writer = new FileWriter(filename, true)) {

            writer.append(String.join(",", courseId, courseName, teacherId));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     public void coursesMenu(Scanner scanner) throws IOException {
            boolean loop = true;
            String teachersId = null;;
            Random rand = new Random();
            HashMap<String, Integer> map = new HashMap<>();
            map.put("CourseName", 1);
            map.put("TeachersID", 2);
                
            while(loop){

                System.out.println("Select: ");
                System.out.println();
                System.out.println("1. Add a new course");
                System.out.println("2. Update a course");
                System.out.println("3. Remove an existing course");

                String choice = scanner.nextLine();

                switch (choice) {

                    case "1":
                             System.out.println("Please enter the courses name: ");
                             String courseName = scanner.nextLine();
                             System.out.println();
                             System.out.println("Now please enter the id of the teacher you would like to assign the course to: ");
                             while(true){

                                String teachersID = scanner.nextLine();

                                if(checkTeacherID(teachersID)) {

                                    teachersId = teachersID;
                                    break;  

                                }
                             }
                             int course = rand.nextInt(0, 15000);
                             String courseID = Integer.toString(course);
                             System.out.println();
                             addCourse(courses, courseID, courseName, teachersId);
                             System.out.println("Thank you it has been added");
                             break;
                
                    case "2":
                            System.out.println("Please enter the courseID of the course, whose infomation you would like to update: ");
                            String teachersiD = scanner.nextLine();

                            while(true){

                                System.out.println("Please enter which type of infomation you would like to change: ");
                                System.out.println("Options include: CourseName, TeachersID");
                                System.out.println("Type 'done' when you are done!");
                                String toChange = scanner.nextLine();
                                
                                if(toChange.equalsIgnoreCase("done")) {

                                    break;

                                }

                                System.out.println("Now please enter what you would like to change this too: ");
                                String toChangeTo = scanner.nextLine();

                                int position = map.get(toChange);

                                FinancialAdvisor advisor = new FinancialAdvisor();
                                advisor.updateFile(courses, teachersiD, position, toChangeTo);

                                System.out.println("It has been updated!");
                            }
                            break;

                        case "3":
                                  System.out.println("Please enter the ID of the course you would like to remove: ");
                                  System.out.println("");
                                  String id = scanner.nextLine();
                                  remove(courses, id);
                                  System.out.println("Thanks it has been removed");
                                  break;
                        
                        case "4":
                                    System.out.println("Please enter the id of the course you would like to view: ");
                                    String idCourse = scanner.nextLine();
                                    System.out.println();
                                    view(courses, "courses", false, idCourse);
                                    scanner.nextLine();
                                    break;
                        
                        case "5":
                                    view(student, "courses", true, null);
                                    scanner.nextLine();
                                    break;
                           

                    }
                }
            }
    
}
