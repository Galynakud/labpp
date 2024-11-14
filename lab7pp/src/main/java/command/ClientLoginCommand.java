package command;
import menu.Menu;
public class ClientLoginCommand implements Command {
    private final Menu menu;
    public ClientLoginCommand(Menu menu) {
        this.menu = menu;
    }
    @Override
    public void execute() {
        menu.clientLogin();
    }
}