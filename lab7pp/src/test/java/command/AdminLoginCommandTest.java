package command;
import menu.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
class AdminLoginCommandTest {
    private Menu menuMock;
    private AdminLoginCommand adminLoginCommand;
    @BeforeEach
    void setUp() {
        // Створюємо мок об'єкта Menu
        menuMock = mock(Menu.class);
        // Ініціалізуємо команду з цим моком
        adminLoginCommand = new AdminLoginCommand(menuMock);
    }
    @Test
    void execute_shouldCallAdminLogin() {
        // Виконуємо команду
        adminLoginCommand.execute();
        // Перевіряємо, що метод adminLogin() було викликано один раз
        verify(menuMock, times(1)).adminLogin();
    }
    @Test
    void execute_whenAdminLoginThrowsException_shouldPropagateException() {
        // Налаштовуємо мок так, щоб метод adminLogin() викликав виняток
        doThrow(new RuntimeException("Login error")).when(menuMock).adminLogin();
        // Перевіряємо, що виняток прокидається, коли виконується execute()
        assertThrows(RuntimeException.class, adminLoginCommand::execute);
        // Перевіряємо, що метод adminLogin() все одно викликано
        verify(menuMock, times(1)).adminLogin();
    }
}