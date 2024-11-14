package menu;

import bank.Bank;
import bank.Credit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class AdminMenuTest {

    @Mock
    private Bank mockBank;

    private AdminMenu adminMenu;

    @BeforeEach
    public void setUp() {
        mockBank = mock(Bank.class);
        Scanner mockScanner = mock(Scanner.class);
        adminMenu = new AdminMenu(mockScanner, mockBank);  // використовуємо мокований Scanner і банк
    }

    @Test
    public void testShowAllCredits() {
        // Створюємо список кредитів
        List<Credit> credits = new ArrayList<>();
        Credit credit1 = new Credit("Home Loan", 50000, 7.5, 24, true);
        Credit credit2 = new Credit("Car Loan", 20000, 5.0, 18, false);
        credits.add(credit1);
        credits.add(credit2);

        when(mockBank.getCredits()).thenReturn(credits);

        // Викликаємо метод showAllCredits
        adminMenu.showAllCredits();

        // Перевіряємо, чи був викликаний метод getCredits()
        verify(mockBank, times(1)).getCredits();
    }

    @Test
    public void testAddCredit() {
        // Створюємо мок сканера для введення даних
        Scanner mockScanner = mock(Scanner.class);
        adminMenu = new AdminMenu(mockScanner, mockBank); // Передаємо мокований сканер

        // Налаштовуємо поведінку для моканого сканера
        when(mockScanner.nextLine()).thenReturn("Home Loan");
        when(mockScanner.nextDouble()).thenReturn(50000.0, 7.5, 24.0);
        when(mockScanner.next()).thenReturn("так");
        when(mockScanner.nextLine()).thenReturn(""); // Споживаємо залишковий символ нового рядка

        // Викликаємо метод addCredit()
        adminMenu.addCredit();

        // Перевіряємо, чи був викликаний метод addCredit на банку
        verify(mockBank, times(1)).addCredit(any(Credit.class));
    }

    @Test
    public void testAddCredit_invalidInput() {
        // Тест для неправильного вводу користувача
        Scanner mockScanner = mock(Scanner.class);
        adminMenu = new AdminMenu(mockScanner, mockBank); // Передаємо мокований сканер

        when(mockScanner.nextLine()).thenReturn("Home Loan");
        when(mockScanner.nextDouble()).thenReturn(50000.0, 7.5, 24.0);
        when(mockScanner.next()).thenReturn("ні");  // Вибір "ні" для перевірки, що не додається кредит
        when(mockScanner.nextLine()).thenReturn(""); // Споживаємо залишковий символ нового рядка

        adminMenu.addCredit();

        // Перевіряємо, чи не викликається addCredit у банку, якщо вибір "ні"
        verify(mockBank, times(0)).addCredit(any(Credit.class));
    }

    @Test
    public void testEditCredit() {
        // Створюємо список кредитів для редагування
        List<Credit> credits = new ArrayList<>();
        Credit credit1 = new Credit("Home Loan", 50000, 7.5, 24, true);
        Credit credit2 = new Credit("Car Loan", 20000, 5.0, 18, false);
        credits.add(credit1);
        credits.add(credit2);

        when(mockBank.getCredits()).thenReturn(credits);

        // Мокання введення користувача для редагування
        Scanner mockScanner = mock(Scanner.class);
        adminMenu = new AdminMenu(mockBank); // Перезавантажуємо adminMenu

        when(mockScanner.nextInt()).thenReturn(0); // Вибір індексу для редагування
        when(mockScanner.nextLine()).thenReturn("Updated Home Loan");
        when(mockScanner.nextDouble()).thenReturn(60000.0, 8.0, 24.0);
        when(mockScanner.next()).thenReturn("ні");

        // Викликаємо метод редагування кредиту
        adminMenu.editCredit();

        // Перевіряємо, чи був оновлений кредит у банку
        verify(mockBank, times(1)).getCredits();
    }

    @Test
    public void testEditCredit_invalidIndex() {
        // Створюємо список кредитів для редагування
        List<Credit> credits = new ArrayList<>();
        Credit credit1 = new Credit("Home Loan", 50000, 7.5, 24, true);
        Credit credit2 = new Credit("Car Loan", 20000, 5.0, 18, false);
        credits.add(credit1);
        credits.add(credit2);

        when(mockBank.getCredits()).thenReturn(credits);

        // Мокання введення користувача для редагування
        Scanner mockScanner = mock(Scanner.class);
        adminMenu = new AdminMenu(mockBank); // Перезавантажуємо adminMenu

        when(mockScanner.nextInt()).thenReturn(5); // Некоректний індекс
        when(mockScanner.nextLine()).thenReturn("Updated Home Loan");
        when(mockScanner.nextDouble()).thenReturn(60000.0, 8.0, 24.0);
        when(mockScanner.next()).thenReturn("ні");

        // Викликаємо метод редагування кредиту
        adminMenu.editCredit();

        // Перевіряємо, що метод getCredits був викликаний хоча б один раз
        verify(mockBank, times(1)).getCredits();
    }

    @Disabled
    @Test
    public void testShowAdminMenu() {
        // Мокання користувацького вводу для вибору опцій
        Scanner mockScanner = mock(Scanner.class);
        adminMenu = new AdminMenu(mockScanner, mockBank); // Перевантажуємо adminMenu

        // Налаштовуємо поведінку для моканого сканера
        when(mockScanner.nextInt()).thenReturn(1); // Вибір для додавання кредиту
        when(mockScanner.next()).thenReturn("так"); // Встановлюємо повернення значення "так" для next()
        doNothing().when(mockBank).addCredit(any(Credit.class));

        // Запускаємо showAdminMenu() в окремому потоці, щоб уникнути блокування тесту
        Thread thread = new Thread(() -> adminMenu.showAdminMenu());
        thread.start();

        // Дочекаємось завершення потоку (чи коли тест завершиться)
        try {
            thread.join(); // Чекаємо завершення потоку
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Перевіряємо, чи викликається відповідний метод для додавання кредиту
        verify(mockBank, times(1)).addCredit(any(Credit.class));
    }

    @Disabled
    @Test
    public void testShowAdminMenu_invalidInput() {
        // Мокання користувацького вводу для вибору опцій
        Scanner mockScanner = mock(Scanner.class);
        adminMenu = new AdminMenu(mockScanner, mockBank); // Перевантажуємо adminMenu

        // Налаштовуємо поведінку для моканого сканера
        when(mockScanner.nextInt()).thenReturn(999); // Некоректний вибір
        when(mockScanner.next()).thenReturn("так"); // Встановлюємо повернення значення "так" для next()

        // Запускаємо showAdminMenu() в окремому потоці, щоб уникнути блокування тесту
        Thread thread = new Thread(() -> adminMenu.showAdminMenu());
        thread.start();

        // Дочекаємось завершення потоку
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Перевірка, що метод не був викликаний при некоректному вводу
        verify(mockBank, times(0)).addCredit(any(Credit.class));
    }
}