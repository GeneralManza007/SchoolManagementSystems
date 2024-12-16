import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class StudentFinancialReport {
    private String title;
    private String studentID;
    private String date;
    private String author;
    private String summary;
    private int savingsGoal;
    private int currentSavings;
    private double deptedAmount;
    private double deptPaidOff;
    private List<String> recomendations;
    private List<String>appendencies;
    private Budget budget;

    public StudentFinancialReport(String title, String studentID, String date, String author, String summary, int savingsGoal, int currentSavings, double deptedAmount, double deptPaidOff, List<String>recommendations, List<String>appendcies, Budget budget) {
        this.title = title;
        this.studentID = studentID;
        this.date = date;
        this.author = author;
        this.summary = summary;
        this.savingsGoal = savingsGoal;
        this.currentSavings = currentSavings;
        this.deptedAmount = deptedAmount;
        this.deptPaidOff = deptPaidOff;
        this.recomendations = recommendations;
        this.appendencies = appendcies;
        this.budget = budget;
    }

    public void writeReportToFile(String filename) throws IOException {

        double totalExpenses = budget.getTotalExpenses();
        double netIncome = budget.getIncome() - totalExpenses;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            writer.write("Financial Report \n");
            writer.write("================" + "\n");
            writer.write("Title: " + title + "\n");
            writer.write("Student ID: " + studentID + "\n");
            writer.write("Date: " + date + "\n");
            writer.write("Author: " + author + "\n\n");
            writer.write("Financial Summary\n");
            writer.write("----------------\n");
            writer.write("Total Income: " + budget.getIncome() + "\n");
            writer.write("Total Expenses: " + budget.getTotalExpenses() + "\n");
            writer.write("Net Income: " + netIncome + "\n\n");
            writer.write("Savings Goal: " + savingsGoal + "\n");
            writer.write("Current Savings: " + currentSavings + "\n");
            writer.write("Dept Amount: " + deptedAmount + "\n\n");
            writer.write("Dept Paid Off: " + deptPaidOff + "\n\n");
            writer.write("Budget Analysis\n");
            writer.write("-----------------\n");
            writer.write("Budgeted Income: " + budget.getIncome() + "\n");
            writer.write("Total Budget Expenses: " + totalExpenses + "\n");
            writer.write("Budget Balance: " + netIncome + "\n");
            writer.write("Detailed Budgeted Expenses:\n");
            for(Map.Entry<String,Double> entry : budget.getExpenses().entrySet()) {

                    writer.write(entry.getKey() + ": " + entry.getValue() + "\n");

            }

            writer.write("\nRecommendations:\n");
            for(String recomendation: recomendations) {

                writer.write("- " + recomendation + "\n");
            }
            writer.write("\nAppendicies:\n");
            for(String appendix : appendencies){

                writer.write("- " + appendix + "\n");
            }
 
        } catch(IOException e) {}


    }


public static void main(String[] args) {
    List<String> recommendations = Arrays.asList("Reduce food expenses", "Save more", "Invest in stocks");
    List<String> appendices = Arrays.asList("Transaction list", "Budget plan", "Savings progress");

    Budget budget = new Budget(1500);
    budget.addExpense("Housing", 500);
    budget.addExpense("Food", 300);
    budget.addExpense("Transportation", 200);

    StudentFinancialReport report = new StudentFinancialReport(
        "Student Financial Report",
        "S1",
        "2024-06-05",
        "John Doe",
        "This report provides an overview of the financial status of the student.",
        1000,
        500,
        300,
        100,
        recommendations,
        appendices,
        budget
    );

    try {
        report.writeReportToFile("FinancialReport_S1.txt");
        System.out.println("Report generated successfully.");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}

