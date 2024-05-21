package banking;

// Define the abstract base class for different types of accounts
public abstract class Account {
    protected double balance;

    // Constructor to initialize the account with a starting balance
    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    // Abstract methods to be implemented by specific account types
    public abstract void deposit(double amount);
    public abstract boolean withdraw(double amount);

    // Getters and setters
    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

// Implementation of a Savings Account
class SavingsAccount extends Account {
    public SavingsAccount() {
        super(0);
    }

    @Override
    public void deposit(double amount) {
        // Deposit logic for savings account
        this.balance += amount;
    }

    @Override
    public boolean withdraw(double amount) {
        // Withdrawal logic for savings account, ensure sufficient balance
        if (amount <= this.balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
}

// Implementation of a Salary Account
class SalaryAccount extends Account {
    public SalaryAccount() {
        super(0);
    }

    @Override
    public void deposit(double amount) {
        // Deposit logic for salary account
        this.balance += amount;
    }

    @Override
    public boolean withdraw(double amount) {
        // Withdrawal logic for salary account, no restrictions other than balance
        if (amount <= this.balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
}

// Implementation of a Current Account
class CurrentAccount extends Account {
    public CurrentAccount() {
        super(0);
    }

    @Override
    public void deposit(double amount) {
        // Deposit logic for current account
        this.balance += amount;
    }

    @Override
    public boolean withdraw(double amount) {
        // Current account might allow overdrafts or might have specific rules
        if (amount <= this.balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
}
