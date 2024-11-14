package org.example;

import bank.BankSystem;
import menu.IMenu;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class MainTest {

    @Test
    public void testMain() {
        // Мокання IMenu
        IMenu mockMenu = mock(IMenu.class);

        // Мокання BankSystem
        BankSystem mockBankSystem = mock(BankSystem.class);

        // Заміна реального об'єкта BankSystem на мок у класі Main
        Main.setBankSystem(mockBankSystem);  // Для цього потрібно додати метод setBankSystem()

        // Викликаємо головний метод, який має викликати BankSystem
        Main.main(new String[]{});

        // Перевіряємо, чи викликається метод run()
        verify(mockBankSystem, times(1)).run();

        // Перевіряємо, чи викликається showMainMenu()
        verify(mockMenu, times(1)).showMainMenu();
    }

}