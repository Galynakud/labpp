package bank;

import java.util.List;

public class Client {
    private final String name;
    private final String password;
    private final double initialAmount;

    public Client(String name, String password, double initialAmount) {
        this.name = name;
        this.password = password;
        this.initialAmount = initialAmount;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public double getInitialAmount() {
        return initialAmount;
    }

    public void takeCredit(Credit selectedCredit) {
    }

    public void repayCredit(Credit selectedCredit) {
    }

    public void increaseCredit(Credit selectedCredit, double additionalAmount) {
    }

    public List<Credit> getSelectedCredits() {
        return List.of();
    }

    public Object getCreditByName(String споживчийКредит) {
        return null;
    }
}