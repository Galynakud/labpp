package bank;
import bank.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
class ClientTest {
    private Client client;
    private Credit credit1;
    private Credit credit2;
    @BeforeEach
    void setUp() {
        client = new Client("Іван Петренко", "password123", 100.0);
        credit1 = new Credit("Споживчий кредит", 5000, 5.5, 24, true);
        credit2 = new Credit("Авто кредит", 10000, 6.0, 36, true);
    }
    @Test
    void testGetName() {
        assertEquals("Іван Петренко", client.getName());
    }
    @Test
    void testGetPassword() {
        assertEquals("password123", client.getPassword());
    }
    @Test
    void testGetInitialAmount() {
        assertEquals(100.0, client.getInitialAmount());
    }
    @Test
    void testTakeCredit() {
        client.takeCredit(credit1);
        assertTrue(client.getSelectedCredits().contains(credit1));
    }
    @Test
    void testRepayCredit() {
        client.takeCredit(credit1);
        client.repayCredit(credit1);
        assertFalse(client.getSelectedCredits().contains(credit1));
    }
    @Test
    void testIncreaseCredit() {
        client.takeCredit(credit1);
        client.increaseCredit(credit1, 1000);
        assertEquals(6000, credit1.getAmount());
    }
    @Test
    void testGetCreditByName() {
        client.takeCredit(credit1);
        client.takeCredit(credit2);
        assertEquals(credit1, client.getCreditByName("Споживчий кредит"));
        assertEquals(credit2, client.getCreditByName("Авто кредит"));
        assertNull(client.getCreditByName("Неіснуючий кредит"));
    }
    @Test
    void testGetSelectedCredits() {
        client.takeCredit(credit1);
        client.takeCredit(credit2);
        List<Credit> credits = client.getSelectedCredits();
        assertEquals(2, credits.size());
        assertTrue(credits.contains(credit1));
        assertTrue(credits.contains(credit2));
    }
}