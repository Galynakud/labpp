package command;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class ExitCommandTest {

    @Test
    void testExecuteDoesNotExit() {
        // Мокання ExitHandler
        ExitHandler exitHandler = mock(ExitHandler.class);

        // Створення ExitCommand з моканим ExitHandler
        ExitCommand exitCommand = new ExitCommand(exitHandler);

        // Викликаємо метод execute
        exitCommand.execute();

        // Перевіряємо, чи викликано метод exit без реального виходу
        verify(exitHandler, times(1)).exit(0);
    }
}