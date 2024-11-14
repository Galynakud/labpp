package bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class Credit {
    private static final Logger fileLogger = LogManager.getLogger("FileOnlyLogger");
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");

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
        fileLogger.info("Created credit: " + name + " with amount: " + amount + ", interest rate: " + interestRate +
                "%, term: " + termMonths + " months, early repayment: " + earlyRepayment);
    }

    public String getName() {
        fileLogger.info("Accessing credit name: " + name);
        return name;
    }

    public double getAmount() {
        fileLogger.info("Accessing credit amount for: " + name + ", amount: " + amount);
        return amount;
    }

    public double getInterestRate() {
        fileLogger.info("Accessing interest rate for credit: " + name + ", interest rate: " + interestRate);
        return interestRate;
    }

    public int getTermMonths() {
        fileLogger.info("Accessing term months for credit: " + name + ", term: " + termMonths + " months");
        return termMonths;
    }

    public boolean isEarlyRepayment() {
        fileLogger.info("Checking early repayment status for credit: " + name + ", status: " + earlyRepayment);
        return earlyRepayment;
    }

    public void setName(String name) {
        fileLogger.info("Updating credit name from: " + this.name + " to: " + name);
        this.name = name;
    }

    public void setAmount(double amount) {
        fileLogger.info("Updating credit amount for: " + name + ", from: " + this.amount + " to: " + amount);
        this.amount = amount;
    }

    public void setInterestRate(double interestRate) {
        fileLogger.info("Updating interest rate for credit: " + name + ", from: " + this.interestRate + " to: " + interestRate);
        this.interestRate = interestRate;
    }

    public void setTermMonths(int termMonths) {
        fileLogger.info("Updating term months for credit: " + name + ", from: " + this.termMonths + " to: " + termMonths);
        this.termMonths = termMonths;
    }

    public void setEarlyRepayment(boolean earlyRepayment) {
        fileLogger.info("Updating early repayment status for credit: " + name + " to: " + earlyRepayment);
        this.earlyRepayment = earlyRepayment;
    }

    public void increaseAmount(double amount) {
        if (amount > 0) {
            this.amount += amount;
            fileLogger.info("Increased credit amount for: " + name + " by: " + amount + ", new amount: " + this.amount);
        } else {
            errorLogger.error(ERROR_MARKER, "Attempted to increase credit amount for: " + name + " by a non-positive amount: " + amount);
        }
    }

    public void increaseCreditLine(int additionalTerm) {
        if (additionalTerm > 0) {
            this.termMonths += additionalTerm;
            fileLogger.info("Increased term months for credit: " + name + " by: " + additionalTerm + ", new term: " + this.termMonths + " months");
        } else {
            errorLogger.error(ERROR_MARKER, "Attempted to increase term months for credit: " + name + " by a non-positive value: " + additionalTerm);
        }
    }
}
