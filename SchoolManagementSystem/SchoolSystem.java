import java.io.*;
import java.util.*;
import javax.imageio.IIOException;

import java.time.*;
import java.time.format.DateTimeFormatter;


public class SchoolSystem{
    public static String grades = "grades.csv";
    public static String attendence ="attendence.csv";
    public static String teachers = "teachers.csv";
    public static String classes = "classes.csv";
    public static AttendanceRecorder recorder = new AttendanceRecorder();
    public static Student Student = new Student();
    private static final String student = "Student.csv";
    private static final String courses = "Courses.csv";
    private static final String headTeacher = "Headteacher.csv";
    protected static final String schoolToDoList = "SchoolsToDoList.csv";

    public SchoolSystem(){}

    public static void registerIn(String filename, String firstName,String surName, String DOB, int Age, String username, String password) throws IOException {

        if(!checkFileExists(filename)) {

            createFile(filename);

        }

        try(FileWriter writer = new FileWriter(filename, true)){

            writer.append(String.join(",", firstName, surName , DOB , Integer.toString(Age),  username, password));
        }
    }

    public static boolean checkDate(String inputDate, boolean isHyphen){

        if(isHyphen){

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        try{
                LocalDate date = LocalDate.parse(inputDate, dateFormatter);

                return true;

        } catch(DateTimeException e){

            return false;

            }

        } else {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        try{
                LocalDate date = LocalDate.parse(inputDate, dateFormatter);

                return true;

        } catch(DateTimeException e){

            return false;

            }

        }
        
    }

    public boolean checkTime(String time){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        try{
            LocalTime Time = LocalTime.parse(time, formatter);
            return true;

        } catch(DateTimeException e) {
            return false;
        }
    }

    public LocalDate setDOB(String DOB) {

        return LocalDate.parse(DOB);

    }

    public static int ageCalculator(LocalDate DOB) {

        LocalDate currentDate = LocalDate.now();

        Period period = Period.between(DOB, currentDate);

        int age = period.getYears();

        return age;
    }

    public static <T> String accessFile(String filename, String Username, int position, T element) throws IOException {

        try(Scanner scanner = new Scanner(new File(filename))) {

                while(scanner.hasNext()) {

                    String line = scanner.nextLine();
                    String [] words = line.split("; ");

                    if(words[position] == element) {

                        return words[position];
                    }

                 }
            }

            return null;
    }

    public static <T> boolean checkFile(String filename, String Username, int position, T element) throws IOException {

        try(Scanner scanner = new Scanner(new File(filename))) {

                while(scanner.hasNext()) {

                    String line = scanner.nextLine();
                    String [] words = line.split("; ");

                    if(words[position] == element) {

                        return true;
                    }

                 }
            }

            return false;
    }

    public static String getName(String filename, String Username) throws IOException {
        String Name = null;


        try(Scanner scanner = new Scanner(new File(filename))) {

            while(scanner.hasNext()) {

                String line = scanner.nextLine();
                String [] words = line.split(",");

                if(words[4].equalsIgnoreCase(Username)) {

                    Name = words[0];

                    return Name;
                }
            }
        }

        return Name;
    }



    public static void Checklogin(String filename, String Username, String password) throws IOException {

    }

    public static boolean checkFileExists(String filename) throws IOException {

        File file = new File(filename);
        return file.exists();
    }

    public static boolean checkIfFileIsEmpty(String filename) throws IOException{

        if(!checkFileExists(filename)) {
            throw new IOException();
        }
        
        try(Scanner scanner = new Scanner(new File(filename))) {
            return !scanner.hasNext();
            
        }
    }

    public static void createFile(String filename) throws IOException {

                File file = new File(filename);
                file.createNewFile();
    }

    public static boolean checkIfUsernameExists(String filename, String username) throws IOException{

        if(!checkFileExists(filename)) {createFile(filename);}

        if(checkIfFileIsEmpty(filename)) {return false;}


        try(Scanner scanner = new Scanner(new File(filename))) {

            while(scanner.hasNext()) {

                String line = scanner.nextLine();
                String [] words = line.split(",");
                System.out.println(words[4]);

                if(words.length > 4 && words[4].equalsIgnoreCase(username)) {

                    return true;
                }
            }
        }

        return false;
    }

    public static boolean checkIfPasswordExists(String filename, String Username, String password) throws IOException{

        try(Scanner scanner = new Scanner(new File(filename))) {

            while(scanner.hasNext()) {

                String line = scanner.nextLine();
                String [] words = line.split(",");

                if(words[4].equalsIgnoreCase(Username) && words[5].equalsIgnoreCase(password)) {

                    return true;

                }

            }

        }

        return false;

    }


    public void register(Scanner scanner, String filename) throws IOException{

    try{
        String Username = null;
        String DOB = null;

        System.out.println();
        System.out.println("Hi there, please register: ");
        System.out.println();
        System.out.println("Please enter your First name: ");
        System.out.println();
        System.out.print("Firstname: ");
        String firstName = scanner.next();
        System.out.println();
        System.out.println("Please enter your Surname: ");
        System.out.println();
        System.out.print("Surname: ");
        String surName = scanner.next();
        System.out.println();

            
        System.out.println("Please enter your DOB: ");
        System.out.println();

                while(true) {
                    System.out.println("DOB:");
                    String dOB = scanner.next();

                    if(checkDate(DOB, false)) {

                        DOB = dOB;
                        break;
                        
                    } 

                    System.out.println("please enter a valid date! ");
                    System.out.println();

                }

        while(true) {

                    System.out.println("Please enter your Username: ");
                    String username = scanner.nextLine();
                    System.out.println();

                    if(checkIfUsernameExists(filename, username)) {

                            System.out.println("That username is already taken, please enter another one");
                            System.out.println();

                        } else {

                            System.out.println("Thanks " + firstName);
                            Username = username;
                            System.out.println();
                            break;

                 }
            }
            System.out.println("Finally please enter your password");
            String password = scanner.nextLine();
            System.out.println();
            System.out.println("Welcome " + firstName);
            System.out.println();
            LocalDate dob = setDOB(DOB);
            int Age = ageCalculator(dob);
            registerIn(filename, firstName, surName, DOB, Age, Username, password);

       }catch(InputMismatchException e){

       }
    }   

    public void login(Scanner scanner, String filename) throws IOException{
        String Username  = null;
        String Name = null;

    try{
        System.out.println();
        System.out.println("Please login! ");
        System.out.println();
        System.out.println("Please enter your Username: ");
        System.out.println();

        while(true){

                System.out.println("Username: ");
                String username = scanner.nextLine();

                if(checkIfUsernameExists(filename, username)) {

                    System.out.println();
                    Username = username;
                    Name = getName(filename, Username);
                    System.out.println("Thank you");
                    break;

                } else {

                    System.out.println("Please enter a valid username: ");
                    System.out.println();
                }


            }

            

            System.out.println("Now " + Name + " please enter your password ");
            System.out.println();

            while(true) {
            System.out.println("Password: ");
            String password = scanner.nextLine();
            System.out.println();
            if(checkIfPasswordExists(filename, Username, password)) {

                System.out.println("Welcome " + Name);
                System.out.println();
                break;

            } else {

                System.out.println("Please enter the correct password!");
                System.out.println();
            }

        }

        } catch(InputMismatchException e){}
    }

    public void assignTeacher(String filename, String teacherID, String courseID) throws IOException {
        List<String>lines = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            while((line = reader.readLine()) != null) {

                lines.add(line);

            }

        }

        for(int i = 0; i < lines.size(); i++) {

            String line = lines.get(i);
            String [] words = line.split(",");
            if(words.length > 2 && words[0].equals(courseID)) {

                words[2] = teacherID;
                lines.set(i, String.join(",", words));

            }
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            for(String updatedLine : lines) {

                writer.write(updatedLine);
                writer.newLine();

            }
        }
    }

    public boolean checkOverlap(String filename, String location, int startHour, int startMinute, int endHour, int endMinute) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");

                // Ensure the details array has the required number of elements
                if (details.length < 7) {
                    continue; // Skip the line if it doesn't have enough columns
                }

                // Check if the location matches
                if (!details[4].equalsIgnoreCase(location)) {
                    continue;
                }

                // Extract start and end times from the CSV
                String[] startTimeParts = details[5].split(":");
                if (startTimeParts.length < 2) {
                    continue; // Skip the line if the start time is not properly formatted
                }
                int existingStartHour = Integer.parseInt(startTimeParts[0]);
                int existingStartMinute = Integer.parseInt(startTimeParts[1]);

                String[] endTimeParts = details[6].split(":");
                if (endTimeParts.length < 2) {
                    continue; // Skip the line if the end time is not properly formatted
                }
                int existingEndHour = Integer.parseInt(endTimeParts[0]);
                int existingEndMinute = Integer.parseInt(endTimeParts[1]);

                // Convert all times to minutes from midnight
                int newStartTotalMinutes = startHour * 60 + startMinute;
                int newEndTotalMinutes = endHour * 60 + endMinute;
                int existingStartTotalMinutes = existingStartHour * 60 + existingStartMinute;
                int existingEndTotalMinutes = existingEndHour * 60 + existingEndMinute;
                // Debugging outputs
                System.out.println("Checking overlap:");
                System.out.println("Location: " + location);
                System.out.println("New class time: " + startHour + ":" + startMinute + " to " + endHour + ":" + endMinute);
                System.out.println("Existing class time: " + existingStartHour + ":" + existingStartMinute + " to " + existingEndHour + ":" + existingEndMinute);

            // Check for overlap
            if ((newStartTotalMinutes < existingEndTotalMinutes && newEndTotalMinutes > existingStartTotalMinutes) ||
                (newStartTotalMinutes <= existingStartTotalMinutes && newEndTotalMinutes >= existingEndTotalMinutes)) {
                System.out.println("Overlap detected");
                return true;
            }
            }
        }

        return false;
    }

    // Other methods and main class implementation..


    public void recordGrade(String studentID, String courseId, double grade) throws IOException{
        
        try(FileWriter writer = new FileWriter(grades, true)){

            writer.append(String.join(",", studentID, courseId, String.valueOf(grade)));
            writer.append("\n");

        } catch(IOException e) {e.printStackTrace();}

    }

    public void remove(String filename, String ID) throws IOException {
        List<String> lines = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isRemovingClass = false;
    
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
    
                // Check if this line contains the class ID
                if (details.length > 0 && details[0].equals(ID)) {
                    isRemovingClass = true;
                    continue; // Skip this line as it contains the class to be removed
                }
    
                // Check if this line starts a new class
                if (details.length > 0 && !details[0].trim().isEmpty() && !details[0].equals(ID)) {
                    isRemovingClass = false; // Stop removing if a new class is encountered
                }
    
                // If we are in the process of removing a class, skip lines associated with that class
                if (isRemovingClass) {
                    // Check if this line contains a student ID (assuming student IDs are in the 8th column)
                    if (details.length >= 8 && !details[7].trim().isEmpty()) {
                        continue; // Skip this line as it contains a student ID to be removed
                    }
                }
    
                // Add the line to the list if it doesn't match the class or student ID to be removed
                lines.add(line);
            }
        }
    
        // Write the remaining lines back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        }
    }


    public void view(String filename, String type, boolean all, String id) throws IOException {

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) { 
            String line;


        if(type.equals("courses")) {

            if(all) {

                System.out.println("Course List: ");
                System.out.println();

                while((line = reader.readLine()) != null) {

                    String [] details = line.split(",");

                                //writer.append(String.join(",", studentID, firstName, surname, DOB, Integer.toString(Age), username, password, address, phoneNumber));
                                System.out.println("Course ID: " + details[0]);
                                System.out.println();
                                System.out.println("Course Name: " + details[1]);
                                System.out.println();
                                System.out.println("ID of Teacher assigned to course: " + details[2]);
                                System.out.println();
                                System.out.println("Name of teacher assigned to course: " + getTeachersName(filename, id));
                    }

                }

                else{

                    while((line = reader.readLine()) != null) {

                        String [] details = line.split(",");

                            if(details[0].equals(id)) {
                                //writer.append(String.join(",", studentID, firstName, surname, DOB, Integer.toString(Age), username, password, address, phoneNumber));
                                System.out.println("Course ID: " + details[0]);
                                System.out.println();
                                System.out.println("Course Name: " + details[1]);
                                System.out.println();
                                System.out.println("ID of Teacher assigned to course: " + details[2]);
                                System.out.println();
                                System.out.println("Name of teacher assigned to course: " + getTeachersName(filename, id));
                                

                            }

                        }
                    
                    }

                } else if(type.equals("students")) {

                    if(all) {

                        System.out.println("Students List: ");
                        System.out.println();

                        while((line = reader.readLine()) != null) {
            
                            String [] details = line.split(",");
    
                                    System.out.println("StudentID: " + details[0]);
                                    System.out.println();
                                    System.out.println("Student's firstname: " + details[1]);
                                    System.out.println();
                                    System.out.println("Student's surname" + details[2]);
                                    System.out.println();
                                    System.out.println("Students DOB" + details[3]);
                                    System.out.println();
                                    System.out.println("Students Age: " + details[4]);
                                    System.out.println();
                                    System.out.println("Students address: " + details[7]);
                                    System.out.println();
                                    System.out.println("Students Phone number: " + details[8]);
                                    System.out.println();
                                    System.out.println("Students username: " + details[5]);
                                    System.out.println();
                                    System.out.println("Students password: " + details[6]);
                                    System.out.println();
    
                                
            
                        }

                    } else {

                        while((line = reader.readLine()) != null) {

                            String [] details = line.split(",");
    
                                if(details[0].equals(id)) {
    
                                    System.out.println("StudentID: " + details[0]);
                                    System.out.println();
                                    System.out.println("Student's firstname: " + details[1]);
                                    System.out.println();
                                    System.out.println("Student's surname" + details[2]);
                                    System.out.println();
                                    System.out.println("Students DOB" + details[3]);
                                    System.out.println();
                                    System.out.println("Students Age: " + details[4]);
                                    System.out.println();
                                    System.out.println("Students address: " + details[7]);
                                    System.out.println();
                                    System.out.println("Students Phone number: " + details[8]);
                                    System.out.println();
                                    System.out.println("Students username: " + details[5]);
                                    System.out.println();
                                    System.out.println("Students password: " + details[6]);
                                    System.out.println();
    
                                }
                            }
                        }

                } else if (type.equals("classes")) {

                    if(all) {

                            System.out.println("Classes List: ");
                            System.out.println();

                            while((line = reader.readLine()) != null) {
            
                                String [] details = line.split(",");
                                if (details.length >= 8 && !details[0].isEmpty() && !details[1].isEmpty()) {
                                    System.out.println("ClassID: " + details[0]);
                                        System.out.println(details.length);
                                        System.out.println("ClassID: " + details[0] );
                                        System.out.println();
                                        System.out.println("CourseName: " + details[1]);
                                        System.out.println();
                                        System.out.println("Teachers ID: " + details[2]);
                                        System.out.println();
                                        System.out.println("Teachers Name: " + details[3]);
                                        System.out.println();
                                        System.out.println("Location: " + details[4]);
                                        System.out.println();
                                        System.out.println("Start Time: " + details[5]);
                                        System.out.println();
                                        System.out.println("End Time: " + details[6]);
                                        System.out.println();
                                        System.out.println("Number of students: " + details[7]);
                                        System.out.println();
                                        System.out.println("Student's:  ");
                                        System.out.println();
                                        List<String>students = Student.getStudentsFromCourse(filename, details[0]);
                                        for(String student : students) {

                                            System.out.println(student);
                                            System.out.println();
                                    }

                                }
                        
                            }
                            
    
                        } else {
                            // writer.append(String.join(",", courseName, teacherID, teachersName, location, startTime, endTime, numberOfStudents));
                            while((line = reader.readLine()) != null) {
    
                                String [] details = line.split(",");
        
                                    if(details[0].equals(id)) {
        
                                        System.out.println("ClassID: " + details[0] );
                                        System.out.println();
                                        System.out.println("CourseName: " + details[1]);
                                        System.out.println();
                                        System.out.println("Teachers ID: " + details[2]);
                                        System.out.println();
                                        System.out.println("Teachers Name: " + details[3]);
                                        System.out.println();
                                        System.out.println("Location: " + details[4]);
                                        System.out.println();
                                        System.out.println("Start Time: " + details[5]);
                                        System.out.println();
                                        System.out.println("End Time: " + details[6]);
                                        System.out.println();
                                        System.out.println("Number of students: " + details[7]);
                                        System.out.println();
                                        System.out.println("Student's:  ");
                                        System.out.println();
                                        List<String>students = Student.getStudentsFromCourse(filename, details[0]);
                                        for(String student : students) {

                                            System.out.println(student);
                                            System.out.println();

                                }
                            }
                         }
                      }
                  }
             } 
    }

    public String getTeachersName(String filename, String id) throws IOException{
        String teachersName = null;

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while((line = reader.readLine()) != null) {

                String [] details = line.split(",");

                if(details[0].equals(id)) {

                    teachersName = details[0] + " " + details[1];

                }
            }
        }

        return teachersName;
    }


    public int calculateNumberOfStudents(List<String>students) {
        int total = 0;

        for(String student : students) {
            total += 1;
        }
        return total;
    }

    public void addOrRemoveSingularStudent(String filename, String classID, String studentID, String choice) throws IOException {
        List<String> lines = new ArrayList<>();
        boolean studentFound = false;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean inClassSection = false;
            int studentCount = 0;
    
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                
                // Debugging output for each line read
                System.out.println("Reading line: " + line);
    
                if (details.length > 0 && details[0].equals(classID)) {
                    inClassSection = true;
                    studentCount = Integer.parseInt(details[7].trim());
                    System.out.println("Found class ID: " + classID + " with initial student count: " + studentCount);
                    lines.add(line);
                    continue;
                }
    
                if (inClassSection) {
                    if (details.length == 8 && details[7].trim().equals(studentID)) {
                        if (choice.equals("remove")) {
                            System.out.println("Removing student ID: " + studentID);
                            studentFound = true;
                            studentCount--;
                            continue; // Skip this line as it contains the student to be removed
                        }
                    } else if (details.length == 8 && details[7].trim().isEmpty()) {
                        if (choice.equals("add")) {
                            System.out.println("Adding student ID: " + studentID);
                            studentFound = true;
                            studentCount++;
                            lines.add(line); // Add the current empty line first
                            lines.add(",,,,,,," + studentID);
                            continue; // Skip adding the current line again
                        }
                    } else if (details.length == 8 && !details[7].trim().isEmpty()) {
                        lines.add(line);
                    } else {
                        inClassSection = false;
                    }
                } else {
                    lines.add(line);
                }
            }
        
        
        if (!studentFound) {
            System.out.println("Student ID not found or not in class.");
        } else {
            // Update the student count in the class details line
            for (int i = 0; i < lines.size(); i++) {
                String[] details = lines.get(i).split(",");
                if (details.length > 0 && details[0].equals(classID)) {
                    details[7] = String.valueOf(studentCount);
                    lines.set(i, String.join(",", details));
                    break;
                }
            }
        }
    }
    
    
        // Write the updated lines back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }


        public void attendenceMenu(Scanner scanner) throws IOException {
            HashMap<String, Boolean> map = new LinkedHashMap<>();
            boolean looped = true;

        while(looped){
            System.out.println("Please select: ");
            System.out.println();
            System.out.println("1) Register a students attendence");
            System.out.println("2) Edit/Update a students Attendence");
            System.out.println("3) Remove a students attendence");
            System.out.println("4) View attedence");
            System.out.println("5) Go back");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                System.out.println();
                    System.out.println("Please enter the name of the course: ");
                    String className = scanner.nextLine();
                    System.out.println();
                    System.out.println("Now please enter the class's ID");
                    String classID = scanner.nextLine();
                    System.out.println();

                    System.out.println("Now you will be asked to enter the student's ID and whether or not they were absent. Type 'done' when you are done.");
                    Set<String> studentIDs = recorder.collectStudentIDs("Registers/" + className + "/" + classID);

                    if (studentIDs != null && !studentIDs.isEmpty()) {
                        for (String studentID : studentIDs) {
                            System.out.println("StudentID: " + studentID);
                            System.out.println("Please enter whether or not they were absent: ");
                            String absentOrNot = scanner.nextLine();

                            if (absentOrNot.equalsIgnoreCase("absent")) {
                                map.put(studentID, false);
                            } else if (absentOrNot.equalsIgnoreCase("present")) {
                                map.put(studentID, true);
                            }
                        }
                    } else {
                        while (true) {
                            System.out.println("StudentID: ");
                            String studentID = scanner.nextLine();

                            if (!studentID.equals("done")) {
                                System.out.println("Please enter whether or not they were absent: ");
                                String absentOrNot = scanner.nextLine();

                                if (absentOrNot.equalsIgnoreCase("absent")) {
                                    map.put(studentID, false);
                                } else if (absentOrNot.equalsIgnoreCase("present")) {
                                    map.put(studentID, true);
                                }
                            } else if (studentID.equals("done")) {
                                break;
                            }
                        }
                    }

                    try {
                        recorder.recordAttendance(classID, className, map);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    map.clear();
                    System.out.println("Thank you, they have been added!");
                    break;

                case "2":
                            String toChangeTo = null;
                            String Date = null;
                            String Time = null;
                            System.out.println();
                            System.out.println("To update/edit a students attendence- ");
                            System.out.println("please enter the name of the course they were in: ");
                            String courseName = scanner.nextLine();
                            System.out.println();
                            System.out.println("Now please enter the ID of the class: ");
                            String classiD = scanner.nextLine();
                            System.out.println();
                            System.out.println("Next please enter the student's ID");
                            String studentId = scanner.nextLine();
                            System.out.println();

                            while(true){

                            System.out.println("Penultimatley, please enter the date on which this register was taken (in the form dd/MM/YYYY):  ");
                            String date = scanner.nextLine();

                            if(checkDate(date, true)) {

                                Date = date;
                                System.out.println(Date);
                                break;

                            } else {System.out.println("Please enter a valid date in the form dd/MM/YYYY: ");}

                            }

                            System.out.println();
                            System.out.println("Finally, please enter the time at which that class took place (in the form 'HH:MM'): ");

                            while(true){

                            String time = scanner.nextLine();
                            System.out.println();

                            if(checkTime(time)) {

                                Time = time;
                                break;

                            } else{

                                System.out.println("Please enter a valid time in the form HH:mm");
                            }
                            }

                            if(!recorder.locateStudent(courseName, classiD, Date, Time, studentId)){

                                System.out.println("The student could not be located, please try again!");
                                break;

                            }
                            System.out.println("The student has been located.");
                            System.out.println();
                            while(true){
                            System.out.println(" Please type 'absent' or 'present'");
                            String toChange = scanner.nextLine();

                            if(toChange.equalsIgnoreCase("absent")) {

                                toChangeTo = "/";
                                break;
                                
                            } else if(toChange.equalsIgnoreCase("present")) {

                                toChangeTo = "Y";
                                break;

                            } else {

                                System.out.println("Please enter a valid option!");
                                
                                }

                            }

                            recorder.updateRecord(courseName, classiD, Date, Time, studentId, toChangeTo);
                            System.out.println("It has been updated!");
                            break;

                case "3":   
                            String typeToRemove = null;
                            String dAte = null;
                            System.out.println();
                            System.out.println("Would you like to: ");
                            System.out.println();
                            System.out.println("1) Remove a student from a register? ");
                            System.out.println("2) Remove a time from the register?");
                            System.out.println("3) Remove a whole day from the register?");
                            System.out.println("4) Remove a class from the register?");
                            System.out.println("5) Remove a whole course from the register?");
                            
                            String Choice = scanner.nextLine();

                            switch (Choice) {

                                case "1":
                                          System.out.println("Please enter the following: ");
                                          System.out.println();
                                          System.out.println("please enter the name of the course: ");
                                            String CouresName = scanner.nextLine();
                                            System.out.println();
                                            System.out.println("Now please enter the ID of the class: ");
                                            String classesID = scanner.nextLine();
                                            System.out.println();

                                            while(true){

                                            System.out.println("Next, please enter the date on which this register was taken (in the form dd-MM-YYYY):  ");
                                            String date = scanner.nextLine();

                                            if(checkDate(date, true)) {

                                                dAte = date;
                                                break;

                                            } else {System.out.println("Please enter a valid date in the form dd-MM-YYYY: ");}

                                            }

                                            System.out.println("penultimatley, please enter the time this register was taken: ");
                                            String studentTime = scanner.nextLine();

                                            System.out.println("Finally, please enter the student's ID");
                                            String studentsID = scanner.nextLine();
                                            System.out.println();
                                            recorder.removeStudent(CouresName, classesID, dAte, studentTime, studentsID);
                                            break;
                                    
                                    case "2":
                                              String date = null;
                                              String time = null;
                                              System.out.println();
                                              System.out.println("Please enter:");
                                              System.out.println();
                                              System.out.println("The name of the course:");
                                              String nameOfCourse = scanner.nextLine();
                                              System.out.println();
                                              System.out.println("The classID: ");
                                              String idOfClass = scanner.nextLine();
                                              System.out.println();
                                              
                                              while(true) {
                                              System.out.println("Date of register to remove (in the form 'DD-MM-YYYY'): ");
                                              String dayToRemove = scanner.nextLine();

                                              if(checkDate(dayToRemove, true)) {

                                                  date = dayToRemove;
                                                  break;

                                              } else {System.out.println("Please enter a valid date!");}

                                            }

                                            System.out.println("Please enter the time of register (in the form 'HH:MM'): ");
                                            String timeToRemove = scanner.nextLine();
                                            
                                            if(recorder.checkTime(timeToRemove)) {

                                                time = timeToRemove;

                                            }  else {
                                                
                                                System.out.println("Please enter a valid time in the form MM:HH ");
                                            
                                            }
                                            typeToRemove = "time";
                                            recorder.removeTime(nameOfCourse, idOfClass, date, time);
                                            break;


                                        case "3":   
                                                    String dateOfReg = null;
                                                    System.out.println("Please enter the following: ");
                                                    System.out.println();
                                                    System.out.println("please enter the name of the course: ");
                                                    String nameCourse = scanner.nextLine();
                                                    System.out.println();
                                                    System.out.println("Now please enter the ID of the class: ");
                                                    String IDclass = scanner.nextLine();
                                                    System.out.println();

                                                    while(true){

                                                    System.out.println("Finally, please enter the date on which this register was taken (in the form dd-MM-YYYY):  ");
                                                    String dayOfReg = scanner.nextLine();

                                                    if(checkDate(dayOfReg, true)) {

                                                        dateOfReg = dayOfReg;
                                                        break;

                                                    } else {System.out.println("Please enter a valid date in the form dd-MM-YYYY: ");}
                                                }
                                                typeToRemove = "day";
                                                recorder.removeDate(nameCourse, IDclass, dateOfReg);
                                                break;

                                        case "4":
                                                  System.out.println("Please enter the following:");
                                                  System.out.println();
                                                  System.out.println("The name of the course: ");
                                                  String courseNameToRemove = scanner.nextLine();
                                                  System.out.println();
                                                  System.out.println("The ID of the class you would like to remove: ");
                                                  String classIDToRemove = scanner.nextLine();
                                                  System.out.println();
                                                  typeToRemove = "class";
                                                  recorder.removeClass(courseNameToRemove, classIDToRemove);
                                                  break;

                                        case "5":
                                                 System.out.println();
                                                 System.out.println("Please enter the following: ");
                                                 System.out.println();
                                                 System.out.println("Name of course you would like to remove: ");
                                                 String courseToPermantentlyRemove = scanner.nextLine();
                                                 typeToRemove = "course";
                                                 recorder.removeCourse(courseToPermantentlyRemove, "Registers");
                                                 break;
                                                

                                                
                                

                            
                                default:
                                    break;
                            }
                
                case "4":
                          System.out.println("Please select: ");
                          System.out.println();
                          System.out.println("1) View Students attendence menu");
                          System.out.println();
                          System.out.println("2) View a Class's attedence");
                          System.out.println();
                          String viewChoice = scanner.nextLine();

                          if(viewChoice.equalsIgnoreCase("1")){

                            System.out.println("1) View Singular Student's Localised Attendence");
                            System.out.println();
                            System.out.println("2) View a Singular Student's Overall Attendence");
                            System.out.println();
                            System.out.println("3) Issue A Student An Attendence Warning");
                            System.out.println();
                            System.out.println("4) View An Courses Average Attendence");

                            String choiceToView = scanner.nextLine();

                            switch (choiceToView) {

                                case "1":
                                String dateToView = null;
                                String timeToView = null;
    
                                    System.out.println("Please enter the name of the course that student is a part of: ");
                                    String courseToView = scanner.nextLine();
                                    System.out.println();
                                    System.out.println("Please enter the classID: ");
                                    String classIDtoView = scanner.nextLine();
                                    System.out.println();
                                    System.out.println("Please enter the date on which their register was taken: ");
                                    System.out.println();
                                    while(true){
    
                                        System.out.println("Date: ");
                                        String date = scanner.nextLine();
    
                                        if(checkDate(date, true)) {
    
                                            dateToView = date;
                                            System.out.println();
                                            break;
    
                                        } else {System.out.println("Please enter a valid date;");}
    
                                    }
    
                                    System.out.println("Now please enter the time at which thier register was taken: ");
                                    System.out.println();
    
                                    while(true){
                                    System.out.println("Time: ");
                                    String time = scanner.nextLine();
    
                                    if(checkTime(time)){
                                    
                                        timeToView = time;
                                        System.out.println();
                                        break;
                                    
                                        } else {System.out.println("Please enter a valid time");}
                                    }

                                    System.out.println("Finally please enter their ID: ");
                                    String studentToViewID = scanner.nextLine();

                                    recorder.viewSingularStudentsAttendence(courseToView, classIDtoView, dateToView, timeToView, studentToViewID);
                                    break;
                            
                                case "2":
                                          System.out.println();
                                          System.out.println("Please enter the student's ID");
                                          System.out.println();
                                          System.out.println("ID: ");
                                          String studentIDtoViewOverall = scanner.nextLine();
                                          System.out.println();
                                          double overall = recorder.viewStudentOverallAttendance(studentIDtoViewOverall);
                                          System.out.println("Students Overall Attendence: " + overall + "%");
                                          break;
                                        
                                case "3":
                                            System.out.println("Please enter the ID of the student you would like to issue an attendence warning for: ");
                                            System.out.println();
                                            String studentIDToIssueWarningFor = scanner.nextLine();
                                            System.out.println();
                                            recorder.issueAttendanceWarning(studentIDToIssueWarningFor);
                                            scanner.nextLine();
                                            break;
                                
                                case "4":
                                            System.out.println();
                                            System.out.println("Please enter the name of the course you would like to find the average attendence of: ");
                                            String courseToFindAttendenceOf = scanner.nextLine();
                                            System.out.println();
                                            double courseAttendence = recorder.courseAverageAttendance(courseToFindAttendenceOf);
                                            System.out.println("Course Average Attendence: " + courseAttendence + "%");
                                            System.out.println();  
                            


                                default:
                                    break;
                            }
                        
                            



 
                          } else if(viewChoice.equalsIgnoreCase("2")) {
                            String dateToView = null;
                            String timeToView = null;

                                System.out.println("Please enter the name of the course that class is a part of: ");
                                String courseToView = scanner.nextLine();
                                System.out.println();
                                System.out.println("Please enter the classID: ");
                                String classIDtoView = scanner.nextLine();
                                System.out.println();
                                System.out.println("Please enter the date on which this register was taken: ");
                                System.out.println();
                                while(true){

                                    System.out.println("Date: ");
                                    String date = scanner.nextLine();

                                    if(checkDate(date, true)) {

                                        dateToView = date;
                                        System.out.println();
                                        break;

                                    } else {System.out.println("Please enter a valid date;");}

                                }

                                System.out.println("Now please enter the time at which this register was taken: ");
                                System.out.println();

                                while(true){
                                System.out.println("Time: ");
                                String time = scanner.nextLine();

                                if(checkTime(time)){
                                
                                    timeToView = time;
                                    System.out.println();
                                    break;
                                
                                    } else {System.out.println("Please enter a valid time");}
                                }

                                recorder.viewClassAttendence(courseToView, classIDtoView, dateToView, timeToView);
                          }
                          break;
                
                
                case "5":
                          looped = false;
                          break;


                default:
                    break;
                }
            }

        }

        public static void main(String [] args)throws IOException{

            Scanner scanner = new Scanner(System.in);

            SchoolSystem sys = new SchoolSystem();
            sys.attendenceMenu(scanner);

        }

    }

