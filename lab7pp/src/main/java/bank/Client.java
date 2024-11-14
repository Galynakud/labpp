package bank;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class Client {
    private static final Logger fileLogger = LogManager.getLogger("FileOnlyLogger");
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");

    private final String name;
    private final String password;
    private final double initialAmount;
    private final List<Credit> selectedCredits;

    public Client(String name, String password, double initialAmount) {
        this.name = name;
        this.password = password;
        this.initialAmount = initialAmount;
        this.selectedCredits = new ArrayList<>();
        fileLogger.info("Created client: " + name + " with initial amount: " + initialAmount);
    }

    public String getName() {
        fileLogger.info("Accessing client's name: " + name);
        return name;
    }

    public String getPassword() {
        errorLogger.warn(ERROR_MARKER, "Accessed password for client: " + name); // Log as a warning for security
        return password;
    }

    public double getInitialAmount() {
        fileLogger.info("Accessing initial amount for client: " + name);
        return initialAmount;
    }

    public void takeCredit(Credit selectedCredit) {
        selectedCredits.add(selectedCredit);
        fileLogger.info("Client " + name + " took credit: " + selectedCredit.getName() + " with amount: " + selectedCredit.getAmount());
    }

    public void repayCredit(Credit selectedCredit) {
        if (selectedCredits.remove(selectedCredit)) {
            fileLogger.info("Client " + name + " repaid credit: " + selectedCredit.getName());
        } else {
            errorLogger.error(ERROR_MARKER, "Client " + name + " attempted to repay a credit that they do not have: " + selectedCredit.getName());
        }
    }

    public void increaseCredit(Credit selectedCredit, double additionalAmount) {
        if (selectedCredits.contains(selectedCredit)) {
            selectedCredit.increaseAmount(additionalAmount);
            fileLogger.info("Client " + name + " increased credit: " + selectedCredit.getName() + " by amount: " + additionalAmount);
        } else {
            errorLogger.error(ERROR_MARKER, "Client " + name + " attempted to increase a credit that they do not have: " + selectedCredit.getName());
        }
    }

    public List<Credit> getSelectedCredits() {
        fileLogger.info("Accessing selected credits for client: " + name);
        return new ArrayList<>(selectedCredits);
    }

    public Credit getCreditByName(String creditName) {
        for (Credit credit : selectedCredits) {
            if (credit.getName().equalsIgnoreCase(creditName)) {
                fileLogger.info("Found credit by name: " + creditName + " for client: " + name);
                return credit;
            }
        }
        errorLogger.warn(ERROR_MARKER, "Credit with name: " + creditName + " not found for client: " + name);
        return null;
    }
}
