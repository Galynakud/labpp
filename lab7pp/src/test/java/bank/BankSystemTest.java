package bank;

import menu.IMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class BankSystemTest {
    private IMenu menu;
    private BankSystem bankSystem;

    @BeforeEach
    void setUp() {
        menu = Mockito.mock(IMenu.class); // Мокування інтерфейсу IMenu
    }

    @Test
    void testRun_withMenu() {
        // Використання конструктора з параметром
        bankSystem = new BankSystem(new Bank(), menu);

        bankSystem.run();
        verify(menu, times(1)).showMainMenu();  // Перевірка виклику showMainMenu()
    }

    @Test
    void testRun_withoutMenu() {
        // Використання конструктора без параметра
        bankSystem = new BankSystem();

        bankSystem.run();
        verify(menu, times(0)).showMainMenu();  // Перевірка, що метод showMainMenu не викликається
    }

    @Test
    void testRun_withNullMenu() {
        // Використання конструктора з параметром і menu = null
        bankSystem = new BankSystem(null);

        bankSystem.run();
        verify(menu, times(0)).showMainMenu();  // Перевірка, що метод showMainMenu не викликається
    }

    @Test
    void testRun_withMockedMenu() {
        // Використання мок меню, яке вже було замокано
        bankSystem = new BankSystem(new Bank(), menu);

        bankSystem.run();
        verify(menu, times(1)).showMainMenu();  // Перевірка виклику showMainMenu() на замоканому меню
    }

    @Test
    void testRun_withMockedMenuAndBankSystem() {
        // Перевірка конструктора з конкретним Bank (якщо клас Bank буде доданий у майбутньому)
        bankSystem = new BankSystem(new Bank(), menu);

        bankSystem.run();
        verify(menu, times(1)).showMainMenu();  // Перевірка виклику showMainMenu() на замоканому меню
    }

    @Test
    void testRun_withMockMenuAndNullBank() {
        // Тест для конструктора з null для Bank, але з мок меню
        bankSystem = new BankSystem(null, menu);

        bankSystem.run();
        verify(menu, times(1)).showMainMenu();  // Перевірка, що меню показується навіть без банку
    }

    @Test
    void testGetMenu() {
        // Перевірка гетера для menu
        bankSystem = new BankSystem(new Bank(), menu);

        IMenu retrievedMenu = bankSystem.getMenu();
        assert retrievedMenu != null;  // Перевірка, що menu коректно отримано
        verify(menu, times(1)).showMainMenu(); // Перевірка, що menu встановлено правильно
    }

    @Test
    void testGetMenu_withNullMenu() {
        // Перевірка гетера для menu, коли menu = null
        bankSystem = new BankSystem(null);

        IMenu retrievedMenu = bankSystem.getMenu();
        assert retrievedMenu == null;  // Перевірка, що повертається null, якщо menu не ініціалізовано
    }

    @Test
    void testGetMenu_withMockMenu() {
        // Перевірка гетера для menu з мок меню
        bankSystem = new BankSystem(new Bank(), menu);

        IMenu retrievedMenu = bankSystem.getMenu();
        assert retrievedMenu != null;  // Перевірка, що menu правильно отримано через гетер
    }

    @Test
    void testConstructorWithNoParams() {
        // Тест для конструктора без параметрів
        bankSystem = new BankSystem();
        assert bankSystem != null; // Перевірка, що об'єкт створений
    }

    @Test
    void testConstructorWithBankAndMenu() {
        // Тест для конструктора BankSystem(Bank bank, IMenu menu)
        bankSystem = new BankSystem(new Bank(), menu);
        assert bankSystem != null; // Перевірка, що об'єкт створений
    }
}