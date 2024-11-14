package command;
import command.ClientLoginCommand;
import menu.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
class ClientLoginCommandTest {
    private Menu menu;
    private ClientLoginCommand clientLoginCommand;
    @BeforeEach
    void setUp() {
        // Мокаємо об'єкт Menu
        menu = mock(Menu.class);
        clientLoginCommand = new ClientLoginCommand(menu); // Створюємо команду з мока Menu
    }
    @Test
    void testExecuteCallsClientLogin() {
        // Викликаємо execute і перевіряємо, чи метод clientLogin був викликаний
        clientLoginCommand.execute();
        verify(menu, times(1)).clientLogin(); // Перевірка, що clientLogin викликаний 1 раз
    }
    @Test
    void testExecuteWhenClientLoginThrowsException() {
        // Якщо clientLogin кидає виключення, перевіряємо, що воно викидається
        doThrow(new RuntimeException("Test Exception")).when(menu).clientLogin();
        assertThrows(RuntimeException.class, clientLoginCommand::execute); // Перевірка на виняток
    }
}