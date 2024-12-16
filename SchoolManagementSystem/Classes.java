import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Classes extends SchoolSystem {
    public static String classes = "classes.csv";

    public void addClass(String filename, String classID, String courseName, String teacherID, String teachersName, String location, String startTime, String endTime, String numberOfStudents, List<String> students) throws IOException {
        // Read the current content of the file
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        // Find the position of the first completely empty row
        int emptyRowIndex = -1;
        for (int i = 0; i < lines.size(); i++) {
            String[] details = lines.get(i).split(",");
            boolean isEmptyRow = details.length < 8 || Arrays.stream(details).allMatch(String::isBlank);
            if (isEmptyRow) {
                emptyRowIndex = i;
                break;
            }
        }

        // Append the new class data at the identified position
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < lines.size(); i++) {
                if (i == emptyRowIndex) {
                    // Write the class details
                    writer.append(String.join(",", classID, courseName, teacherID, teachersName, location, startTime, endTime, numberOfStudents));
                    writer.append("\n");
                    // Write each student in the subsequent rows under the 7th column
                    for (String student : students) {
                        writer.append(",,,,,,,").append(student);
                        writer.append("\n");
                    }
                } else {
                    writer.append(lines.get(i));
                    writer.append("\n");
                }
            }

            // If no empty row is found, append at the end
            if (emptyRowIndex == -1) {
                writer.append(String.join(",", classID, courseName, teacherID, teachersName, location, startTime, endTime, numberOfStudents));
                writer.append("\n");
                for (String student : students) {
                    writer.append(",,,,,,,").append(student);
                    writer.append("\n");
                }
            }
        }
    }

    public String getLocation(String filename, String id) throws IOException {

        String location = null;

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while((line = reader.readLine()) != null) {

                String [] details = line.split(",");

                if(details[0].equals(id)) {

                    location = details[4];

                }

            }

        }

        return location;
    }

    public int getTime(String filename, String type, String ID) throws IOException {
        int time = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while((line = reader.readLine()) != null) {

                String [] details = line.split(",");

                if(details[0].equals(ID)) {

                if(type.equals("hour")) {

                    String [] hourPiece = details[5].split(":");
                    time = Integer.parseInt(hourPiece[0]);



                } else if(type.equals("minute")) {

                    String [] minutePiece = details[5].split(":");
                    time = Integer.parseInt(minutePiece[1]);
                }
 
            }
           
        }

        }
        return time;

    }

    public static boolean CheckTime(String timeOfAppoitment) {

        if(timeOfAppoitment.contains(":")) {

                            String [] timeOfAppointmentSeperate = timeOfAppoitment.split(":");
                        
                            if(timeOfAppointmentSeperate.length == 2) {
                            try{
                                int Hour = Integer.parseInt(timeOfAppointmentSeperate[0]);
                                int Minute = Integer.parseInt(timeOfAppointmentSeperate[1]);

                                if(Hour >= 0 && Hour <= 23 && Minute >= 0 && Minute <= 59) {

                                    return true;

                                } else{
                                        return false;

                                }
                                
                            } catch(NumberFormatException e) {

                                    return false;

                                }

                            } else {
                                        return false;
                        } 
                            
                    } else{
                                return false;

            }
        }

    public void clasessMenu(Scanner scanner) throws IOException{
        int startHour = 0;
        int startMinute = 0;
        int endHour = 0;
        int endMinute = 0;
        boolean loop = true;
        Random rand = new Random();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("CourseName", 1);
        map.put("TeachersID", 2);
        map.put("TeachersName", 3);
        map.put("Location", 4);
        map.put("StartTime", 5);
        map.put("EndTime", 6);
        map.put("NumberOfStudents", 7);
        int NewHour = 0;
        int NewMinute = 0;
        String newTime = null;
        FinancialAdvisor advisor = new FinancialAdvisor();
        String startTime = null;
        String endTime = null;
        String checkTheStartTime = null;
        String checkTheEndTime = null;


        while(loop) {

        System.out.println("Please select: ");
        System.out.println();
        System.out.println("1) Add a class");
        System.out.println("2) Remove a class");
        System.out.println("3) Update a class");
        System.out.println("4) View a class");
        System.out.println("5) View all classes");

        String choice = scanner.nextLine();

        switch(choice){
            
            case "1":
                      System.out.println("Please enter the name of the course this class will a part of: ");
                      String courseName = scanner.nextLine();
                      System.out.println();

                      System.out.println("Please enter the Id of the teacher assigned to it:  ");
                      String teachersID = scanner.nextLine();
                      System.out.println();
                      System.out.println("For student's simplicity, please enter the name of said teacher: ");
                      String teachersName = scanner.nextLine();
                      System.out.println();
                      System.out.println("Now please enter the location of said ");
                      String location = scanner.nextLine();
                      System.out.println();

                
                while(true){

                      while(true) {
                        try{
                            System.out.println();
                            System.out.println("Start Time of class in HH:MM: ");
                            String timeOfClass = scanner.nextLine().trim(); 
                            checkTheStartTime = timeOfClass;
                            boolean CheckTheTime = CheckTime(timeOfClass);

                            if(CheckTheTime) {

                                String [] timeOfClassSeperate = timeOfClass.split(":");
                                int Hour = Integer.parseInt(timeOfClassSeperate[0]);
                                int Minute = Integer.parseInt(timeOfClassSeperate[1]);
                                startHour = Hour;
                                startMinute = Minute;
                                break;

                            } else{
                                System.out.println("Please enter a valid hour/minute, making sure it is in the format HH:mm");
                                    continue;
                                }

                            } catch(InputMismatchException e) {}
                    }

                    System.out.println();

                    while(true) {
                        try{
                            System.out.println();
                            System.out.println("End Time of class in HH:MM: ");
                            String timeOfClass = scanner.nextLine().trim(); 
                            checkTheEndTime = timeOfClass; 
                            boolean CheckTheTime = CheckTime(timeOfClass);

                            if(CheckTheTime) {

                                String [] timeOfClassSeperate = timeOfClass.split(":");
                                int Hour2 = Integer.parseInt(timeOfClassSeperate[0]);
                                int Minute2 = Integer.parseInt(timeOfClassSeperate[1]);
                                endHour = Hour2;
                                endMinute = Minute2;
                                break;

                            } else{

                                System.out.println("Please enter a valid hour/minute, making sure it is in the format HH:mm");
                                    continue;
                                }

                            } catch(InputMismatchException e) {}
                    }

                    if(!checkOverlap(classes, location, startHour, startMinute, endHour, endMinute)) {
                        
                        startTime = String.format("%02d:%02d", startHour, startMinute);
                        endTime = String.format("%02d:%02d", endHour, endMinute);

                        System.out.println("This time is available thanks.");
                        break;

                    } else {

                        System.out.println("Please enter a valid time. This time slot at " + location + "has already been taken.");
                    }

                }


                    System.out.println();
                    System.out.println("Now please enter the ID of the student's present in the class: type 'done' when you are finished: ");
                    System.out.println();
                    List<String>students = new ArrayList<>();

                    while(true) {

                    System.out.println("ID: ");
                    String studentID = scanner.nextLine();
                    System.out.println("");

                    if(!studentID.equalsIgnoreCase("done")) {

                        students.add(studentID);

                    } else { System.out.println();
                                break;
                        }
                   
                    }

                    int numOfStu = calculateNumberOfStudents(students);

                    String numberOfStudents = Integer.toString(numOfStu);

                    int id = rand.nextInt(0,15000);
                    String classID = Integer.toString(id);

                    addClass(classes, classID, courseName, teachersID, teachersName, location, startTime, endTime , numberOfStudents, students);
                    System.out.println();
                    System.out.println("Thank you it has been added");
                    break;



                case "2":
                          System.out.println("Please enter the ID of the class: ");
                          String ClassID = scanner.nextLine();
                          System.out.println();
                          remove(classes, ClassID);
                          break;

                case "3":

                        System.out.println("Please enter the ID of the class you would like to update: ");
                          String classId = scanner.nextLine();
                          if(!(advisor.validateID(classes, classId))) {
                            System.out.println("Please enter a valid id!");
                            System.out.println();
                            break;
                          }
                          System.out.println();
                    while(true) {
                          String Location = getLocation(classes, classId);
                          boolean time = false;
                          boolean student = false;
                          System.out.println();
                          System.out.println("Please enter the infomation you would like to update, inclues:");
                          System.out.println("CourseName, TeachersID, TeachersName, Location, StartTime, EndTime, numberOfStudents");
                          System.out.println("Additionally: type 'student' to add/remove a student from said class");
                          System.out.println("When you are done please type 'done' ");
                          String toChange = scanner.nextLine();

                          if(toChange.equalsIgnoreCase("done")) {break;}

                          if(toChange.equalsIgnoreCase("student")) {
                            student = true;

                            System.out.println("1) Would you like to add a student? ");
                            System.out.println("2) Would you like to remove a student?");

                            System.out.println("Choice: ");
                            int Choice = scanner.nextInt();
                            scanner.nextLine();

                            if(Choice == 1) {   

                                System.out.println("Please enter the ID of the student you would like to add: ");
                                String studentIDToAdd = scanner.nextLine();
                                addOrRemoveSingularStudent(classes, classId, studentIDToAdd, "add");

                            } else if(Choice == 2) {

                                System.out.println("Please enter the ID of the student you like to remove: ");
                                String studentIDToRemove = scanner.nextLine();
                                addOrRemoveSingularStudent(classes, classId, studentIDToRemove, "remove");

                            }

                          }
                          
                          if(toChange.equalsIgnoreCase("StartTime") || toChange.equalsIgnoreCase("EndTime")) {
                                time = true;

                                System.out.println("Please type hour or minute or both: ");
                                String digit = scanner.nextLine();
                                if(digit.equalsIgnoreCase("hour")) {

                                    System.out.println("Please enter the new hour");
                                    int newHour = scanner.nextInt();
                                    scanner.nextLine();
                                    int minute = getTime(classes, "minute", classId);
                                    newTime = String.format("%02d:%02d", newHour, minute);
                                    if(!checkOverlap(classes, Location, startHour, startMinute, endHour, endMinute)){
                                            
                                        advisor.updateFile(classes, classId, map.get(toChange), newTime);
                                   

                                    } else {System.out.println("This time overlaps with another!");}
                                    

                                } else if (digit.equalsIgnoreCase("minute")) {

                                    System.out.println("Please enter the new minute");
                                    int newMinute = scanner.nextInt();
                                    scanner.nextLine();
                                    int hour = getTime(classes, "hour", classId);
                                    newTime = String.format("%02d:%02d", hour, newMinute);

                                    if(!checkOverlap(classes, Location, startHour, startMinute, endHour, endMinute)){
                                            
                                        advisor.updateFile(classes, classId, map.get(toChange), newTime);

                                    } else {System.out.println("This time overlaps with another!");}

                                   

                                } else if(digit.equalsIgnoreCase("both")) {

                                    while(true) {

                                    System.out.println("Please enter the new time");
                                    String NewTime = scanner.nextLine();

                                    if(CheckTime(NewTime)) {

                                    if(!checkOverlap(classes, Location, startHour, startMinute, endHour, endMinute)){

                                        newTime = NewTime;
                                        advisor.updateFile(classes, classId, map.get(toChange), newTime);
                                        break;

                                        } else {System.out.println("this time overlaps!");}

                                    } else {System.out.println("Please enter a valid time");}

                                    }

                                }

                            }

                                if(!time && !student) {
                                System.out.println();
                                System.out.println("Now please enter what you would like to change this too: ");
                                String toChangeto = scanner.nextLine();
                                int position = map.get(toChange);
                                advisor.updateFile(classes, classId, position, toChangeto);
                                System.out.println();
                                System.out.println("Thank you it has been updated!");
                                
                                }

                          
                    }
                    break;

                    case "4":
                              System.out.println("Please enter the id of the class you would like to attend: ");
                              String classtoviewID = scanner.nextLine();
                              view(classes, "classes", false, classtoviewID);
                              scanner.nextLine();
                              break;
                        
                    case "5":
                              view(classes, "classes", true, null);
                              scanner.nextLine();
                              break;
                }

            }
        }
    
}
