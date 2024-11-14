package bank;

import menu.IMenu;

public class BankSystem {

    private IMenu menu;

    public BankSystem(IMenu menu) {
        this.menu = menu;
    }

    public BankSystem() {

    }

    public BankSystem(Bank bank, IMenu menu) {
    }

    public void run() {
        if (menu != null) {
            menu.showMainMenu();
        }
    }

    public IMenu getMenu() {
        return menu;
    }
}