import java.text.NumberFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;
import java.util.random.*;


public class FinancialAdvisor extends SchoolSystem {
    private static final String FILENAME = Occupation.FINANCIALADVISOR.getOccupation() + ".txt";
    private static final String financialsFilename = "Financial.csv";
    private static final String In = "In";
    private static final String Out = "Out";
    private static final String sltToDoList = "sltToDoList.txt";
    private static final String studentsFinance = "StudentsFinance.csv";
    private Map<String, Payment> payments;

public FinancialAdvisor(){

    super();
    payments = new HashMap<>();
}   

public int getTotal(String filename) throws IOException {
    int total = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] words = line.split(",");
            if (words.length >= 5) {
                try {
                    if (words[1].equals("In")) {
                        total += Integer.parseInt(words[3]);
                    } else if (words[1].equals("Out")) {
                        total -= Integer.parseInt(words[3]);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format in line: " + line);
                }
            } else {
                System.err.println("Invalid line format: " + line);
            }
        }
    }
    return total;
}

public int getTotalfrom(String filename, String direction) throws IOException {
    int total = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] words = line.split(",");
            if (words.length >= 5 && words[1].equalsIgnoreCase(direction)) {
                try {
                    total += Integer.parseInt(words[3]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format in line: " + line);
                }
            }
        }
    }
    return total;
}


public void updateFile(String filename, String ID, int position, String toChangeTo) throws IOException {
    List<String> lines = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
    }

    for (int i = 0; i < lines.size(); i++) {
        String line = lines.get(i);
        String[] words = line.split(",");
        if (words.length > 2 && words[0].equals(ID)) {
            if (position >= 0 && position < words.length) {
                words[position] = toChangeTo;
                lines.set(i, String.join(",", words));
            }
        }
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
        for (String updatedLine : lines) {
            bw.write(updatedLine);
            bw.newLine();
        }
    }
}



public static String formatNumberWithCommas(int number) {

    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.UK);
    return numberFormat.format(number);
}

public void createBudgetPlan(String studentID, Scanner scanner)throws IOException{

    System.out.println("Firstly, Please enter your monthly income: ");
    double income = scanner.nextDouble();
    scanner.nextLine();

    Budget budget = new Budget(income);

    System.out.println();
    System.out.println("Next, please enter your expenses (type 'done' to finish): ");

    while(true) {

        System.out.println("Enter expense category (e.g., Housing, Food, Transportation) or 'done' to finish: ");
        String category = scanner.nextLine();
        if(category.equalsIgnoreCase("done")) {

            break;

        }

        System.out.println("Enter amount for " + category + ": ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        budget.addExpense(category, amount);
    }

    String filename = studentID + "_budget.csv";
    budget.saveToFile(filename);
}

public void loadBudgetPlan(String studentID) throws IOException{

    String filename = studentID + "_budget.csv";
    Budget budget = Budget.loadFromFile(filename);
    displayBudgetSummary(studentID, budget);

}

public void displayBudgetSummary(String studentID, Budget budget){

    double totalExpenses = budget.getTotalExpenses();
    double balance = budget.getIncome() - totalExpenses;

    System.out.println("\nBudget Summary for Student ID: " + studentID);
    System.out.println("Total Income: £" + budget.getIncome());
    System.out.println("Total Expenses: £" + budget.getTotalExpenses());
    System.out.println("Balance: £" + balance);

    System.out.println("\n Detailed Expenses:");
    for(Map.Entry<String,Double>entry : budget.getExpenses().entrySet()) {

        System.out.println(entry.getKey() + ": £" + entry.getValue());
    }

    if(balance < 0) {

        System.out.println("You are overspending. Consider reducing your expenses");

    } else {

            System.out.println("\n You are within your budget. Good job!");

    }


}

public void displayInfo(String filename) throws IOException{

    System.out.println("The total in today was seen to be: " + formatNumberWithCommas(getTotalfrom(filename, In)));
    System.out.println();
    System.out.println("The total seen to be going out today was: " + formatNumberWithCommas(getTotalfrom(filename, Out)));
    System.out.println();
    System.out.println("A Summary is preseneted below: ");


    try(Scanner scanner = new Scanner(new File(filename))){

        while(scanner.hasNext()) {

                String line = scanner.nextLine();
                String [] words = line.split("; ");

                if(words.length > 2) {

                if(words[1].equalsIgnoreCase(In)) {
                    
                    System.out.println(formatNumberWithCommas(Integer.parseInt(words[3])) + " came in from " + words[2]);
                    System.out.println();

                } else {
                        System.out.println(formatNumberWithCommas(Integer.parseInt(words[3])) + " went to " + words[2]);
                        System.out.println();
                    }
                }
            }
        }

        System.out.println("And so, we started the day with: " + formatNumberWithCommas(getTotalfrom(filename, In)));
        System.out.println();
        System.out.println("Finally the total after everything came in and left was: " + formatNumberWithCommas(getTotal(filename)));
    }

    public void budgetMenu(Scanner scanner) throws IOException {
        boolean loop = true;
        while(loop) {
            try{

        System.out.println("Welcome to creating a budget, please select what you would like to do: ");
        System.out.println();
        System.out.println("1) Create a budget planner for a student ");
        System.out.println("2) View a students budget planner ");
        System.out.println("3) Go back");
        int Choice = scanner.nextInt();
        scanner.nextLine();

        switch(Choice) {

            case 1:
                    System.out.println("Please enter the students ID: ");
                    String iD = scanner.nextLine();
                    createBudgetPlan(iD, scanner);
                    break;
            
            case 2: 
                    System.out.println("Please enter the ID of the student you want to see: ");
                    String id = scanner.nextLine();
                    System.out.println();
                    loadBudgetPlan(id);
                    break;
            
            case 3:
                    loop = false;
                    break;
        }

            } catch(InputMismatchException e) {}


        }
    }

    public void addPayment(Payment payment){

        payments.put(payment.getPaymentID(), payment);
    }

    public void generateReceipt(String paymentID) throws IOException {

        Payment payment = payments.get(paymentID);

        if(payment == null) {

            throw new IllegalArgumentException();

        }

        String receipt = formatReceipt(payment);

        try(FileWriter writer = new FileWriter(paymentID + "_receipt.txt")) {

            writer.write(receipt);
        }

        System.out.println("Receipt Generated: " + paymentID + "_receipt.txt");

    }

    private String formatReceipt(Payment payment) {

        StringBuilder receipt = new StringBuilder();
        receipt.append("Receipt\n");
        receipt.append("=======\n");
        receipt.append("Payment ID: ").append(payment.getPaymentID()).append("\n");
        receipt.append("Student ID: ").append(payment.getStudentID()).append("\n");
        receipt.append("Amount: £").append(String.format("%.2f", payment.getAmount())).append("\n");//append(formatNumberWithCommas(Integer.parseInt(String.format("%.2f", payment.getAmount())))).append("\n");
        receipt.append("Date: ").append(payment.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n");
        receipt.append("\nThank you for your payment! \n");
        
        return receipt.toString();
    }

    public void viewReciept(String paymentID) throws IOException {
        String filename = paymentID + "_receipt.txt";

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while((line = reader.readLine()) != null) {

                System.out.println(line);

                } 

            } catch(FileNotFoundException e) {

                System.out.println("Receipt not found for Payment ID: " + paymentID);
        }
    }

    public void receiptMenu(Scanner scanner, boolean student) throws IOException {
        boolean loop = false;
        Random rand  = new Random();

        while(loop) {

            try{

                System.out.println("Please select: ");
                System.out.println();
                System.out.println("1) Generate New receipt (Only available if you are a financial advisor)");
                System.out.println("2) View Receipt");
                System.out.println("3) Go back");
                System.out.println();
                int Choice = scanner.nextInt();
                scanner.nextLine();

                if(Choice == 1 && !student) {

                            System.out.println("Please enter the following infomation: ");
                            System.out.println();
                            Double num = rand.nextDouble();
                            String paymentID = num.toString();
                            System.out.println("StudentID: ");
                            String studentID = scanner.nextLine();
                            System.out.println();
                            System.out.println("Amount: ");
                            Double amount = scanner.nextDouble();
                            scanner.nextLine();
                            LocalDate date = LocalDate.now();

                            Payment payment = new Payment(paymentID, studentID, amount, date);
                            addPayment(payment);
                            generateReceipt(paymentID);
                    
                    } else if(Choice == 2) {

                            System.out.println("Please enter the PaymentID of the reciept you would like to view: ");
                            System.out.println();
                            System.out.println("PaymentID:");
                            String paymentID = scanner.nextLine();
                            viewReciept(paymentID);


                } else if (Choice == 3) {
                    
                    loop = false;
                
                } else {System.out.println("Please enter a valid choice");}


            } catch(InputMismatchException e) {}
        }
    }

    public boolean validateID(String filename, String ID) throws IOException {

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while((line = reader.readLine()) != null) {

                String [] words = line.split(",");

                if(words[0].equalsIgnoreCase(ID)) {

                    return true;

                }
            }
        }

        return false;
    }

    public void addTransaction(String filename, String id, String direction, String source, int amount, LocalDate date) throws IOException{

        try(FileWriter writer = new FileWriter(filename, true)) {

            writer.append(String.join(",", id, direction, source, String.valueOf(amount), date.toString()));
            writer.append("\n");

        } catch(IOException e) {e.printStackTrace();}

    }

    public void viewTransactions(String filename, String ID, Boolean all) throws IOException {

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
 
            if(all) {
                        while((line = reader.readLine()) != null) {

                            String [] words = line.split(",");

                            if(words.length >= 5) {

                                    System.out.println();
                                    System.out.println("ID: " + words[0]);
                                    System.out.println();
                                    if(words[1].equals("In")) {System.out.println("The payment is coming In");}
                                    else if(words[1].equals("Out")) {System.out.println("The payment is going Out");}
                                    System.out.println();
                                    System.out.println("Source: " + words[2]);
                                    System.out.println();
                                    System.out.println("Date transfered: " + words[4]);
                                    System.out.println();
                                    if(words[1].equals("In")) {System.out.println("Amount recieved: £" + words[3]);}
                                    else if(words[1].equals("Out")) {System.out.println("Amount transfered: £" + words[3]);}
                                    System.out.println();
                                }
                            }
                } else {

                        while((line = reader.readLine()) != null) {

                            String [] words = line.split(",");

                            if(words.length >= 5) {

                                if(words[0].equals(ID)) {

                                    System.out.println();
                                    System.out.println("ID: " + words[0]);
                                    System.out.println();
                                    if(words[1].equals("In")) {System.out.println("The payment is coming In");}
                                    else if(words[1].equals("Out")) {System.out.println("The payment is going Out");}
                                    System.out.println();
                                    System.out.println("Source: " + words[2]);
                                    System.out.println();
                                    System.out.println("Date transfered: " + words[4]);
                                    System.out.println();
                                    if(words[1].equals("In")) {System.out.println("Amount recieved: £" + words[3]);}
                                    else if(words[1].equals("Out")) {System.out.println("Amount transfered: £" + words[3]);}
                                    System.out.println();

                        }
                    }
                }
            }
        }
    }

    public void manageTransactionsMenu(Scanner scanner) throws IOException {
        if(!super.checkFileExists(financialsFilename)) {

            super.createFile(financialsFilename);
    
        }
        Map<String, Integer>map = new HashMap<>();
        map.put("Direction", 1);
        map.put("Source", 2);
        map.put("Amount", 3);
        map.put("Date", 4);
        boolean loop = true;
        Random rand = new Random();

        while(loop) {

            try {
    
                System.out.println("Please select: ");
                System.out.println();
                System.out.println("1) Add a transaction");
                System.out.println("2) View a singular transaction");
                System.out.println("3) View all transactions");
                System.out.println("4) Edit a transaction");
                System.out.println("5) Go back");

                System.out.println();
                System.out.println("Choice: ");
                int Choice = scanner.nextInt();
                scanner.nextLine();

                switch (Choice) {

                    case 1:
                            System.out.println("Please enter the following infomation: ");
                            System.out.println();
                            int num = rand.nextInt(0, 150000);
                            String ID = Integer.toString(num);
                            System.out.println("Is this payment going IN or OUT?");
                            String direction = scanner.nextLine();
                            System.out.println();
                            System.out.println("What is the purpose of this transaction?: ");
                            String purpose = scanner.nextLine();
                            System.out.println();
                            System.out.println("And finally the amount involved: ");
                            int amount = scanner.nextInt();
                            scanner.nextLine();
                            LocalDate date = LocalDate.now();
                            addTransaction(financialsFilename, ID, direction, purpose, amount, date);
                            break;

                    case 2:
                            System.out.println("Please enter the Payment ID: ");
                            System.out.println();
                            ID = scanner.nextLine();
                            viewTransactions(financialsFilename, ID, false);
                            scanner.nextLine();
                            break;

                    case 3:
                            System.out.println("Okay no problem, here are all the transactions: ");
                            viewTransactions(financialsFilename, null, true);
                            scanner.nextLine();
                            break;

                    case 4:
                            String id = null;
                            System.out.println("Please enter the ID of the transaction you would like to edit: ");
                            System.out.println();

                            while(true) {
                            System.out.println("ID: ");
                            ID = scanner.nextLine();

                            if(validateID(financialsFilename, ID)) {
                                id = ID;
                                break;

                                } else {System.out.println("Please enter a valid ID");}
                            }
                            System.out.println("Please enter what you would like to edit: ");
                            System.out.println("Options are: Direction (In or Out)");
                            System.out.println("Source, Amount, Date");
                            String choice = scanner.nextLine();
                            int position = map.get(choice);
                            System.out.println();
                            System.out.println("Thanks, now enter what you would like to change that too: ");
                            System.out.println();
                            String changeTo = scanner.nextLine();
                            updateFile(financialsFilename, ID, position, changeTo);
                            break;

                    case 5:
                            loop = false;
                            break;
                
                    default:
                        break;
                }


                } catch(InputMismatchException e) {
            }
        }
    }

    public List<String> getItems(String filename, String StudentID) throws IOException {
        List<String>lst = new ArrayList<>();
        boolean studentFound = false;

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();  

            if (line == null || !line.startsWith("StudentID")) {
                throw new IOException("Invalid file format or empty file");
            }

            while((line = reader.readLine())!= null) {

                String [] details = line.split(",");

                if(details[0].equals(StudentID)) {

                    lst.add(details[1]);
                    lst.add(details[5]);
                    lst.add(details[4]);
                    
                    studentFound = true;
                    
                }

                if(studentFound) {
                    break;
                }
            }
        }
        return lst;
    }

    /*private String title;
    private String studentID;
    private String date;
    private String author;
    private String summary;
    private int savingsGoal;
    private int currentSavings;
    private int deptedAmount;
    private int deptPaidOff;
    private List<String> recomendations;
    private List<String>appendencies;
    private Budget budget; */

    public void generateStudentFinancialReport(String filename, Scanner scanner) throws IOException{
        String studentID = null;
        Double deptedAmount = 0.0;
        Double deptpaidOff = 0.0;
        int savingsgoal = 0;
        int currentsavings = 0;

        System.out.println("Please enter the title of the report: ");
        String title = scanner.nextLine();
        System.out.println();
        LocalDate now = LocalDate.now();
        String date = now.toString();
        System.out.println("Please enter the studentsID: ");

        while(true){

            System.out.println("ID: ");
            String ID = scanner.nextLine();

            if(validateID(filename, ID)) {

                System.out.println();
                studentID = ID;
                break;

            }
        }

        System.out.println("Please enter the name of the author of said report: or type nothing to use default: ");
        String author = scanner.nextLine().trim();
        String basicAuthor = "Rachel Smith";
        author = author.isEmpty() ? basicAuthor : author;
        System.out.println();
        System.out.println("Please enter a short summary of what this report will be about else just leave it blank: ");
        System.out.println();
        String summary = scanner.nextLine().trim();
        String basicSummary = "This report provides an overview of the financial status of the student.";
        summary = summary.isEmpty() ? basicSummary : summary;
        System.out.println();
        while(true){
            try{
                System.out.println("Please enter the students savings goal: ");
                int savingsGoal = scanner.nextInt();
                scanner.nextLine();
                savingsgoal = savingsGoal;
                break;

            } catch(InputMismatchException e) {System.out.println("Please enter an int!");}

        }
        System.out.println();

        while(true) {

            try{
                    System.out.println("Please enter the student's current savings: ");
                    int currentSavings = scanner.nextInt();
                    scanner.nextLine();
                    currentsavings = currentSavings;
                    break;

            } catch(InputMismatchException e) {System.out.println("Please enter an int!");}

        }
        System.out.println();
        List<String> lst = getItems(filename, studentID);

        deptedAmount = Double.parseDouble(lst.get(1));
        deptpaidOff = Double.parseDouble(lst.get(2));

        List<String>recomendations = new ArrayList<>();
        System.out.println("Please enter recomendations for the student based on their finances: (type 'done' to continue)");
        while(true) {

            System.out.println("Recomendation: ");
            String recomendation = scanner.nextLine();

            if(recomendation.equalsIgnoreCase("done")) {
                
                System.out.println("Appreciate that!");
                break;
            
            }
            recomendations.add(recomendation);
        }

        List<String>appendencies = new ArrayList<>();
        System.out.println("Now please enter any appendices type 'done' when you're finished: ");
        while(true) {

            System.out.println("Appendencies: ");
            String appendenice = scanner.nextLine();

            if(appendenice.equalsIgnoreCase("done")) {
    
                break;

            }

            appendencies.add(appendenice);
        }

        String Bugfilename = studentID + "_budget.csv";
        Budget budget = Budget.loadFromFile(Bugfilename);
        String FileName = "FinancialReport_" + studentID + ".txt";
        
        StudentFinancialReport report = new StudentFinancialReport(title, studentID, date, author, summary, savingsgoal, currentsavings, deptedAmount, deptpaidOff, recomendations, appendencies, budget);
        report.writeReportToFile(FileName);
    }






public void Menu(Scanner scanner) throws IOException {
    boolean loop = true;

    while(loop) {
    
    System.out.println("___________________________________");
    System.out.println("| Welcome to the Financial's page |");
    System.out.println("|_________________________________|");
    System.out.println("|                                 |");
    System.out.println("| Select:                         |");
    System.out.println("|                                 |");
    System.out.println("| 1) Financial acitivies          |");
    System.out.println("| 2) Create Budget Plan           |");
    System.out.println("| 3) Generate a receipt           |");
    System.out.println("| 4) Manage Transactions          |");
    System.out.println("| 5) View SLT toDoList            |");
    System.out.println("| 6) Generate a Financials Report |");
    System.out.println("| 7) Mangage Students Finance     |");
    System.out.println("| 8) Go back                      |");
    System.out.println("___________________________________");
    System.out.println("Please select your choice: ");
    int Choice = scanner.nextInt();
    scanner.nextLine();

    switch(Choice) {

        case 1:
                displayInfo(financialsFilename);
                break;
        case 2:
                budgetMenu(scanner);
                break;
                
        case 3:
                receiptMenu(scanner, false);
                break;


        case 4:
                manageTransactionsMenu(scanner);
                break;
        case 5:
                ToDoList.ToDo(sltToDoList);
                break;


        case 6:
                generateStudentFinancialReport(studentsFinance, scanner);
                break;

        case 7: 
                StudentFinance finance = new StudentFinance();
                finance.StudentsFinances(studentsFinance, scanner);

        case 8:
                loop = false;
                break;

        default:

    }
    
    }

}

public void financalAdvisor(Scanner scanner) throws IOException{

    try{

        while(true) {

            System.out.println("Welcome to the Financial Advisor section: ");
            System.out.println();
            System.out.println("Please select: ");
            System.out.println();
            System.out.println("1) Login: ");
            System.out.println();
            System.out.println("2) Go back");
            System.out.println();
            System.out.println("Choice: ");
            int Choice = scanner.nextInt();
            scanner.nextLine();

            if(Choice == 1) {

                System.out.println(FILENAME);
                super.login(scanner, FILENAME);

                Menu(scanner);

            } else if(Choice == 2){ 

                break;

            } else {
                        System.out.println("Please enter a valid choice: ");
                    }
                }

            }catch(InputMismatchException e) {
        }
    } 
    /*public static void main(String [] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        FinancialAdvisor financalAdvisor = new FinancialAdvisor();
        financalAdvisor.Menu(scanner);
        //financalAdvisor.manageTransactionsMenu(scanner);
        //financalAdvisor.financalAdvisor(scanner);
        //String ID = "2356";
       // financalAdvisor.createBudgetPlan(ID, scanner);
        //financalAdvisor.loadBudgetPlan(ID);

        /*financalAdvisor.addPayment(new Payment("12345", "5123", 1500.00, LocalDate.now()));
        financalAdvisor.generateReceipt("12345");
        financalAdvisor.viewReciept("12345");

    }*/
}