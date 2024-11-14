package bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
class BankTest {
    private Bank bank;
    @BeforeEach
    void setUp() {
        bank = new Bank();
    }
    @Test
    void testGetCredits() {
        List<Credit> credits = bank.getCredits();
        assertNotNull(credits, "Credits list should not be null.");
        assertEquals(15, credits.size(), "Initial credits list size should be 15.");
    }
    @Test
    void testGetClients() {
        List<Client> clients = bank.getClients();
        assertNotNull(clients, "Clients list should not be null.");
        assertEquals(15, clients.size(), "Initial clients list size should be 15.");
    }
    @Test
    void testAddCredit() {
        Credit newCredit = new Credit("Новий кредит", 5000, 5.0, 24, true);
        bank.addCredit(newCredit);
        List<Credit> credits = bank.getCredits();
        assertEquals(16, credits.size(), "Credits list size should be 16 after adding a new credit.");
        assertEquals(newCredit, credits.get(credits.size() - 1), "Last credit in the list should be the newly added credit.");
    }
    @Test
    void testEditCreditValidIndex() {
        Credit updatedCredit = new Credit("Оновлений кредит", 10000, 6.0, 12, false);
        bank.editCredit(0, updatedCredit);
        Credit firstCredit = bank.getCredits().get(0);
        assertEquals(updatedCredit, firstCredit, "First credit in the list should be updated to the new credit.");
    }
    @Test
    void testEditCreditInvalidIndex() {
        Credit updatedCredit = new Credit("Оновлений кредит", 10000, 6.0, 12, false);
        bank.editCredit(-1, updatedCredit); // негативний індекс
        bank.editCredit(20, updatedCredit); // індекс за межами списку
        List<Credit> credits = bank.getCredits();
        assertEquals(15, credits.size(), "Credits list size should remain the same when editing with an invalid index.");
        assertNotEquals(updatedCredit, credits.get(0), "First credit should not be updated with invalid index.");
    }
    @Test
    void testGetCreditByNameFound() {
        Credit credit = bank.getCreditByName("Іпотечний кредит");
        assertNotNull(credit, "Credit with name 'Іпотечний кредит' should be found.");
        assertEquals("Іпотечний кредит", credit.getName(), "The found credit should have the correct name.");
    }
    @Test
    void testGetCreditByNameNotFound() {
        Credit credit = bank.getCreditByName("Несуществующий кредит");
        assertNull(credit, "Credit with a non-existent name should return null.");
    }
}