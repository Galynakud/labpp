package command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import menu.Menu;

public class AdminLoginCommand implements Command {
    private static final Logger fileLogger = LogManager.getLogger("FileOnlyLogger");
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");

    private final Menu menu;

    public AdminLoginCommand(Menu menu) {
        this.menu = menu;
        fileLogger.info("AdminLoginCommand created with menu: " + menu.getClass().getSimpleName());
    }

    @Override
    public void execute() {
        try {
            fileLogger.info("Executing AdminLoginCommand.");
            menu.adminLogin();
            fileLogger.info("Admin login executed successfully.");
        } catch (Exception e) {
            errorLogger.error(ERROR_MARKER, "Error during admin login execution: " + e.getMessage(), e);
        }
    }
}
