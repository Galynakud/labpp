package menu;

import command.*;
import bank.Bank;
import bank.Admin;
import bank.Client;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu implements IMenu {
    private final Bank bank;
    private final Admin admin;
    private final Scanner scanner;
    private final Map<Integer, Command> commands = new HashMap<>();

    public Menu(Bank bank, Admin admin, Scanner scanner) {
        this.bank = bank;
        this.admin = admin;
        this.scanner = scanner;
        initializeCommands();
    }

    private void initializeCommands() {
        commands.put(1, new ClientLoginCommand(this));
        commands.put(2, new AdminLoginCommand(this));
        commands.put(3, new ExitCommand());
    }

    @Override
    public void showMainMenu() {
        while (true) {
            System.out.println("Головне меню:");
            System.out.println("1. Увійти як клієнт");
            System.out.println("2. Увійти як адміністратор");
            System.out.println("3. Вийти");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 3) {
                break;
            }

            Command command = commands.get(choice);
            if (command != null) {
                command.execute();
            } else {
                System.out.println("Невірний вибір!");
            }
        }
    }

    @Override
    public void clientLogin() {
        System.out.print("Введіть ім'я: ");
        String name = scanner.nextLine();
        System.out.print("Введіть пароль: ");
        String password = scanner.nextLine();
        Client client = authenticateClient(name, password);
        if (client != null) {
            UserMenu userMenu = new UserMenu(client, bank, scanner);
            userMenu.showClientMenu();
        } else {
            System.out.println("Невірне ім'я або пароль.");
        }
    }

    public Client authenticateClient(String name, String password) {
        for (Client client : bank.getClients()) {
            if (client.getName().equals(name) && client.getPassword().equals(password)) {
                return client;
            }
        }
        return null;
    }

    @Override
    public void adminLogin() {
        System.out.print("Введіть ім'я: ");
        String username = scanner.nextLine();
        System.out.print("Введіть пароль: ");
        String password = scanner.nextLine();

        if (admin != null && admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            AdminMenu adminMenu = new AdminMenu(admin, bank);
            adminMenu.showAdminMenu();
        } else {
            System.out.println("Невірне ім'я або пароль.");
        }
    }

    public Map<Integer, Command> getCommands() {
        return Map.of();
    }
}