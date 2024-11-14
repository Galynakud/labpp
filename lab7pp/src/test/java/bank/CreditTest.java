package bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CreditTest {
    private Credit credit;
    @BeforeEach
    void setUp() {
        credit = new Credit("Споживчий кредит", 5000, 5.5, 24, true);
    }
    @Test
    void testGetName() {
        assertEquals("Споживчий кредит", credit.getName());
    }
    @Test
    void testGetAmount() {
        assertEquals(5000, credit.getAmount());
    }
    @Test
    void testGetInterestRate() {
        assertEquals(5.5, credit.getInterestRate());
    }
    @Test
    void testGetTermMonths() {
        assertEquals(24, credit.getTermMonths());
    }
    @Test
    void testIsEarlyRepayment() {
        assertTrue(credit.isEarlyRepayment());
    }
    @Test
    void testSetName() {
        credit.setName("Новий кредит");
        assertEquals("Новий кредит", credit.getName());
    }
    @Test
    void testSetAmount() {
        credit.setAmount(6000);
        assertEquals(6000, credit.getAmount());
    }
    @Test
    void testSetInterestRate() {
        credit.setInterestRate(6.0);
        assertEquals(6.0, credit.getInterestRate());
    }
    @Test
    void testSetTermMonths() {
        credit.setTermMonths(36);
        assertEquals(36, credit.getTermMonths());
    }
    @Test
    void testSetEarlyRepayment() {
        credit.setEarlyRepayment(false);
        assertFalse(credit.isEarlyRepayment());
    }
    @Test
    void testIncreaseAmount() {
        credit.increaseAmount(1000);
        assertEquals(6000, credit.getAmount());
    }
}