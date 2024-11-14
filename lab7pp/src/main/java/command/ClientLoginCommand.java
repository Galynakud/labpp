package command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import menu.Menu;

public class ClientLoginCommand implements Command {
    private static final Logger fileLogger = LogManager.getLogger("FileOnlyLogger");
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");

    private final Menu menu;

    public ClientLoginCommand(Menu menu) {
        this.menu = menu;
        fileLogger.info("ClientLoginCommand created with menu: " + menu.getClass().getSimpleName());
    }

    @Override
    public void execute() {
        try {
            fileLogger.info("Executing ClientLoginCommand.");
            menu.clientLogin();
            fileLogger.info("Client login executed successfully.");
        } catch (Exception e) {
            errorLogger.error(ERROR_MARKER, "Error during client login execution: " + e.getMessage(), e);
        }
    }
}
