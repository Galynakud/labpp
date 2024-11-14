package command;

public class ExitCommand implements Command {

    private final ExitHandler exitHandler;

    public ExitCommand(ExitHandler exitHandler) {
        this.exitHandler = exitHandler;
    }

    public ExitCommand() {
        this.exitHandler = new DefaultExitHandler() {
            @Override
            public void exit(int i) {

            }
        };
    }

    @Override
    public void execute() {
        System.out.println("Вихід з програми...");
        exitHandler.exit(0); // Викликаємо exit через handler
    }
}