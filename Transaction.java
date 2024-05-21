package banking;

public class Transaction {
    private String transactionId;
    private String date;
    private double amount;
    private String type;

    // Constructor
    public Transaction(String transactionId, String date, double amount, String type) {
        this.transactionId = transactionId;
        this.date = date;
        this.amount = amount;
        this.type = type;
    }

    // Getters
    public String getTransactionId() { return transactionId; }
    public String getDate() { return date; }
    public double getAmount() { return amount; }
    public String getType() { return type; }

    // Setters
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public void setDate(String date) { this.date = date; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setType(String type) { this.type = type; }
}
