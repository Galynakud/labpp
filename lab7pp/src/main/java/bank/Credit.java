package bank;
public class Credit {
    private String name;
    private double amount;
    private double interestRate;
    private int termMonths;
    private boolean earlyRepayment;
    public Credit(String name, double amount, double interestRate, int termMonths, boolean earlyRepayment) {
        this.name = name;
        this.amount = amount;
        this.interestRate = interestRate;
        this.termMonths = termMonths;
        this.earlyRepayment = earlyRepayment;
    }
    public String getName() {
        return name;
    }
    public double getAmount() {
        return amount;
    }
    public double getInterestRate() {
        return interestRate;
    }
    public int getTermMonths() {
        return termMonths;
    }
    public boolean isEarlyRepayment() {
        return earlyRepayment;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    public void setTermMonths(int termMonths) {
        this.termMonths = termMonths;
    }
    public void setEarlyRepayment(boolean earlyRepayment) {
        this.earlyRepayment = earlyRepayment;
    }
    public void increaseAmount(double amount) {
        this.amount += amount;
    }

    public void increaseCreditLine(int i) {

    }
}