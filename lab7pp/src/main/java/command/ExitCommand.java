package command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class ExitCommand implements Command {
    private static final Logger fileLogger = LogManager.getLogger("FileOnlyLogger");
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");

    private final ExitHandler exitHandler;

    public ExitCommand(ExitHandler exitHandler) {
        this.exitHandler = exitHandler;
        fileLogger.info("ExitCommand created with custom ExitHandler.");
    }

    public ExitCommand() {
        this.exitHandler = new DefaultExitHandler() {
            @Override
            public void exit(int status) {
                fileLogger.info("Executing exit with status: {}", status);
                System.exit(status);
            }
        };
        fileLogger.info("ExitCommand created with default ExitHandler.");
    }

    @Override
    public void execute() {
        fileLogger.info("Executing ExitCommand - preparing to exit the application.");
        try {
            System.out.println("Вихід з програми...");
            exitHandler.exit(0); // Викликаємо exit через handler
            fileLogger.info("Exit command executed successfully.");
        } catch (Exception e) {
            errorLogger.error(ERROR_MARKER, "Error while executing ExitCommand: " + e.getMessage(), e);
        }
    }
}
