import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.io.*;

public class Budget {
    private double income;
    private Map<String, Double> expenses;

    public Budget(double income) {

        this.income = income;
        this.expenses = new HashMap<>();
    }

    public Budget() {

        this.expenses = new HashMap<>();
    }

    public double getIncome() {return this.income;}

    public void addExpense(String catergory, double amount ){

        this.expenses.put(catergory, this.expenses.getOrDefault(catergory, 0.0) + amount);
    }

    public Double getTotalExpenses(){

        return this.expenses.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    public Map<String, Double> getExpenses() {

        return this.expenses;
    }

    public void saveToFile(String filename) throws IOException {

        try(PrintWriter writer = new PrintWriter(new FileWriter(filename))) {

            writer.println("Income; " + income);

            for(Map.Entry<String, Double> entry : expenses.entrySet()) {

                writer.println(entry.getKey() + "; " + entry.getValue());
            }
        }
    }

    public static Budget loadFromFile(String filename) throws IOException {

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            String line = reader.readLine();
            if(line == null || !line.startsWith("Income;")) {

                throw new IOException("Invalid file format");

            }

            double income = Double.parseDouble(line.split(";")[1].trim());
            Budget budget = new Budget(income);

            while((line = reader.readLine()) != null) {

                String [] parts = line.split(";");
                if(parts.length == 2) {

                    String category = parts[0].trim();
                    double amount = Double.parseDouble(parts[1].trim());
                    budget.addExpense(category, amount);

                }

            }

            return budget;
        }
    }
}
