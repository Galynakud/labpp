package menu;

import bank.Bank;
import bank.Client;
import bank.Credit;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.io.*;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserMenuTest {

    @Mock private Bank mockBank;
    @Mock private Client mockClient;
    @Mock private Credit mockCredit;
    private UserMenu userMenu;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowClientMenu() {
        String input = "1\n";
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        userMenu = new UserMenu(mockClient, mockBank, scanner);

        when(mockBank.getCredits()).thenReturn(Collections.singletonList(mockCredit));
        when(mockCredit.getName()).thenReturn("Test Credit");
        when(mockCredit.getAmount()).thenReturn(1000.0);
        when(mockCredit.getInterestRate()).thenReturn(5.0);
        when(mockCredit.getTermMonths()).thenReturn(12);

        userMenu.showClientMenu();

        verify(mockBank, times(1)).getCredits();
    }

    @Test
    void testSelectCredit() {
        String input = "Test Credit\n";
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        userMenu = new UserMenu(mockClient, mockBank, scanner);

        when(mockBank.getCreditByName("Test Credit")).thenReturn(mockCredit);
        when(mockCredit.getName()).thenReturn("Test Credit");

        userMenu.selectCredit();

        verify(mockClient, times(1)).takeCredit(mockCredit);
    }

    @Test
    void testEarlyRepayment() {
        String input = "Test Credit\n"; // User enters "Test Credit"
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        userMenu = new UserMenu(mockClient, mockBank, scanner);

        when(mockBank.getCreditByName("Test Credit")).thenReturn(mockCredit);

        userMenu.earlyRepayment();

        verify(mockClient, times(1)).repayCredit(mockCredit);
    }

    @Test
    void testIncreaseCreditLine() {
        String input = "Test Credit\n5000.0\n"; // Коректний ввід для збільшення лінії
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        userMenu = new UserMenu(mockClient, mockBank, scanner);

        when(mockBank.getCreditByName("Test Credit")).thenReturn(mockCredit);

        userMenu.increaseCreditLine();

        verify(mockClient,
                times(1)).increaseCredit(mockCredit, 5000.0);
    }


    @Test
    void testSaveSelectedCreditsToFile() throws IOException {
        String input = "credits.txt\n";
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        userMenu = new UserMenu(mockClient, mockBank, scanner);

        when(mockClient.getSelectedCredits()).thenReturn(Collections.singletonList(mockCredit));
        when(mockCredit.getName()).thenReturn("Test Credit");

        File file = new File("credits.txt");
        if (file.exists()) {
            file.delete();
        }

        userMenu.saveSelectedCreditsToFile();

        assertTrue(file.exists());
        file.delete();
    }

    @Test
    void testLoadSelectedCreditsFromFile() throws IOException {
        String input = "credits.txt\n";
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        userMenu = new UserMenu(mockClient, mockBank, scanner);

        when(mockBank.getCreditByName("Test Credit")).thenReturn(mockCredit);
        when(mockCredit.getName()).thenReturn("Test Credit");

        // Prepare mock file
        File file = new File("credits.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Test Credit");
        }

        userMenu.loadSelectedCreditsFromFile();

        verify(mockClient, times(1)).takeCredit(mockCredit);
        file.delete();
    }

    @Test
    void testLoadSelectedCreditsFileNotFound() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        String input = "nonexistent.txt\n";
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        userMenu = new UserMenu(mockClient, mockBank, scanner);

        userMenu.loadSelectedCreditsFromFile();

        String expectedErrorMessage = "Файл не знайдено:";
        assertTrue(outputStream.toString().contains(expectedErrorMessage),
                "Повідомлення про помилку не було виведено. Текст виведення: " + outputStream.toString());

        System.setOut(System.out);
    }

    @Test
    void testInvalidMenuChoice() {
        String input = "9\n";
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        userMenu = new UserMenu(mockClient, mockBank, scanner);

        userMenu.showClientMenu();

    }

    @Test
    void testSelectCreditNotFound() {
        String input = "Nonexistent Credit\n";
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        userMenu = new UserMenu(mockClient, mockBank, scanner);

        when(mockBank.getCreditByName("Nonexistent Credit")).thenReturn(null);

        userMenu.selectCredit();

        verify(mockClient, never()).takeCredit(any());
    }

    @Test
    void testSaveSelectedCreditsToFileIOException() {
        String input = "invalid_path/credits.txt\n";
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        userMenu = new UserMenu(mockClient, mockBank, scanner);

        when(mockClient.getSelectedCredits()).thenReturn(Collections.singletonList(mockCredit));
        when(mockCredit.getName()).thenReturn("Test Credit");

        userMenu.saveSelectedCreditsToFile();

    }

    @Test
    void testLoadSelectedCreditsFileIOException() {
        String input = "invalid_file.txt\n";
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        userMenu = new UserMenu(mockClient, mockBank, scanner);

        userMenu.loadSelectedCreditsFromFile();

    }
}