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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AttendanceRecorder extends SchoolSystem {

    public AttendanceRecorder() {
        super();
    }

    public void recordAttendance(String classID, String className, Map<String, Boolean> studentAttendance) throws IOException {
        String baseDir = "Registers";
        String classDir = Paths.get(baseDir, className).toString();
        String classIDDir = Paths.get(classDir, classID).toString();

        createDirectoryIfNotExists(baseDir);
        createDirectoryIfNotExists(classDir);
        createDirectoryIfNotExists(classIDDir);

        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-YYYY"));
        String dateDir = Paths.get(classIDDir, currentDate).toString();
        createDirectoryIfNotExists(dateDir);

        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        String csvFileName = currentTime + ".csv";
        Path csvFilePath = Paths.get(dateDir, csvFileName);

        boolean fileExists = Files.exists(csvFilePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath.toString(), true))) {
            if (!fileExists) {
                writer.write("StudentID,Attendance");
                writer.newLine();
            }
            for (Map.Entry<String, Boolean> entry : studentAttendance.entrySet()) {
                String studentID = entry.getKey();
                boolean isPresent = entry.getValue();
                writer.write(studentID + "," + (isPresent ? "Y" : "/"));
                writer.newLine();
            }
        }
    }

    private void createDirectoryIfNotExists(String dir) throws IOException {
        Path path = Paths.get(dir);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
    }

    public boolean locateStudent(String courseName, String classID, String date, String time, String studentID) throws IOException{

        String baseDir = "Registers";
        String classDir = Paths.get(baseDir, courseName).toString();
        String classIDDir = Paths.get(classDir, classID).toString();
        String dateDir = Paths.get(classIDDir, date).toString();
        String csvFileName = time + ".csv";
        Path csvFilePath = Paths.get(dateDir, csvFileName);

        if (Files.notExists(csvFilePath)) {
            System.out.println("The specified file does not exist!");
            return false;
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath.toString()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(studentID)) { 
                    
                    return true;

                }
            }
        }
        return false;
    }

    public void updateRecord(String courseName, String classID, String date, String time, String studentID, String toWrite) throws IOException {
        String baseDir = "Registers";
        String classDir = Paths.get(baseDir, courseName).toString();
        String classIDDir = Paths.get(classDir, classID).toString();
        String dateDir = Paths.get(classIDDir, date).toString();
        String csvFileName = time + ".csv";
        Path csvFilePath = Paths.get(dateDir, csvFileName);

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
                    line = studentID + "," + toWrite;
                }
                content.append(line).append(System.lineSeparator());
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath.toString()))) {
            writer.write(content.toString());
        }
    }

    public void removeCourse(String courseName, String BaseDir) throws IOException {
        String baseDir = BaseDir;
        String classDir = Paths.get(baseDir, courseName).toString();
        Path path = Paths.get(classDir);

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
            System.out.println("Course removed successfully.");
        } else {
            System.out.println("Course does not exist.");
        }
    }


    public void removeClass(String courseName, String classID) throws IOException {
        String baseDir = "Registers";
        String classDir = Paths.get(baseDir, courseName).toString();
        String classIDDir = Paths.get(classDir, classID).toString();
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

    public void removeDate(String courseName, String classID, String date) throws IOException {
        String baseDir = "Registers";
        String classDir = Paths.get(baseDir, courseName).toString();
        String classIDDir = Paths.get(classDir, classID).toString();
        String dateDir = Paths.get(classIDDir, date).toString();
        Path path = Paths.get(dateDir);

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
            System.out.println("Date removed successfully.");
        } else {
            System.out.println("Date does not exist.");
        }
    }

    public void removeTime(String courseName, String classID, String date, String time) throws IOException {
        String baseDir = "Registers";
        String classDir = Paths.get(baseDir, courseName).toString();
        String classIDDir = Paths.get(classDir, classID).toString();
        String dateDir = Paths.get(classIDDir, date).toString();
        String csvFileName = time + ".csv";
        Path csvFilePath = Paths.get(dateDir, csvFileName);

        if (Files.exists(csvFilePath)) {
            Files.delete(csvFilePath);
            System.out.println("Time record removed successfully.");
        } else {
            System.out.println("Time record does not exist.");
        }
    }

    public void removeStudent(String courseName, String classID, String date, String time, String studentID) throws IOException {
        String baseDir = "Registers";
        String classDir = Paths.get(baseDir, courseName).toString();
        String classIDDir = Paths.get(classDir, classID).toString();
        String dateDir = Paths.get(classIDDir, date).toString();
        String csvFileName = time + ".csv";
        Path csvFilePath = Paths.get(dateDir, csvFileName);

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


    public void viewClassAttendence(String courseName, String classID, String date, String time) throws IOException{
        String baseDir = "Registers";
        String classDir = Paths.get(baseDir, courseName).toString();
        String classIDDir = Paths.get(classDir, classID).toString();
        String dateDir = Paths.get(classIDDir, date).toString();
        String csvFileName = time + ".csv";
        Path csvFilePath = Paths.get(dateDir, csvFileName);

        if (Files.notExists(csvFilePath)) {
            System.out.println("The specified file does not exist!");
            return;
        }
    

    StringBuilder builder = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath.toString()))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");

            if(parts[1].equalsIgnoreCase("Y")){

                builder.append("StudentID: " + parts[0] + " Absent/Present: Present" );
                builder.append("\n");

            } else if(parts[1].equalsIgnoreCase("/")){

                builder.append("StudentID: " + parts[0] + " Absent/Present: Absent");
                builder.append("\n");

                }
            }
        }

        System.out.println(builder.toString());
    }

    public void viewSingularStudentsAttendence(String courseName, String classID, String date, String time, String studentID) throws IOException{
        String baseDir = "Registers";
        String classDir = Paths.get(baseDir, courseName).toString();
        String classIDDir = Paths.get(classDir, classID).toString();
        String dateDir = Paths.get(classIDDir, date).toString();
        String csvFileName = time + ".csv";
        Path csvFilePath = Paths.get(dateDir, csvFileName);

        if (Files.exists(csvFilePath)) {
        } else {
            System.out.println("Time record does not exist.");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath.toString()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(studentID)) {

                    if(parts[1].equals("Y")) {

                        System.out.println("Student ID: " + parts[0] + " Absent/Present: Present");

                    } else if(parts[1].equals("/")){
    
                        System.out.println("Student ID: " + parts[0] + " Absent/Present: Absent");

                    }
                }       
            }
        }
    }


    public double viewStudentOverallAttendance(String studentID) throws IOException {
        String baseDir = "Registers";
        List<Path> csvFiles = new ArrayList<>();
        collectCSVFiles(Paths.get(baseDir), csvFiles);

        int totalClasses = 0;
        int attendedClasses = 0;

        for (Path csvFile : csvFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile.toString()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(studentID)) {
                        totalClasses++;
                        if (parts[1].equalsIgnoreCase("Y")) {
                            attendedClasses++;
                        }
                        break;
                    }
                }
            }
        }

        if (totalClasses == 0) {
            return 0.0; // If the student ID was not found in any classes
        }

        return (attendedClasses / (double) totalClasses) * 100;
    }

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

    public void issueAttendanceWarning(String studentID) throws IOException {
        String filename = studentID + "AttendanceWarning.txt";
        String warningsFileName = "warnings.csv";
        boolean studentFound = false;
        List<String> lines = new ArrayList<>();

        // Read the warnings file
        if (Files.exists(Paths.get(warningsFileName))) {
            try (BufferedReader reader = new BufferedReader(new FileReader(warningsFileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(studentID)) {
                        int warnings = Integer.parseInt(parts[1]);
                        if (warnings >= 3) {
                            System.out.println("The student has already received the maximum number of warnings.");
                            return;
                        } else {
                            warnings++;
                            line = studentID + "," + warnings;
                        }
                        studentFound = true;
                    }
                    lines.add(line);
                }
            }
        }

        // If the student was not found, add them to the list with one warning
        if (!studentFound) {
            lines.add(studentID + ",1");
        }

        // Write back the updated warnings file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(warningsFileName))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }

        // Write the warning notice
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("==========================================================================\n");
            writer.write("                                                                          \n");
            writer.write(    "WARNING. STUDENT " + studentID + " YOU HAVE RECEIVED AN ATTENDANCE WARNING\n");
            writer.write("                                                                          \n");
            writer.write("     YOU MAY APPEAL THIS WARNING BY CONTACTING OUR ATTENDANCE TEAM        \n");
            writer.write("                                                                          \n");
            writer.write("    TO REMIND YOU, 3 WARNINGS MAY CONTRIBUTE TO A | PERMANENT EXCLUSION | \n");
            writer.write("                                                                          \n");
            writer.write("  We hope you can reflect on this matter and adjust yourself accordingly. \n");
            writer.write("                                                                          \n");
            writer.write("                Thank you. And Good luck.                                 \n");
            writer.write("==========================================================================\n");
        }
    }

    public double courseAverageAttendance(String courseName) throws IOException {
        String baseDir = "Registers";
        String courseDir = Paths.get(baseDir, courseName).toString();
        List<Path> csvFiles = new ArrayList<>();
        collectCSVFiles(Paths.get(courseDir), csvFiles);

        int totalClasses = 0;
        int totalAttendances = 0;

        for (Path csvFile : csvFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile.toString()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("StudentID")) {
                        continue; // Skip header line
                    }
                    String[] parts = line.split(",");
                    if (parts[1].equalsIgnoreCase("Y")) {
                        totalAttendances++;
                    }
                    totalClasses++;
                }
            }
        }

        if (totalClasses == 0) {
            return 0.0; // If no attendance records found
        }

        return (totalAttendances / (double) totalClasses) * 100;
    }

    public Set<String> collectStudentIDs(String classIDDir) {
        Set<String> studentIDs = new HashSet<>();
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
                            if (parts.length > 0) {
                                studentIDs.add(parts[0]);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        } catch (IOException e) {
            // If directories don't exist, return an empty set
            return new HashSet<>();
        }
        return studentIDs;
    }

   /*public static void main(String[] args) {
        AttendanceRecorder recorder = new AttendanceRecorder();
        try {
            double attendance = recorder.viewStudentOverallAttendance("S0012");
            System.out.println("Student's overall attendance: " + attendance + "%");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}

    
