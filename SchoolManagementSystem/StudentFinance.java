import java.io.*;
import java.util.*;

public class StudentFinance extends FinancialAdvisor{

    public StudentFinance()throws IOException{}

    public void StudentsFinances(String filename, Scanner scanner) throws IOException{ 
        boolean loop = true;
        Map<String,Integer>map = new HashMap<>();
        map.put("StudentID",0);
        map.put("Income", 1);
        map.put("LoanID", 2);
        map.put("StudentLoan", 3);
        map.put("LoanPaidOff", 4 );
        map.put("RemainingLoan", 5);

        while(loop) {
            System.out.println("______________________________________________");
            System.out.println("| Please select:                             |");
            System.out.println("|____________________________________________|");
            System.out.println("|                                            |");
            System.out.println("| 1) To add a new student's finances         |");
            System.out.println("| 2) To view a student's finances            |");
            System.out.println("| 3) Update an existing students finances    |");
            System.out.println("| 4) Remove a student's finances             |");
            System.out.println("| 5) Go back                                 |");
            System.out.println("|____________________________________________|");
            System.out.println();
            System.out.println("Choice: ");
            int Choice = scanner.nextInt();
            scanner.nextLine();

            switch(Choice) {

                    case 1:

                    case 2:

                    case 3:
                            System.out.println();
                            System.out.println("Please enter the StudentsID: ");
                            String id = scanner.nextLine();
                            System.out.println();
                            System.out.println("Please select what you would like to change (type: 'StudentID, Income, LoanID, StudentLoan, LoanPaidOff, RemainingLoan')");
                            String change = scanner.nextLine();
                            int position = map.get(change);
                            System.out.println();
                            System.out.println("Finally, please select what you would like to change it to: ");
                            String toChangeTo = scanner.nextLine();
                            super.updateFile(filename,id,position,toChangeTo);
                            System.out.println("It's been updated. Thank you");
                            scanner.nextLine();

                    case 4: 
                            System.out.println("To remove a student please enter their ID: ");
                            System.out.println();
                            System.out.println("ID: ");
                            String studentID = scanner.nextLine();
                            removeStudentFinance(filename, studentID);



            }
        }
    }

    public static void removeStudentFinance(String filename, String studentID)throws IOException{
            File inputFile = new File(filename);
            File tempFile = new File("temp_" + filename);


            try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                String line;
                boolean studentFound = false;

                String header = reader.readLine();
                if(header != null && header.startsWith("StudentID")) {

                    writer.write(header);
                    writer.newLine();

                }

                while((line = reader.readLine()) != null) {

                    String [] details = line.split(",");
                    if(!details[0].equals(studentID)) {

                        writer.write(line);
                        writer.newLine();

                    } else {

                        studentFound = true;
                    }
                }

                if(!studentFound) {

                    System.out.println("There is no infomation present on the student: " + studentID);
                }
            }

        if(!inputFile.delete()) {

            System.err.println("Could not delete the original file");
            return;
        }

        if(!tempFile.renameTo(inputFile)) {

            System.err.println("Could not rename temp file");

        }

    }

    public static void writeToFilestudentsFinance(String filename, String studentID, double income, String loanID, double StudentLoan, double loadpaidOff) throws IOException {
        double remaining = StudentLoan - loadpaidOff;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {

            File file = new File(filename);
            if (file.length() == 0) {
                writer.write("StudentID,Income,LoanID,StudentLoan,LoanPaidOff,RemainingLoan");
                writer.newLine();
            }
            writer.append(String.join(",", studentID, String.valueOf(income), loanID, String.valueOf(StudentLoan), String.valueOf(loadpaidOff), String.valueOf(remaining)));
            writer.append("\n");


            } catch(IOException e) {
        }
    }

    public static void viewStudentsFinance(String filename, String studentsID) throws IOException {

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();

            if(!line.startsWith("StudentID") || line == null) {

                throw new IOException();
            }

            boolean studentFound = false;

            while((line = reader.readLine())!=null) {

                String [] details = line.split(",");

                if(details[0].equals(studentsID)) {

                    System.out.println("Student ID: " + details[0]);
                    System.out.println();
                    System.out.println("Income: £" + details[1]);
                    System.out.println();
                    System.out.println("Loan ID: " + details[2]);
                    System.out.println();
                    System.out.println("Student Loan: £" + details[3]);
                    System.out.println();
                    System.out.println("Loan Paid Off: £" + details[4]);
                    System.out.println();
                    System.out.println("Remaining Loan: £" + details[5]);
                    System.out.println();
                    studentFound = true;
                    break;
                }

            }


        }

    }

    public static void main(String [] args) throws IOException {

       //writeToFilestudentsFinance("StudentsFinance.csv","1234", 1500.00, "5467", 9250.00, 3000.00);
       //writeToFilestudentsFinance("StudentsFinance.csv","2348", 1500.00, "5467", 9250.00, 3000.00);
        //System.out.println("Successful");
        //viewStudentsFinance("StudentsFinance.csv", "1234");
        //removeStudentFinance("StudentsFinance.csv", "1234");
       // System.out.println("Successful");
    }
}
