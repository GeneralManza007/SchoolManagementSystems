import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Grades {
    public static AttendanceRecorder recorder = new AttendanceRecorder();
    public static final String Grades = "Grades";

    public void addGrade(String course, String schoolYear, String classID, String testName, LinkedHashMap<String, String> map) throws IOException {

        String baseDir = Grades;
        String courseDir = Paths.get(baseDir, course).toString();
        String schoolYearDir = Paths.get(courseDir, schoolYear).toString(); // Corrected this line
        String classIDDir = Paths.get(schoolYearDir, classID).toString();

        createDirectoryIfNotExists(baseDir);
        createDirectoryIfNotExists(courseDir);
        createDirectoryIfNotExists(schoolYearDir);
        createDirectoryIfNotExists(classIDDir);

        String csvFileName = testName + ".csv";
        Path csvFilePath = Paths.get(classIDDir, csvFileName);

        boolean fileExists = Files.exists(csvFilePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath.toString(), true))) {
            if (!fileExists) {
                writer.write("StudentID,Grade");
                writer.newLine();
            }

            for (Map.Entry<String, String> entry : map.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println("Error writing to file: " + csvFilePath);
            throw e;
        }
    }

    private void createDirectoryIfNotExists(String dir) throws IOException {
        Path path = Paths.get(dir);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
    }

    public void updateGrades(String courseName, String schoolYear, String classID, String testName, String studentID, String grade)throws IOException{
        String baseDir = Grades;
        String courseDir = Paths.get(baseDir, courseName).toString();
        String schoolYearDir = Paths.get(courseDir, schoolYear).toString(); // Corrected this line
        String classIDDir = Paths.get(schoolYearDir, classID).toString();
        String csvFileName = testName + ".csv";
        Path csvFilePath = Paths.get(classIDDir, csvFileName);

        if (Files.notExists(csvFilePath)) {
            System.out.println("The specified file does not exist!");
            return;
        }

        StringBuilder content = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath.toString()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts[0].equals(studentID)) {
                            line = studentID + "," + grade;
                        }
                        content.append(line).append(System.lineSeparator());
                    }
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath.toString()))) {
                    writer.write(content.toString());
                }
            }

    public void removeSchoolYear(String courseName, String schoolYear) throws IOException{
        String baseDir = Grades;
        String courseDir = Paths.get(baseDir, courseName).toString();
        String syDir = Paths.get(courseDir, schoolYear).toString();
        Path path = Paths.get(syDir);

        if (Files.exists(path)) {
            Files.walk(path)
                .sorted((a, b) -> b.compareTo(a)) // reverse order to delete files before directories
                .forEach(p -> {
                    try {
                        Files.delete(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            System.out.println("School Year removed successfully.");
        } else {
            System.out.println("School Year does not exist.");
        }
    }

    public void removeClass(String courseName, String schoolYear, String classID) throws IOException{
        String baseDir = Grades;
        String courseDir = Paths.get(baseDir, courseName).toString();
        String schoolYearDir = Paths.get(courseDir, schoolYear).toString();
        String classIDDir = Paths.get(schoolYearDir, classID).toString();
        Path path = Paths.get(classIDDir);

        if (Files.exists(path)) {
            Files.walk(path)
                .sorted((a, b) -> b.compareTo(a)) // reverse order to delete files before directories
                .forEach(p -> {
                    try {
                        Files.delete(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            System.out.println("Class removed successfully.");
        } else {
            System.out.println("Class does not exist.");
        }
    }

    public void removeTest(String courseName, String schoolYear, String classID, String testName) throws IOException{
        String baseDir = Grades;
        String courseDir = Paths.get(baseDir, courseName).toString();
        String schoolYearDir = Paths.get(courseDir, schoolYear).toString();
        String classIDDir = Paths.get(schoolYearDir, classID).toString();
        String testNameDir = Paths.get(classIDDir, testName).toString();
        String testNamePath = testNameDir + ".csv";
        Path csvFilePath = Paths.get(testNamePath);

        if (Files.exists(csvFilePath)) {
            Files.delete(csvFilePath);
            System.out.println("Time record removed successfully.");
        } else {
            System.out.println("Time record does not exist.");
        }

    }

    public void removeStudent(String courseName, String schoolYear, String classID, String testName, String studentID) throws IOException{
        String baseDir = Grades;
        String courseDir = Paths.get(baseDir, courseName).toString();
        String schoolYearDir = Paths.get(courseDir, schoolYear).toString();
        String classIDDir = Paths.get(schoolYearDir, classID).toString();
        String testNameDir = Paths.get(classIDDir, testName).toString();
        String testNamePath = testNameDir + ".csv";
        Path csvFilePath = Paths.get(testNamePath);

        if (Files.notExists(csvFilePath)) {
            System.out.println("The specified file does not exist!");
            return;
        }

        StringBuilder content = new StringBuilder();
        boolean studentFound = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath.toString()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (!parts[0].equals(studentID)) {
                    content.append(line).append(System.lineSeparator());
                } else {
                    studentFound = true;
                }
            }
        }

        if (studentFound) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath.toString()))) {
                writer.write(content.toString());
                System.out.println("Student removed successfully.");
            }
        } else {
            System.out.println("Student ID not found.");
        }
    }

    public void viewSingularStudentsGrade(String courseName, String schoolYear, String classID, String testName, String studentID) throws IOException{
        String baseDir = Grades;
        String courseDir = Paths.get(baseDir, courseName).toString();
        String schoolYearDir = Paths.get(courseDir, schoolYear).toString();
        String classIDDir = Paths.get(schoolYearDir, classID).toString();
        String testNameDir = Paths.get(classIDDir, testName).toString();
        String testNamePath = testNameDir + ".csv";
        Path csvFilePath = Paths.get(testNamePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath.toString()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(studentID)) {

                    System.out.println("Student ID: " + parts[0] + " Grade: " + parts[1]);

                    }
                }       
            }
        }


        public void viewSingularStudentsGradesForAllTests(String courseName, String schoolYear, String classID, String studentID) {
            String baseDir = Grades;
            String classIDDir = Paths.get(baseDir, courseName, schoolYear, classID).toString();
    
            // Using an array to hold sumOfGrades and totalGrades
            final double[] sumOfGrades = {0.0};
            final int[] totalGrades = {0};
    
            try {
                Files.walk(Paths.get(classIDDir))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".csv"))
                    .forEach(path -> {
                        try (BufferedReader reader = new BufferedReader(new FileReader(path.toString()))) {
                            String line;
                            reader.readLine(); // Skip header line
                            while ((line = reader.readLine()) != null) {
                                String[] parts = line.split(",");
                                if (parts.length > 1 && parts[0].equals(studentID)) {
                                    try {
                                        double grade = Double.parseDouble(parts[1]);
                                        sumOfGrades[0] += grade;
                                        totalGrades[0]++;
                                    } catch (NumberFormatException e) {
                                        System.err.println("Invalid grade format for student " + studentID + " in file " + path.getFileName());
                                    }
                                }
                            }
                        } catch (IOException e) {
                            System.err.println("Error reading file " + path.getFileName());
                            e.printStackTrace();
                        }
                    });
    
                if (totalGrades[0] > 0) {
                    double averageGrade = sumOfGrades[0] / totalGrades[0];
                    System.out.println("Average grade for student " + studentID + ": " + averageGrade);
                } else {
                    System.out.println("No grades found for student " + studentID);
                }
    
            } catch (IOException e) {
                System.err.println("Error accessing the directory: " + classIDDir);
                e.printStackTrace();
            }
        }


   public double viewSingularStudentsOverallGrade(String courseName, String schoolYear, String studentID) throws IOException {
        String baseDir = Grades;
        Path coursePath = Paths.get(baseDir, courseName, schoolYear);

        // Collect all CSV files related to the course and school year
        List<Path> csvFiles = new ArrayList<>();
        collectCSVFiles(coursePath, csvFiles);

        int totalTests = 0;
        double sumOfGrades = 0.0;

        // Process each CSV file
        for (Path csvFile : csvFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile.toString()))) {
                String line;
                // Skip the header
                reader.readLine();
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(studentID)) {
                        sumOfGrades += Double.parseDouble(parts[1]); // Assuming the grade is a double
                        totalTests++;
                        break; // Assuming each student appears only once per file
                    }
                }
            }
        }

        // Calculate the average grade
        if (totalTests == 0) {
            return 0.0; // If the student ID was not found in any tests
        }

        return sumOfGrades / totalTests;
    }

    // Helper method to collect all CSV files in a given directory and its subdirectories
    private void collectCSVFiles(Path dir, List<Path> csvFiles) throws IOException {
        if (Files.isDirectory(dir)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                for (Path entry : stream) {
                    if (Files.isDirectory(entry)) {
                        collectCSVFiles(entry, csvFiles);
                    } else if (entry.toString().endsWith(".csv")) {
                        csvFiles.add(entry);
                    }
                }
            }
        }
    }

    public double viewClassesOverallGradeForTest(String courseName, String schoolYear, String classID, String testName) throws IOException {
        String baseDir = Grades;
        String classIDDIR = Paths.get(baseDir, courseName, schoolYear, classID).toString();
        Path testFilePath = Paths.get(classIDDIR, testName + ".csv");

        if(!Files.exists(testFilePath)){
            System.out.println("Test file not found for the specified class.");
            return 0.0;
        }

        double sumOfGrades = 0.0;
        int numberOfStudents = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(testFilePath.toString()))){
            String line;
            reader.readLine();
            while((line = reader.readLine()) != null){
                String [] parts = line.split(",");

                if(parts.length > 2) {

                    sumOfGrades += Double.parseDouble(parts[1]);
                    numberOfStudents++;
                }
            }
        }

        if(numberOfStudents == 0) {
            return 0.0;
        }
        return sumOfGrades / numberOfStudents;
    }

    public void viewClassesOverallGradeForCourse(String courseName, String schoolYear, String classID) throws IOException {
        String baseDir = "Grades";
        String classIDDir = Paths.get(baseDir, courseName, schoolYear, classID).toString();

        if (!Files.exists(Paths.get(classIDDir))) {
            System.out.println("Directory for the specified class does not exist.");
        }

        double [] totalSumOfGrades = {0.0};
        int [] totalNumberOfGrades = {0};

        // Walk through all CSV files in the class directory
        Files.walk(Paths.get(classIDDir))
            .filter(Files::isRegularFile) // Only regular files (not directories)
            .filter(path -> path.toString().endsWith(".csv")) // Only CSV files
            .forEach(path -> {
                try (BufferedReader reader = new BufferedReader(new FileReader(path.toString()))) {
                    String line;
                    reader.readLine(); // Skip header line
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length >= 2) {
                            totalSumOfGrades[0] += Double.parseDouble(parts[1]); // Assuming the grade is a double
                            totalNumberOfGrades[0]++;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


            if (totalNumberOfGrades[0] > 0) {
                double averageGrade = totalSumOfGrades[0] / totalNumberOfGrades[0];
                System.out.println("Average grade for class " + classID + ": " + averageGrade);
            } else {
                System.out.println("No grades found for class " + classID);
            }

     
    }


        
    public void addingGradesMenu(Scanner scanner) throws IOException {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        System.out.println("Select: ");
        System.out.println();
        System.out.println("1) Add a whole class's grade: ");
        System.out.println("2) Add a singular student's grade: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.println("Please enter:");
                System.out.println("The name of the course: ");
                String courseName = scanner.nextLine();
                System.out.println("The school year this test was taken: ");
                String schoolYear = scanner.nextLine();
                System.out.println("The classID: ");
                String classID = scanner.nextLine();
                System.out.println("Test Name: ");
                String testName = scanner.nextLine();
                System.out.println("Now you will be asked to enter the student's ID and their grade. Type 'done' when you are done.");
                Set<String> studentIDs = recorder.collectStudentIDs("Grades/" + courseName + "/" + schoolYear + "/" + classID);

                if (studentIDs != null && !studentIDs.isEmpty()) {
                    for (String studentID : studentIDs) {
                        System.out.println("StudentID: " + studentID);
                        System.out.println("Please enter their grade: ");
                        String grade = scanner.nextLine();
                        map.put(studentID, grade);
                    }
                } else {
                    while (true) {
                        System.out.println("StudentID: ");
                        String studentID = scanner.nextLine();
                        System.out.println("Grade: ");
                        String grade = scanner.nextLine();
                        if (!studentID.equals("done")) {
                            map.put(studentID, grade);
                        } else {
                            break;
                        }
                    }
                }

                try {
                    addGrade(courseName, schoolYear, classID, testName, map);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                map.clear();
                System.out.println("Thank you, the grades have been added!");
                break;

            case "2":
                System.out.println("Please enter:");
                System.out.println("The name of the course: ");
                courseName = scanner.nextLine();
                System.out.println("The school year this test was taken: ");
                schoolYear = scanner.nextLine();
                System.out.println("The classID: ");
                classID = scanner.nextLine();
                System.out.println("StudentID: ");
                String studentID = scanner.nextLine();
                System.out.println("Test Name: ");
                testName = scanner.nextLine();
                System.out.println("Grade: ");
                String grade = scanner.nextLine();

                map.put(studentID, grade);

                try {
                    addGrade(courseName, schoolYear, classID, testName, map);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                map.clear();
                System.out.println("Thank you, the grade has been added!");
                break;

            default:
                System.out.println("Invalid choice");
                break;
        }
    }


    public void updateGradeMenu(Scanner scanner) throws IOException{

        System.out.println();
        System.out.println("Please enter the name of the course: ");
        String courseName = scanner.nextLine();
        System.out.println();
        System.out.println("Now please enter the school year, in the form yy-yy: ");
        String schoolYear = scanner.nextLine();
        System.out.println();
        System.out.println("Please enter the ID of the class the student is a part of: ");
        String classID = scanner.nextLine();
        System.out.println();
        System.out.println("Now please add the name of the test taken: ");
        String testName = scanner.nextLine();
        System.out.println();
        System.out.println("Penultimatley, please enter the ID of the student whose results you would like to change: ");
        String studentID = scanner.nextLine();
        System.out.println();
        System.out.println("Finally, please enter the new grade you would like to change the old one too: ");
        String newGrade = scanner.nextLine();
        System.out.println();
        updateGrades(courseName, schoolYear, classID, testName, studentID, newGrade);
        System.out.println("Please check if has been updated. Thank you.");
    }



    public void removingGrades(Scanner scanner) throws IOException{
        boolean loop = true;
        while(loop){
        System.out.println();
        System.out.println("1) Remove a course");
        System.out.println("2) Remove a school year");
        System.out.println("3) Remove a class");
        System.out.println("4) Remove a test");
        System.out.println("5) Remove a student");
        System.out.println("6)Go back");

        String choice = scanner.nextLine();

        switch (choice) {

            case "1":
                      System.out.println("Please enter the name of the course to remove: ");
                      System.out.println();
                      String courseName = scanner.nextLine();
                      System.out.println();
                      AttendanceRecorder recorder = new AttendanceRecorder();
                      recorder.removeCourse(courseName, "Grades");
                      break;
                      
            case "2":
                     System.out.println();
                     System.out.println("Please enter the name of the course to remove: ");
                     String syCourseName = scanner.nextLine();
                     System.out.println();
                     System.out.println("Please enter the school year this was a part of: ");
                     String sy = scanner.nextLine();
                     System.out.println();
                     removeSchoolYear(syCourseName, sy);
                     break;
            
            case "3":
                     System.out.println();
                     System.out.println("Please enter the name of the course to remove: ");
                     String cCourseName = scanner.nextLine();
                     System.out.println();
                     System.out.println("Please enter the school year this course was in: ");
                     String cSchoolYear = scanner.nextLine();
                     System.out.println();
                     System.out.println("Please enter the classID of said class: ");
                     String classID = scanner.nextLine();
                     System.out.println();
                     removeClass(cCourseName, cSchoolYear, classID);
                     break;

            
            case "4":
                      System.out.println();
                      System.out.println("Please enter the name of the course to remove: ");
                      String tCourseName = scanner.nextLine();
                      System.out.println();
                      System.out.println("Please enter the school year this course was in: ");
                      String tSchoolYear = scanner.nextLine();
                      System.out.println();
                      System.out.println("Please enter the ID of the class this test was a part of; ");
                      String ClassID = scanner.nextLine();
                      System.out.println();
                      System.out.println("Please enter the name of the test: ");
                      String testName = scanner.nextLine();
                      System.out.println();
                      removeTest(tCourseName, tSchoolYear, ClassID, testName);
                      break;

            case "5":
                     System.out.println();
                     System.out.println("Please enter the name of the course to remove: ");
                     String sCourseName = scanner.nextLine();
                     System.out.println();
                     System.out.println("Please enter the school year this course was in: ");
                     String sSchoolYear = scanner.nextLine();
                     System.out.println();
                     System.out.println("Please enter the ID of the class this test was a part of; ");
                     String sClassID = scanner.nextLine();
                     System.out.println();
                     System.out.println("Please enter the name of the test: ");
                     String stestName = scanner.nextLine();
                     System.out.println();
                     System.out.println("Finally, please enter the ID of the student you would like to remove: ");
                     String studentID = scanner.nextLine();
                     System.out.println();
                     removeStudent(sCourseName, sSchoolYear, sClassID, stestName, studentID);
                     break;

            case "6":
                    loop = false;
                    break;
            default:
                break;

            }
        }
    }

    public void viewGradesMenu(Scanner scanner) throws IOException{

        System.out.println();
        System.out.println("Please select an option: ");
        System.out.println();
        System.out.println("1) View Singular Students Grade For Specified Test");
        System.out.println();
        System.out.println("2) View Singular Students Grade For All Tests ");
        System.out.println();
        System.out.println("3) View Singular Students Overall Grade");
        System.out.println();
        System.out.println("4) View Classes Grade For Test ");
        System.out.println();
        System.out.println("5) View Classes Overall Grade For Course ");
        System.out.println();

        String choice = scanner.nextLine();
        System.out.println();

        switch (choice) {

            case "1":
                System.out.println("Please enter the name of the course: ");
                String courseName = scanner.nextLine();
                System.out.println();
                System.out.println("Now enter the school year: ");
                String schoolYear = scanner.nextLine();
                System.out.println();
                System.out.println("Now enter the classID of the class this student is a part of: ");
                String classID = scanner.nextLine();
                System.out.println();
                System.out.println("Now enter the name of the test taken: ");
                String testName = scanner.nextLine();
                System.out.println();
                System.out.println("Finally please enter the id of the student: ");
                String studentID = scanner.nextLine();
                viewSingularStudentsGrade(courseName, schoolYear, classID, testName, studentID);
                break; 


            case "2":
                System.out.println("Please enter the name of the course: ");
                String aCourseName = scanner.nextLine();
                System.out.println();
                System.out.println("Now enter the school year: ");
                String aSchoolYear = scanner.nextLine();
                System.out.println();
                System.out.println("Now enter the classID of the class this student is a part of: ");
                String aClassID = scanner.nextLine();
                System.out.println();
                System.out.println("Finally please enter the id of the student: ");
                String aStudentID = scanner.nextLine();
                viewSingularStudentsGradesForAllTests(aCourseName, aSchoolYear, aClassID, aStudentID);
                break;


            case "3":
                System.out.println("Please enter the name of the course: ");
                String oCourseName = scanner.nextLine();
                System.out.println();
                System.out.println("Now enter the school year: ");
                String oSchoolYear = scanner.nextLine();
                System.out.println();
                System.out.println("Now enter the classID of the class this student is a part of: ");
                String oClassID = scanner.nextLine();
                System.out.println();
                System.out.println("Finally please enter the id of the student: ");
                String oStudentID = scanner.nextLine();
                viewSingularStudentsOverallGrade(oCourseName, oSchoolYear, oStudentID);
                break;

            case "4":
                System.out.println("Please enter the name of the course: ");
                String cCourseName = scanner.nextLine();
                System.out.println();
                System.out.println("Now enter the school year: ");
                String cSchoolYear = scanner.nextLine();
                System.out.println();
                System.out.println("Now enter the classID of the class this student is a part of: ");
                String cClassID = scanner.nextLine();
                System.out.println();
                System.out.println("Now enter the name of the test taken: ");
                String ctestName = scanner.nextLine();
                System.out.println();
                viewClassesOverallGradeForTest(cCourseName, cSchoolYear, cClassID, ctestName);
                break;

            case "5":
                System.out.println("Please enter the name of the course: ");
                String coCourseName = scanner.nextLine();
                System.out.println();
                System.out.println("Now enter the school year: ");
                String coSchoolYear = scanner.nextLine();
                System.out.println();
                System.out.println("Now enter the classID of the class this student is a part of: ");
                String coClassID = scanner.nextLine();
                System.out.println();
                viewClassesOverallGradeForCourse(coCourseName, coSchoolYear, coClassID);
                break;
            default:
                break;
        }

    }

    public void GradesMenu(Scanner scanner)throws IOException{

        while(true){

        System.out.println();
        System.out.println("Please select an option: ");
        System.out.println();
        System.out.println("1) View Grade's menu");
        System.out.println("2) Adding grades menu");
        System.out.println("3) Update grades");
        System.out.println("4) Removing grades menu");
        
        String choice = scanner.nextLine();

        switch (choice) {

            case "1":
                     viewGradesMenu(scanner);
                     break;


            case "2":
                     addingGradesMenu(scanner);
                     break;

            case "3":
                     updateGradeMenu(scanner);
                     break;

            case "4":
                     removingGrades(scanner);
                     break;
            default:
                System.out.println("Please choose a valid option: 1,2,3,4 ");
                break;
            }
        }
    }

    public static void main(String [] args){

        try{
            Scanner scanner = new Scanner(System.in);
            Grades grade = new Grades();
            grade.GradesMenu(scanner);

        }catch(IOException e) {e.printStackTrace();}
    }
}
