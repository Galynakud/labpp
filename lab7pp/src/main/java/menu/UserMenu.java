package menu;

import bank.Bank;
import bank.Client;
import bank.Credit;

import java.io.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserMenu {
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
                case 1 -> showAllCredits();
                case 2 -> selectCredit();
                case 3 -> earlyRepayment();
                case 4 -> increaseCreditLine();
                case 5 -> saveSelectedCreditsToFile();
                case 6 -> loadSelectedCreditsFromFile();
                case 0 -> System.out.println("Вихід з меню.");
                default -> System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Помилка: Введення завершилось несподівано. Будь ласка, перевірте введені дані.");
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
        } else {
            System.out.println("Кредит не знайдено.");
        }
    }

    public void earlyRepayment() {
        System.out.print("Введіть назву кредиту для дострокового погашення: ");
        String creditName = scanner.nextLine().trim();
        Credit selectedCredit = bank.getCreditByName(creditName);
        if (selectedCredit != null) {
            client.repayCredit(selectedCredit);
            System.out.println("Кредит " + selectedCredit.getName() + " успішно погашено достроково.");
        } else {
            System.out.println("Кредит не знайдено.");
        }
    }

    public void increaseCreditLine() {
        System.out.println("Введіть назву кредиту:");
        String creditName = scanner.nextLine();
        Credit credit = bank.getCreditByName(creditName);

        if (credit == null) {
            System.out.println("Кредит не знайдено.");
            return;
        }

        System.out.println("Введіть суму для збільшення кредитної лінії:");
        if (scanner.hasNextDouble()) {
            double amount = scanner.nextDouble();
            client.increaseCredit(credit, amount);
        } else {
            System.out.println("Неправильний ввід. Очікується число.");
            scanner.nextLine();
        }
    }

    public void saveSelectedCreditsToFile() {
        System.out.print("Введіть назву файлу для збереження: ");
        String filename = scanner.nextLine().trim();
        List<Credit> selectedCredits = client.getSelectedCredits();
        if (selectedCredits.isEmpty()) {
            System.out.println("Немає обраних кредитів для збереження.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Credit credit : selectedCredits) {
                writer.write(credit.getName());
                writer.newLine();
            }
            System.out.println("Обрані кредити успішно збережено у файл: " + filename);
        } catch (IOException e) {
            System.out.println("Сталася помилка при збереженні у файл: " + e.getMessage());
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
                } else {
                    System.out.println("Кредит: " + line + " не завантажено.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не знайдено: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Сталася помилка при завантаженні з файлу: " + e.getMessage());
        }
    }
}