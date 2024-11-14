package bank;

import menu.IMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class BankSystem {
    private static final Logger fileLogger = LogManager.getLogger("FileOnlyLogger");
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");

    private IMenu menu;

    public BankSystem(IMenu menu) {
        this.menu = menu;
        fileLogger.info("BankSystem instance created with menu.");
    }

    public BankSystem() {
        fileLogger.info("BankSystem instance created without menu.");
    }

    public BankSystem(Bank bank, IMenu menu) {
        this.menu = menu;
        fileLogger.info("BankSystem instance created with bank and menu.");
    }

    public void run() {
        if (menu != null) {
            fileLogger.info("BankSystem is running. Displaying main menu.");
            menu.showMainMenu();
        } else {
            errorLogger.error(ERROR_MARKER, "Attempted to run BankSystem without a menu instance.");
        }
    }

    public IMenu getMenu() {
        if (menu != null) {
            fileLogger.info("Accessing BankSystem menu.");
        } else {
            errorLogger.warn(ERROR_MARKER, "Attempted to access BankSystem menu, but menu is null.");
        }
        return menu;
    }
}
