package bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private static final Logger fileLogger = LogManager.getLogger("FileOnlyLogger");
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");

    private final List<Credit> credits;
    private final List<Client> clients;

    public Bank() {
        this.credits = new ArrayList<>();
        this.clients = new ArrayList<>();

        fileLogger.info("Creating Bank instance and initializing default credits and clients.");

        credits.add(new Credit("Споживчий кредит", 5000, 5.5, 24, true));
        credits.add(new Credit("Авто кредит", 10000, 6.0, 36, true));
        // інші кредити...

        clients.add(new Client("Іван Петренко", "1234", 5000));
        clients.add(new Client("Олена Коваленко", "5678", 7000));
        // інші клієнти...

        fileLogger.info("Bank instance created with default credits and clients.");
    }

    public List<Credit> getCredits() {
        fileLogger.info("Accessing list of credits.");
        return credits;
    }

    public List<Client> getClients() {
        fileLogger.info("Accessing list of clients.");
        return clients;
    }

    public void addCredit(Credit credit) {
        credits.add(credit);
        fileLogger.info("Added new credit: {}", credit.getName());
    }

    public Credit getCreditByName(String name) {
        fileLogger.info("Searching for credit by name: {}", name);
        for (Credit credit : credits) {
            if (credit.getName().equalsIgnoreCase(name)) {
                fileLogger.info("Credit found: {}", credit.getName());
                return credit;
            }
        }
        errorLogger.warn(ERROR_MARKER, "Credit not found: {}", name);
        return null; // Повертає null, якщо кредит не знайдено
    }

    public void editCredit(int index, Credit updatedCredit) {
        if (index >= 0 && index < credits.size()) {
            Credit oldCredit = credits.get(index);
            credits.set(index, updatedCredit);
            fileLogger.info("Credit updated at index {}: {} -> {}", index, oldCredit.getName(), updatedCredit.getName());
        } else {
            errorLogger.error(ERROR_MARKER, "Attempted to edit credit at invalid index: {}", index);
        }
    }
}
