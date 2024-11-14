package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import bank.BankSystem;

public class Main {
    private static final Logger fileLogger = LogManager.getLogger("FileOnlyLogger");
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");
    private static BankSystem bankSystem;

    public static void main(String[] args) {
        try {
            fileLogger.info("Application started.");
            bankSystem = new BankSystem();
            bankSystem.run();
            fileLogger.info("Bank system run completed successfully.");
        } catch (Exception e) {
            errorLogger.error(ERROR_MARKER, "An error occurred while running the bank system: ", e);
        }
    }

    public static void setBankSystem(BankSystem bankSystem) {
        Main.bankSystem = bankSystem;
    }
}
