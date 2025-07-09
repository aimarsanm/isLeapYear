package isipv4valid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;





public class Sonnet4GTTest {
    private IPAddressChecker checker = new IPAddressChecker();

    @Test
    public void testValidIPAddresses() {
        assertEquals(1, checker.isValidIPV4("192.168.1.1"));
        assertEquals(1, checker.isValidIPV4("0.0.0.0"));
        assertEquals(1, checker.isValidIPV4("255.255.255.255"));
        assertEquals(1, checker.isValidIPV4("127.0.0.1"));
        assertEquals(1, checker.isValidIPV4("1.2.3.4"));
    }

    @Test
    public void testInvalidIPAddresses() {
        assertEquals(0, checker.isValidIPV4("256.1.1.1"));
        assertEquals(0, checker.isValidIPV4("1.1.1"));
        assertEquals(0, checker.isValidIPV4("1.1.1.1.1"));
        assertEquals(0, checker.isValidIPV4("1..1.1"));
        assertEquals(0, checker.isValidIPV4("1.1.1."));
        assertEquals(0, checker.isValidIPV4(".1.1.1"));
        assertEquals(0, checker.isValidIPV4("01.1.1.1"));
        assertEquals(0, checker.isValidIPV4("1.01.1.1"));
        assertEquals(0, checker.isValidIPV4("1.1.01.1"));
        assertEquals(0, checker.isValidIPV4("1.1.1.01"));
    }

    @Test
    public void testNullAndEmptyInput() {
        assertEquals(0, checker.isValidIPV4(null));
        assertEquals(0, checker.isValidIPV4(""));
        assertEquals(0, checker.isValidIPV4("..."));
    }

    @Test
    public void testInvalidCharacters() {
        assertEquals(0, checker.isValidIPV4("192.168.1.a"));
        assertEquals(0, checker.isValidIPV4("192.168.1.-1"));
        assertEquals(0, checker.isValidIPV4("192.168.1.1a"));
        assertEquals(0, checker.isValidIPV4("192.168.1.1."));
    }

    @Test
    public void testValidIPV4Part() {
        assertTrue(checker.isValidIPV4Part("0"));
        assertTrue(checker.isValidIPV4Part("255"));
        assertTrue(checker.isValidIPV4Part("192"));
        assertTrue(checker.isValidIPV4Part("1"));
        assertFalse(checker.isValidIPV4Part("256"));
        assertFalse(checker.isValidIPV4Part("01"));
        assertFalse(checker.isValidIPV4Part("00"));
        assertFalse(checker.isValidIPV4Part("1234"));
        assertFalse(checker.isValidIPV4Part("a"));
        assertFalse(checker.isValidIPV4Part("1a"));
    }
}