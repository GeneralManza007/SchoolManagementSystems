import java.time.LocalDate;

public class Payment {
    private String paymentID;
    private String studentID;
    private double amount;
    private LocalDate date;
    
    public Payment(String paymentID, String studentID, double amount, LocalDate date ) {
        this.paymentID = paymentID;
        this.studentID = studentID;
        this.amount = amount;
        this.date = date;
    }

    public String getPaymentID() {return this.paymentID;}
    
    public String getStudentID() {return this.studentID;}

    public double getAmount() {return this.amount;}

    public LocalDate getDate() {return this.date;}
}
