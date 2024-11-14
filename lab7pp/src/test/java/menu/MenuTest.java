package menu;

import bank.Admin;
import bank.Bank;
import bank.Client;
import command.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {

    @Mock
    private Bank mockBank;
    @Mock
    private Admin mockAdmin;
    @Mock
    private Client mockClient;
    @Mock
    private Scanner mockScanner;
    private Menu menu;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Ініціалізація моків
        menu = new Menu(mockBank, mockAdmin, mockScanner); // ініціалізація меню
    }

    @Disabled
    @Test
    public void testShowMainMenu_ClientLogin() {
        // Мокування введення
        when(mockScanner.nextInt()).thenReturn(1);
        when(mockScanner.nextLine()).thenReturn(""); // Визначення порожнього рядка для nextLine()

        // Виклик nextInt() і додавання наступного nextLine() для очищення буфера
        menu.showMainMenu();

        // Перевірка, що методи nextInt() та nextLine() були викликані
        verify(mockScanner).nextInt();
        verify(mockScanner, times(2)).nextLine(); // Перевірка nextLine() двічі
    }

    @Disabled
    @Test
    public void testAdminLogin_Success() {
        String username = "adminUser";
        String password = "adminPass";

        // Mocking the admin's username and password
        when(mockScanner.nextLine()).thenReturn(username).thenReturn(password);
        when(mockAdmin.getUsername()).thenReturn(username);  // Mocking getUsername() to return a valid username
        when(mockAdmin.getPassword()).thenReturn(password);  // Mocking getPassword() to return the correct password

        // Performing the login action
        menu.adminLogin();

        // Verifying that the correct methods were called
        verify(mockScanner, times(2)).nextLine();  // Checking that nextLine() was called twice
        verify(mockAdmin).getUsername();  // Verifying that getUsername() was called
        verify(mockAdmin).getPassword();  // Verifying that getPassword() was called
    }


    @Test
    public void testShowMainMenu_Exit() {
        when(mockScanner.nextInt()).thenReturn(3);
        when(mockScanner.nextLine()).thenReturn("");
        menu.showMainMenu();
        verify(mockScanner).nextInt();
        verify(mockScanner).nextLine();
    }

    @Disabled
    @Test
    public void testShowMainMenu_InvalidChoice() {
        when(mockScanner.nextInt()).thenReturn(999);
        when(mockScanner.nextLine()).thenReturn("");
        menu.showMainMenu();
        verify(mockScanner).nextInt();
        verify(mockScanner).nextLine();
    }

    @Test
    public void testCommandInitialization() {
        Map<Integer, Command> commands = menu.getCommands();
        assertTrue(commands.containsKey(1));
        assertTrue(commands.containsKey(2));
        assertTrue(commands.containsKey(3));
        assertTrue(commands.get(1) instanceof ClientLoginCommand);
        assertTrue(commands.get(2) instanceof AdminLoginCommand);
        assertTrue(commands.get(3) instanceof ExitCommand);
    }

    @Test
    public void testInvalidInput() {
        when(mockScanner.nextInt()).thenThrow(new java.util.InputMismatchException("Invalid input"));
        try {
            menu.showMainMenu();
        } catch (java.util.InputMismatchException e) {
            assertEquals("Invalid input", e.getMessage());
        }
        verify(mockScanner).nextInt();
    }

    @Test
    public void testExitCommandExecution() {
        // Мокування scanner для імітації вводу
        when(mockScanner.nextInt()).thenReturn(3);
        menu.showMainMenu();
        verify(mockScanner).nextInt();
    }

    @Test
    public void testAdminLogin_Failure() {
        String username = "adminUser";
        String password = "wrongPass";
        when(mockScanner.nextLine()).thenReturn(username).thenReturn(password);
        when(mockAdmin.getUsername()).thenReturn(username);
        when(mockAdmin.getPassword()).thenReturn("correctPass");
        menu.adminLogin();
        verify(mockScanner, times(2)).nextLine();
        verify(mockAdmin).getUsername();
        verify(mockAdmin).getPassword();
    }

    @Test
    public void testClientLogin_Success() {
        String name = "clientName";
        String password = "password123";
        when(mockScanner.nextLine()).thenReturn(name).thenReturn(password);
        when(mockBank.getClients()).thenReturn(List.of(mockClient));
        when(mockClient.getName()).thenReturn(name);
        when(mockClient.getPassword()).thenReturn(password);
        menu.clientLogin();
        verify(mockScanner, times(2)).nextLine();
        verify(mockClient).getName();
        verify(mockClient).getPassword();
    }

    @Test
    public void testClientLogin_Failure() {
        String name = "clientName";
        String password = "wrongPassword";
        when(mockScanner.nextLine()).thenReturn(name).thenReturn(password);
        when(mockBank.getClients()).thenReturn(List.of(mockClient));
        when(mockClient.getName()).thenReturn(name);
        when(mockClient.getPassword()).thenReturn("correctPassword");
        menu.clientLogin();
        verify(mockScanner, times(2)).nextLine();
        verify(mockClient).getName();
        verify(mockClient).getPassword();
    }

    @Test
    public void testClientLogin_EmptyCredentials() {
        when(mockScanner.nextLine()).thenReturn("").thenReturn("");
        menu.clientLogin();
        verify(mockScanner, times(2)).nextLine();
    }

    @Test
    public void testAuthenticateClient_MultipleClients_Success() {
        String name = "clientName";
        String password = "password123";
        Client anotherClient = mock(Client.class);
        when(mockBank.getClients()).thenReturn(List.of(mockClient, anotherClient));
        when(mockClient.getName()).thenReturn(name);
        when(mockClient.getPassword()).thenReturn(password);
        when(anotherClient.getName()).thenReturn("anotherClient");
        when(anotherClient.getPassword()).thenReturn("anotherPassword");
        Client authenticatedClient = menu.authenticateClient(name, password);
        assertNotNull(authenticatedClient);
        verify(mockBank).getClients();
    }

    @Test
    public void testAuthenticateClient_NoClientsFound() {
        when(mockBank.getClients()).thenReturn(List.of());
        Client authenticatedClient = menu.authenticateClient("nonExistentClient", "password");
        assertNull(authenticatedClient);
        verify(mockBank).getClients();
    }

    @Test
    public void testClientLogin_NoClientsInBank() {
        when(mockScanner.nextLine()).thenReturn("clientName").thenReturn("password123");
        when(mockBank.getClients()).thenReturn(List.of());
        menu.clientLogin();
        verify(mockScanner, times(2)).nextLine();
    }
}