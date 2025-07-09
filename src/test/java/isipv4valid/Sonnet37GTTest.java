package isipv4valid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;





public class Sonnet37GTTest {
    
    private final IPAddressChecker checker = new IPAddressChecker();
    
    @Test
    public void testValidIPAddress() {
        assertEquals(1, checker.isValidIPV4("192.168.1.1"));
        assertEquals(1, checker.isValidIPV4("10.0.0.1"));
        assertEquals(1, checker.isValidIPV4("127.0.0.1"));
        assertEquals(1, checker.isValidIPV4("255.255.255.255"));
        assertEquals(1, checker.isValidIPV4("0.0.0.0"));
    }
    
    @Test
    public void testInvalidIPAddress() {
        assertEquals(0, checker.isValidIPV4("256.256.256.256"));
        assertEquals(0, checker.isValidIPV4("192.168.1"));
        assertEquals(0, checker.isValidIPV4("192.168.1.1.1"));
        assertEquals(0, checker.isValidIPV4("..."));
        assertEquals(0, checker.isValidIPV4("192.168.1."));
    }
    
    @Test
    public void testIPAddressWithLeadingZeros() {
        assertEquals(0, checker.isValidIPV4("192.168.01.1"));
        assertEquals(0, checker.isValidIPV4("192.168.1.01"));
        assertEquals(0, checker.isValidIPV4("01.1.1.1"));
    }
    
    @Test
    public void testIPAddressWithNonNumericCharacters() {
        assertEquals(0, checker.isValidIPV4("a.b.c.d"));
        assertEquals(0, checker.isValidIPV4("192.168.1.a"));
        assertEquals(0, checker.isValidIPV4("192.168.a.1"));
    }
    
    @Test
    public void testNullIPAddress() {
        assertEquals(0, checker.isValidIPV4(null));
    }
    
    @Test
    public void testEmptyIPAddress() {
        assertEquals(0, checker.isValidIPV4(""));
    }
    
    @Test
    public void testEdgeCases() {
        assertEquals(1, checker.isValidIPV4("0.0.0.0"));
        assertEquals(1, checker.isValidIPV4("255.255.255.255"));
        assertEquals(0, checker.isValidIPV4("256.0.0.0"));
        assertEquals(0, checker.isValidIPV4("0.256.0.0"));
        assertEquals(0, checker.isValidIPV4("0.0.256.0"));
        assertEquals(0, checker.isValidIPV4("0.0.0.256"));
    }
    
    @Test
    public void testValidIPV4Part() {
        assertTrue(checker.isValidIPV4Part("0"));
        assertTrue(checker.isValidIPV4Part("1"));
        assertTrue(checker.isValidIPV4Part("100"));
        assertTrue(checker.isValidIPV4Part("255"));
        assertFalse(checker.isValidIPV4Part("256"));
        assertFalse(checker.isValidIPV4Part("01"));
        assertFalse(checker.isValidIPV4Part("001"));
        assertFalse(checker.isValidIPV4Part("a"));
        assertFalse(checker.isValidIPV4Part("1a"));
        assertFalse(checker.isValidIPV4Part("1234"));
    }
}