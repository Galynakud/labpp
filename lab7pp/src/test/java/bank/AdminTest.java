package bank;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class AdminTest {
    @Test
    void testGetUsername() {
        Admin admin = new Admin("adminUser", "password123");
        assertEquals("adminUser", admin.getUsername(), "The username should match the one provided in the constructor.");
    }
    @Test
    void testGetPassword() {
        Admin admin = new Admin("adminUser", "password123");
        assertEquals("password123", admin.getPassword(), "The password should match the one provided in the constructor.");
    }
    @Test
    void testUsernameNotNull() {
        Admin admin = new Admin("adminUser", "password123");
        assertNotNull(admin.getUsername(), "The username should not be null.");
    }
    @Test
    void testPasswordNotNull() {
        Admin admin = new Admin("adminUser", "password123");
        assertNotNull(admin.getPassword(), "The password should not be null.");
    }
    @Test
    void testEmptyUsername() {
        Admin admin = new Admin("", "password123");
        assertEquals("", admin.getUsername(), "The username should match the empty string provided in the constructor.");
    }
    @Test
    void testEmptyPassword() {
        Admin admin = new Admin("adminUser", "");
        assertEquals("", admin.getPassword(), "The password should match the empty string provided in the constructor.");
    }
}