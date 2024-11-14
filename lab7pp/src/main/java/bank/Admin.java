package bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class Admin {
    private static final Logger fileLogger = LogManager.getLogger("FileOnlyLogger");
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");

    private final String username = "adminUser";
    private final String password = "adminPass";

    public Admin(String adminUser, String password123) {
        fileLogger.info("Creating Admin instance.");
    }

    public String getUsername() {
        fileLogger.info("Accessing Admin username.");
        return username;
    }

    public String getPassword() {
        fileLogger.warn("Accessing Admin password.");
        return password;
    }
}
