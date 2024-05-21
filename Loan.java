package banking;

public abstract class Loan {
    protected double principalAmount;
    protected double interestRate;
    protected int tenure;

    // Constructor
    public Loan(double principalAmount, double interestRate, int tenure) {
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.tenure = tenure;
    }

    // Getters and Setters
    public double getPrincipalAmount() { return principalAmount; }
    public double getInterestRate() { return interestRate; }
    public int getTenure() { return tenure; }

    public void setPrincipalAmount(double principalAmount) { this.principalAmount = principalAmount; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }
    public void setTenure(int tenure) { this.tenure = tenure; }
}

class EducationLoan extends Loan {
    public EducationLoan(double principalAmount, double interestRate, int tenure) {
        super(principalAmount, interestRate, tenure);
    }
}

class PersonalLoan extends Loan {
    public PersonalLoan(double principalAmount, double interestRate, int tenure) {
        super(principalAmount, interestRate, tenure);
    }
}
