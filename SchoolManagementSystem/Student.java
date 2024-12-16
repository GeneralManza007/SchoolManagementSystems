import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Student extends SchoolSystem {
    private static final String student = "Student.csv";

    public void enrollStudent(String filename, String studentID, String firstName, String surname, String DOB, int Age, String username, String password, String address, String phoneNumber) throws IOException{

        try(FileWriter writer = new FileWriter(filename, true)) {

            writer.append(String.join(",", studentID, firstName, surname, DOB, Integer.toString(Age), username, password, address, phoneNumber));

        } catch(IOException e){}

    }

    public List<String> getStudentsFromCourse(String filename, String ID) throws IOException {
        List<String> students = new ArrayList<>();
        boolean courseFound = false;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
    
            while ((line = reader.readLine()) != null) {
                // Split the line into columns
                String[] columns = line.split(",");
    
                // Check if the current line is the course row
                if (!courseFound) {
                    if (columns.length > 0 && columns[0].equalsIgnoreCase(ID)) {
                        courseFound = true;
                    }
                } else {
                    // Collect student IDs from the subsequent rows under the 8th column
                    if (columns.length >= 8 && !columns[7].trim().isEmpty()) {

                        students.add(columns[7].trim());
                    } else {
                        // Stop if the next course or end of file is encountered
                        break;
                    }
                }
            }
        }
        return students;
    }

    public void student(Scanner scanner)throws IOException{
        Random rand = new Random();
        boolean loop = true;
        String DOB = null;
        String Username = null;
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Firstname", 1);
        map.put("Surname", 2);
        map.put("DOB", 3);
        map.put("Username", 5);
        map.put("Password", 6);
        map.put("Address",7);
        map.put("Phonenumber", 8);


    while(loop){

            System.out.println();
            System.out.println("1) Enroll a new student");
            System.out.println("2) Update/ edit an existing students info");
            System.out.println("3) Remove an existing student's info");
            System.out.println("4) View a student");
            System.out.println("5) View all students");
            System.out.println();
            int Choice = scanner.nextInt();
            scanner.nextLine();

                switch(Choice) {

                    case 1:
                            System.out.println("To enroll a new student please enter the following infomation: ");
                            System.out.println();
                            System.out.println("FirstName: ");
                            String firstName = scanner.nextLine();
                            System.out.println();
                            System.out.println("Surname: ");
                            String surname = scanner.nextLine();
                            System.out.println();

                            while(true) {

                                System.out.println("DOB: ");
                                String dob = scanner.nextLine();

                                if(checkDate(DOB, false)) {

                                    DOB = dob;
                                    System.out.println();
                                    break;  

                                } else {

                                        System.out.println("Please enter a valid date");

                                }

                            }
                            
                            System.out.println("Enter their address: ");
                            String address = scanner.nextLine();
                            System.out.println();

                            System.out.println("Enter the phone number of the student: ");
                            String phoneNumber = scanner.nextLine();
                            System.out.println();

                            while(true) {


                            System.out.println("Now enter the username you would like them to have: ");
                            System.out.println();
                            String username = scanner.nextLine();

                            if(!checkIfUsernameExists(address, username)) {

                                Username = username;
                                System.out.println();
                                break;

                            } else {

                                System.out.println("Please enter a unique username for " + firstName);
                                }

                            } 

                            System.out.println("Please enter the students chosen password: ");
                            String password = scanner.nextLine();

                            int id = rand.nextInt(0, 150000);
                            String studentID = Integer.toString(id);
                            
                            LocalDate num = setDOB(DOB);
                            int age = ageCalculator(num);

                            System.out.println();
                            enrollStudent(student, studentID, firstName, surname, DOB, age, Username, password, address, phoneNumber);
                            System.out.println("Thank you they have been added. ");
                            break;

                    
                    case 2:
                            System.out.println("Please enter the studentID of the student you whose infomation you would like to update: ");
                            String studentsID = scanner.nextLine();

                        while(true){

                            System.out.println("Please enter which type of infomation you would like to change: ");
                            System.out.println("Options include: Firstname, Surname, DOB, Username, Password, Address, PhoneNumber");
                            System.out.println("Type 'done' when you are done!");
                            String toChange = scanner.nextLine();
                            
                            if(toChange.equalsIgnoreCase("done")) {

                                break;

                            }

                            System.out.println("Now please enter what you would like to change this too: ");
                            String toChangeTo = scanner.nextLine();

                            if(toChange.equals("DOB") && !checkDate(toChange, false)) {

                                System.out.println("Please enter a valid date:");
                                break;
                            }

                            int position = map.get(toChange);

                            FinancialAdvisor advisor = new FinancialAdvisor();
                            advisor.updateFile(student, studentsID, position, toChangeTo);

                            System.out.println("It has been updated!");
                        }
                        break;
                    
                    
                    case 3:
                            System.out.println("To remove a student please enter their StudentID: ");
                            String studentIDtoRemove = scanner.nextLine();
                            remove(student, studentIDtoRemove);
                            System.out.println();
                            System.out.println("Student has been removed! ");
                            break;
                    
                    case 4:
                            System.out.println("Please enter the id of the student you would like to view: ");
                            String viewid = scanner.nextLine();
                            System.out.println();
                            view(student, "students", false, viewid);
                            scanner.nextLine();
                            break;
                    
                    case 5:
                            view(student, "students", true, null);
                            scanner.nextLine();
                            break;
                           

                 }

            }
        }
    
}
