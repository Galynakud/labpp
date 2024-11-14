package command;
import menu.Menu;
public class AdminLoginCommand implements Command {
    private final Menu menu;
    public AdminLoginCommand(Menu menu) {
        this.menu = menu;
    }
    @Override
    public void execute() {
        menu.adminLogin();
    }
}