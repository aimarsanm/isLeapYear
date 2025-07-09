
package isipv4valid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;





public class Gemini25propreGTTest {

    private final IPAddressChecker checker = new IPAddressChecker();

    @Test
    public void testIsValidIPV4Part_Valid() {
        assertTrue(checker.isValidIPV4Part("0"));
        assertTrue(checker.isValidIPV4Part("1"));
        assertTrue(checker.isValidIPV4Part("10"));
        assertTrue(checker.isValidIPV4Part("100"));
        assertTrue(checker.isValidIPV4Part("255"));
    }

    @Test
    public void testIsValidIPV4Part_InvalidLength() {
        assertFalse(checker.isValidIPV4Part("1234"));
    }

    @Test
    public void testIsValidIPV4Part_InvalidCharacters() {
        assertFalse(checker.isValidIPV4Part("a"));
        assertFalse(checker.isValidIPV4Part("1a"));
    }

    @Test
    public void testIsValidIPV4Part_InvalidLeadingZero() {
        assertFalse(checker.isValidIPV4Part("01"));
        assertFalse(checker.isValidIPV4Part("00"));
        assertFalse(checker.isValidIPV4Part("099"));
    }

    @Test
    public void testIsValidIPV4Part_InvalidRange() {
        assertFalse(checker.isValidIPV4Part("256"));
        assertFalse(checker.isValidIPV4Part("300"));
    }

    @Test
    public void testIsValidIPV4_ValidIPs() {
        assertEquals(1, checker.isValidIPV4("1.1.1.1"));
        assertEquals(1, checker.isValidIPV4("255.255.255.255"));
        assertEquals(1, checker.isValidIPV4("0.0.0.0"));
        assertEquals(1, checker.isValidIPV4("192.168.0.1"));
    }

    @Test
    public void testIsValidIPV4_InvalidIPs() {
        assertEquals(0, checker.isValidIPV4("1.1.1")); // Not enough parts
        assertEquals(0, checker.isValidIPV4("1.1.1.1.1")); // Too many parts
        assertEquals(0, checker.isValidIPV4("256.1.1.1")); // Part out of range
        assertEquals(0, checker.isValidIPV4("1.1.1.abc")); // Invalid characters
        assertEquals(0, checker.isValidIPV4("1.01.1.1")); // Leading zero
        assertEquals(0, checker.isValidIPV4("1..1.1")); // Empty part
        assertEquals(0, checker.isValidIPV4("1.1.1.")); // Trailing dot
        assertEquals(0, checker.isValidIPV4(".1.1.1")); // Leading dot
        assertEquals(0, checker.isValidIPV4("")); // Empty string
        assertEquals(0, checker.isValidIPV4(null)); // Null string
        assertEquals(0, checker.isValidIPV4("1234.1.1.1")); // Part too long
    }
}