package isipv4valid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class GPTo3miniGTTest {

    private final IPAddressChecker checker = new IPAddressChecker();

    // Valid IP addresses tests
    @Test
    public void testValidIPAddresses() {
        // Standard loopback
        assertEquals(1, checker.isValidIPV4("127.0.0.1"));
        // Lowest possible IP
        assertEquals(1, checker.isValidIPV4("0.0.0.0"));
        // Highest possible IP
        assertEquals(1, checker.isValidIPV4("255.255.255.255"));
        // Middle values
        assertEquals(1, checker.isValidIPV4("192.168.1.1"));
        // Single digit parts
        assertEquals(1, checker.isValidIPV4("1.2.3.4"));
    }

    // Invalid IP addresses tests
    @Test
    public void testInvalidIPAddresses() {
        // Out of range numbers
        assertEquals(0, checker.isValidIPV4("256.100.100.100"));
        assertEquals(0, checker.isValidIPV4("100.100.100.300"));
        
        // Wrong number of parts
        assertEquals(0, checker.isValidIPV4("192.168.1"));          // Too few parts
        assertEquals(0, checker.isValidIPV4("192.168.1.1.1"));        // Too many parts
        
        // Invalid numbers (leading zeros)
        assertEquals(0, checker.isValidIPV4("01.2.3.4"));
        assertEquals(0, checker.isValidIPV4("1.02.3.4"));
        
        // Non-numeric characters
        assertEquals(0, checker.isValidIPV4("a.b.c.d"));
        assertEquals(0, checker.isValidIPV4("123.abc.123.123"));
        
        // Empty part or extra delimiters
        assertEquals(0, checker.isValidIPV4("192..1.1"));
        assertEquals(0, checker.isValidIPV4(".192.168.1.1"));
        assertEquals(0, checker.isValidIPV4("192.168.1.1."));
        
        // Null IP string
        assertEquals(0, checker.isValidIPV4(null));
    }
    
    // Test for valid single part using isValidIPV4Part method directly
    @Test
    public void testIsValidIPV4Part() {
        // Valid parts
        assertTrue(checker.isValidIPV4Part("0"));
        assertTrue(checker.isValidIPV4Part("10"));
        assertTrue(checker.isValidIPV4Part("255"));
        
        // Part length more than 3
        assertFalse(checker.isValidIPV4Part("1234"));
        
        // Leading zero invalid cases
        assertFalse(checker.isValidIPV4Part("00"));
        assertFalse(checker.isValidIPV4Part("01"));
        
        // Non-digit characters
        assertFalse(checker.isValidIPV4Part("a1"));
    }
}