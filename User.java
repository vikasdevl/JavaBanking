package banking;



public class User {
    private String accountNumber;
    private String username;
    private String password;
    private String email;
    private String phone;
    private boolean isActive;
    private double balance;
    private boolean haveTakenLoan;
    private Loan loanDetails;
    private Account accountType;
    private Transaction[] transactions;
    private boolean isAdmin;

    // Constructor
    public User(String accNo, String username, String password, String email, String phone, boolean isActive,
            double balance, boolean haveTakenLoan, Loan loanDetails, Account accType,
            Transaction[] transactions, boolean isAdmin) {
    this.accountNumber = accNo;
    this.username = username;
    this.password = password;
    this.email = email;
    this.phone = phone;
    this.isActive = isActive;
    this.balance = balance;
    this.haveTakenLoan = haveTakenLoan;
    this.loanDetails = loanDetails;
    this.accountType = accType;
    this.transactions = transactions;
    this.isAdmin = isAdmin;
}


    public User(String username, String password, String email, String phone) {

    	this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
	}

	// Getters
    public String getAccountNumber() { return accountNumber; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public boolean isActive() { return isActive; }
    public double getBalance() { return balance; }
    public boolean haveTakenLoan() { return haveTakenLoan; }
    public Loan getLoanDetails() { return loanDetails; }
    public Account getAccountType() { return accountType; }
    public Transaction[] getTransactions() { return transactions; }
    public boolean isAdmin() { return isAdmin; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setActive(boolean isActive) { this.isActive = isActive; }
    public void setBalance(double balance) { this.balance = balance; }
    public void setHaveTakenLoan(boolean haveTakenLoan) { this.haveTakenLoan = haveTakenLoan; }
    public void setLoanDetails(Loan loanDetails) { this.loanDetails = loanDetails; }
    public void setAccountType(Account accountType) { this.accountType = accountType; }
    public void setTransactions(Transaction[] transactions) { this.transactions = transactions; }
    public void setAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }
	public void setAccountNumber(String string) {
		this.accountNumber=string;
		
	}

    // Banking operations
    public boolean performDeposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            return true;
        }
        return false;
    }

    public boolean performWithdraw(double amount) {
        if (this.balance >= amount && amount > 0) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public String getTypeOfAccount() {
        return this.accountType.getClass().getSimpleName();
    }





}
