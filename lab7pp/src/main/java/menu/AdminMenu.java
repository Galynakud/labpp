package menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import bank.Admin;
import bank.Bank;
import bank.Credit;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private static final Logger fileLogger = LogManager.getLogger("FileOnlyLogger");
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");

    private Admin admin = null;
    private Bank bank = null;
    private static Scanner scanner = new Scanner(System.in);

    public AdminMenu(Bank bank) {
        this.bank = bank;
        fileLogger.info("AdminMenu created with bank reference.");
    }

    public AdminMenu(Admin admin, Bank bank) {
        this.admin = admin;
        this.bank = bank;
        fileLogger.info("AdminMenu created with admin and bank references.");
    }

    public AdminMenu(Scanner scanner, Bank bank) {
        this.scanner = scanner;
        this.bank = bank;
        fileLogger.info("AdminMenu created with scanner and bank references.");
    }

    public void showAdminMenu() {
        while (true) {
            System.out.println("Меню адміністратора:");
            System.out.println("1. Додати кредит");
            System.out.println("2. Редагувати кредит");
            System.out.println("3. Вийти");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addCredit();
                    break;
                case 2:
                    editCredit();
                    break;
                case 3:
                    fileLogger.info("Admin exited the admin menu.");
                    return;
                default:
                    System.out.println("Невірний вибір!");
                    fileLogger.warn("Invalid choice in admin menu: " + choice);
            }
        }
    }

    public void addCredit() {
        try {
            System.out.print("Введіть назву кредиту: ");
            String name = scanner.nextLine();
            System.out.print("Введіть суму кредиту: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Введіть процентну ставку: ");
            double interestRate = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Введіть термін кредиту (в місяцях): ");
            int termMonths = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Дострокове погашення (так/ні): ");
            boolean earlyRepayment = scanner.next().equalsIgnoreCase("так");
            scanner.nextLine();

            Credit newCredit = new Credit(name, amount, interestRate, termMonths, earlyRepayment);
            bank.addCredit(newCredit);
            System.out.println("Кредит успішно додано!");
            fileLogger.info("New credit added: " + name);
        } catch (Exception e) {
            errorLogger.error(ERROR_MARKER, "Error while adding credit: " + e.getMessage(), e);
        }
    }

    public void editCredit() {
        try {
            showAllCredits();
            System.out.print("Виберіть індекс кредиту для редагування: ");
            int index = scanner.nextInt();
            scanner.nextLine();
            if (index >= 0 && index < bank.getCredits().size()) {
                Credit creditToEdit = bank.getCredits().get(index);
                System.out.print("Введіть нову назву кредиту (старе: " + creditToEdit.getName() + "): ");
                String name = scanner.nextLine();

                System.out.print("Введіть нову суму кредиту (старе: " + creditToEdit.getAmount() + "): ");
                double amount = scanner.nextDouble();

                System.out.print("Введіть нову процентну ставку (старе: " + creditToEdit.getInterestRate() + "%): ");
                double interestRate = scanner.nextDouble();

                System.out.print("Введіть новий термін кредиту (в місяцях, старе: " + creditToEdit.getTermMonths() + "): ");
                int termMonths = scanner.nextInt();

                scanner.nextLine();
                System.out.print("Дострокове погашення (так/ні, старе: " + (creditToEdit.isEarlyRepayment() ? "Так" : "Ні") + "): ");
                String earlyRepaymentInput = scanner.nextLine();

                boolean earlyRepayment = "так".equalsIgnoreCase(earlyRepaymentInput);

                creditToEdit.setName(name);
                creditToEdit.setAmount(amount);
                creditToEdit.setInterestRate(interestRate);
                creditToEdit.setTermMonths(termMonths);
                creditToEdit.setEarlyRepayment(earlyRepayment);
                System.out.println("Кредит успішно оновлено!");
                fileLogger.info("Credit updated: " + name);
            } else {
                System.out.println("Невірний індекс кредиту!");
                fileLogger.warn("Invalid credit index chosen for editing: " + index);
            }
        } catch (Exception e) {
            errorLogger.error(ERROR_MARKER, "Error while editing credit: " + e.getMessage(), e);
        }
    }

    public void showAllCredits() {
        System.out.println("Доступні кредити:");
        List<Credit> credits = bank.getCredits();
        for (int i = 0; i < credits.size(); i++) {
            Credit credit = credits.get(i);
            System.out.println(i + ": " + credit.getName() + ", Сума: " + credit.getAmount() +
                    ", Процентна ставка: " + credit.getInterestRate() + "%, Термін: " + credit.getTermMonths() + " місяців" +
                    ", Дострокове погашення: " + (credit.isEarlyRepayment() ? "Так" : "Ні"));
        }
        fileLogger.info("Displayed all available credits.");
    }
}
