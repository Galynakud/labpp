package menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import bank.Bank;
import bank.Client;
import bank.Credit;

import java.io.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserMenu {
    private static final Logger fileLogger = LogManager.getLogger("FileOnlyLogger");
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");
    private final Client client;
    private final Bank bank;
    private final Scanner scanner;

    private static final int MAX_MENU_ITERATIONS = 10;

    public UserMenu(Client client, Bank bank, Scanner scanner) {
        this.client = client;
        this.bank = bank;
        this.scanner = scanner;
    }

    public void showClientMenu() {
        System.out.println("Виберіть дію:");
        System.out.println("1. Показати всі кредити");
        System.out.println("2. Обрати кредит");
        System.out.println("3. Дострокове погашення кредиту");
        System.out.println("4. Збільшити кредитний ліміт");
        System.out.println("5. Зберегти вибрані кредити у файл");
        System.out.println("6. Завантажити кредити з файлу");
        System.out.println("0. Вийти");

        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    showAllCredits();
                    fileLogger.info("User selected option 1: Show all credits.");
                }
                case 2 -> {
                    selectCredit();
                    fileLogger.info("User selected option 2: Select credit.");
                }
                case 3 -> {
                    earlyRepayment();
                    fileLogger.info("User selected option 3: Early repayment.");
                }
                case 4 -> {
                    increaseCreditLine();
                    fileLogger.info("User selected option 4: Increase credit line.");
                }
                case 5 -> {
                    saveSelectedCreditsToFile();
                    fileLogger.info("User selected option 5: Save selected credits to file.");
                }
                case 6 -> {
                    loadSelectedCreditsFromFile();
                    fileLogger.info("User selected option 6: Load selected credits from file.");
                }
                case 0 -> {
                    System.out.println("Вихід з меню.");
                    fileLogger.info("User selected option 0: Exit.");
                }
                default -> {
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
                    fileLogger.warn("Invalid menu choice: " + choice);
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Помилка: Введення завершилось несподівано. Будь ласка, перевірте введені дані.");
            errorLogger.error(ERROR_MARKER, "Unexpected input error: " + e.getMessage(), e);
        }
    }

    private void showAllCredits() {
        System.out.println("Доступні кредити:");
        for (Credit credit : bank.getCredits()) {
            System.out.println(credit.getName() + ", Сума: " + credit.getAmount() + ", Процентна ставка: " +
                    credit.getInterestRate() + "%, Термін: " + credit.getTermMonths() + " місяців");
        }
    }

    public void selectCredit() {
        System.out.print("Введіть назву кредиту для вибору: ");
        String creditName = scanner.nextLine().trim();
        Credit selectedCredit = bank.getCreditByName(creditName);
        if (selectedCredit != null) {
            client.takeCredit(selectedCredit);
            System.out.println("Ви вибрали кредит: " + selectedCredit.getName());
            fileLogger.info("Client selected credit: " + selectedCredit.getName());
        } else {
            System.out.println("Кредит не знайдено.");
            fileLogger.warn("Credit not found: " + creditName);
        }
    }

    public void earlyRepayment() {
        System.out.print("Введіть назву кредиту для дострокового погашення: ");
        String creditName = scanner.nextLine().trim();
        Credit selectedCredit = bank.getCreditByName(creditName);
        if (selectedCredit != null) {
            client.repayCredit(selectedCredit);
            System.out.println("Кредит " + selectedCredit.getName() + " успішно погашено достроково.");
            fileLogger.info("Early repayment processed for credit: " + selectedCredit.getName());
        } else {
            System.out.println("Кредит не знайдено.");
            fileLogger.warn("Credit not found for early repayment: " + creditName);
        }
    }

    public void increaseCreditLine() {
        System.out.println("Введіть назву кредиту:");
        String creditName = scanner.nextLine();
        Credit credit = bank.getCreditByName(creditName);

        if (credit == null) {
            System.out.println("Кредит не знайдено.");
            fileLogger.warn("Credit not found for increasing credit line: " + creditName);
            return;
        }

        System.out.println("Введіть суму для збільшення кредитної лінії:");
        if (scanner.hasNextDouble()) {
            double amount = scanner.nextDouble();
            client.increaseCredit(credit, amount);
            fileLogger.info("Credit line increased for credit: " + creditName + " by amount: " + amount);
        } else {
            System.out.println("Неправильний ввід. Очікується число.");
            fileLogger.error(ERROR_MARKER, "Invalid input for increasing credit line.");
            scanner.nextLine();
        }
    }

    public void saveSelectedCreditsToFile() {
        System.out.print("Введіть назву файлу для збереження: ");
        String filename = scanner.nextLine().trim();
        List<Credit> selectedCredits = client.getSelectedCredits();
        if (selectedCredits.isEmpty()) {
            System.out.println("Немає обраних кредитів для збереження.");
            fileLogger.warn("No selected credits to save in file.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Credit credit : selectedCredits) {
                writer.write(credit.getName());
                writer.newLine();
            }
            System.out.println("Обрані кредити успішно збережено у файл: " + filename);
            fileLogger.info("Selected credits saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("Сталася помилка при збереженні у файл: " + e.getMessage());
            errorLogger.error(ERROR_MARKER, "Error saving selected credits to file: " + e.getMessage(), e);
        }
    }

    public void loadSelectedCreditsFromFile() {
        System.out.print("Введіть назву файлу для завантаження: ");
        String filename = scanner.nextLine().trim();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Credit loadedCredit = bank.getCreditByName(line);
                if (loadedCredit != null) {
                    client.takeCredit(loadedCredit);
                    System.out.println("Кредит: " + loadedCredit.getName() + " обраний.");
                    fileLogger.info("Credit loaded from file: " + loadedCredit.getName());
                } else {
                    System.out.println("Кредит: " + line + " не завантажено.");
                    fileLogger.warn("Credit not loaded from file: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не знайдено: " + e.getMessage());
            errorLogger.error(ERROR_MARKER, "File not found: " + filename, e);
        } catch (IOException e) {
            System.out.println("Сталася помилка при завантаженні з файлу: " + e.getMessage());
            errorLogger.error(ERROR_MARKER, "Error loading credits from file: " + e.getMessage(), e);
        }
    }
}
