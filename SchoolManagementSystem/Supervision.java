import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Supervision extends SchoolSystem {
    private static final String detentions = "Detentions.csv";

    public Supervision(){
        super();
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

    public void addStudentsDetention(String studentID, String detentionLocation, String detentionDate, String detentionTime, String detentionLength, String detentionSupervisor) throws IOException {
        File file = new File(detentions);

        if (!file.exists()) {
            // Create the file and parent directories if needed
            file.getParentFile().mkdirs();  // Ensures any necessary parent directories are created
            file.createNewFile();  // This creates the empty detentions.csv file
        }

        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(detentions, true))) {
        
             // For headers, if it's empty, we will need to add headers to it.
            if (file.length() == 0) {
                writer.write(String.join(",", "StudentID", "DetentionLocation", "DetentionDate", "DetentionTime", "DetentionLength", "DetentionSupervisor"));
                writer.newLine(); 
            }
            writer.write(String.join(",", studentID, detentionLocation, detentionDate, detentionTime, detentionLength, detentionSupervisor));
            writer.newLine(); 

            System.out.println("Detention record added successfully for student: " + studentID);
            
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Failed to add the detention record", e);
        }
    }

    public void addStudentsDetentionInfo(Scanner scanner) throws IOException{
        String detentionDate = null;
        String detentionTime = null;

        System.out.println();
        System.out.println("Please enter the following infomation: ");
        System.out.println();
        System.out.println("Please enter the studentsID: ");
        String studentID = scanner.nextLine();
        System.out.println();
        System.out.println("Now enter the location of this detention: ");
        String detentionLocation = scanner.nextLine();
        System.out.println();

        while(true){
            System.out.println("Next please enter the date of this detention: ");
            String date = scanner.nextLine();
            System.out.println();

            if(checkDate(date, false)){
                detentionDate = date;
                break;
            }
            else{
                System.out.println("Please enter a valid date");
            }
        }

        while(true){
            System.out.println("Now please enter the time at which this detention will occur (in the form HH:mm): ");
            String time = scanner.nextLine();
            System.out.println();

            if(checkTime(time)){
                detentionTime = time;
                break;
            }
            else{
                System.out.println("Please enter a valid time in the right form!");
            }
        }
        System.out.println("Penultimatley, please enter the length of the detention to be added: ");
        String detentionLength = scanner.nextLine();
        System.out.println();
        System.out.println("Finally, please enter the supervisor of this detention: type TBD (To Be Decided) if undecided");
        String detentionSupervisor = scanner.nextLine();
        System.out.println();
        addStudentsDetention(studentID, detentionLocation, detentionDate, detentionTime, detentionLength, detentionSupervisor);
    }

    public void UpdateStudentsDetentionInfo(Scanner scanner) throws IOException{

        System.out.println();
        System.out.println("Please type in which part you would like to update: ");
        System.out.println("You can choose from: (StudentID, DetentionLocation, DetentionDate, DetentionTime, DetentionLength, DetentionSupervision) ");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

    }

    public void DetentionsMenu(Scanner scanner) throws IOException{

        System.out.println("|                                |");
        System.out.println("| 1) Add A Students Detention    |");
        System.out.println("|                                |");
        System.out.println("| 2) Update A Students Detention |");
        System.out.println("|                                |");
        System.out.println("| 3) Remove A Students Detention |");
        System.out.println("|                                |");
        System.out.println("| 4) View A Students Detention   |");
        System.out.println("|                                |");
        System.out.println("| 5) View All Students Detentions|");
        System.out.println("|                                |");
        System.out.println("| 6) Issue Further Warning       |");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                     addStudentsDetentionInfo(scanner);
                     break;
        
            default:
                break;
        }


    }

    public void superVisionMenu(Scanner scanner) throws IOException{

        System.out.println("|                              |");
        System.out.println("| 1) Students Supervision menu |");
        System.out.println("|                              |");
        System.out.println("| 2) Teachers Supervision menu |");
        System.out.println("|                              |");
    }

    public void studentsSuperVisionMenu(Scanner scanner) throws IOException{
        System.out.println("|                                |");
        System.out.println("| 1) View Detentions Menu        |");
        System.out.println("|                                |");
        System.out.println("| 2) View messages from students |");
        System.out.println("|                                |");
        System.out.println("| 3) View behaviour logs         |");
        System.out.println("|                                |");
        System.out.println("| 4) Students Mental Health Menu |");
        System.out.println("|                                |");


    }

    public void teachersSuperVisionMenu(Scanner scanner) throws IOException{

        System.out.println("|                                          |");
        System.out.println("| 1) View Teachers Menu                    |");
        System.out.println("|                                          |");
        System.out.println("| 2) View Teachers Messages                |");
        System.out.println("|                                          |");
        System.out.println("| 3) View Any Teachers Complaints/reports  |");
        System.out.println("|                                          |");
        System.out.println("|                                          |");
    }

}
