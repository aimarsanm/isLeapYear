package isipv4valid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;





public class Sonnet37ThinkGTTest {
    
    private final IPAddressChecker checker = new IPAddressChecker();
    
    @Test
    public void testValidIPAddresses() {
        assertEquals(1, checker.isValidIPV4("192.168.1.1"));
        assertEquals(1, checker.isValidIPV4("0.0.0.0"));
        assertEquals(1, checker.isValidIPV4("255.255.255.255"));
        assertEquals(1, checker.isValidIPV4("10.0.0.1"));
    }
    
    @Test
    public void testNullInput() {
        assertEquals(0, checker.isValidIPV4(null));
    }
    
    @Test
    public void testEmptyString() {
        assertEquals(0, checker.isValidIPV4(""));
    }
    
    @Test
    public void testTooFewDots() {
        assertEquals(0, checker.isValidIPV4("192.168.1"));
        assertEquals(0, checker.isValidIPV4("192.168"));
        assertEquals(0, checker.isValidIPV4("192"));
    }
    
    @Test
    public void testTooManyDots() {
        assertEquals(0, checker.isValidIPV4("192.168.1.1.1"));
        assertEquals(0, checker.isValidIPV4("192.168.1.1."));
    }
    
    @Test
    public void testConsecutiveDots() {
        assertEquals(0, checker.isValidIPV4("192..168.1.1"));
        assertEquals(0, checker.isValidIPV4("192.168..1.1"));
        assertEquals(0, checker.isValidIPV4("192.168.1..1"));
        assertEquals(0, checker.isValidIPV4("1...1"));
    }
    
    @Test
    public void testOutOfRangeValues() {
        assertEquals(0, checker.isValidIPV4("256.256.256.256"));
        assertEquals(0, checker.isValidIPV4("999.999.999.999"));
        assertEquals(0, checker.isValidIPV4("192.168.1.256"));
    }
    
    @Test
    public void testNonNumericParts() {
        assertEquals(0, checker.isValidIPV4("a.b.c.d"));
        assertEquals(0, checker.isValidIPV4("192.168.1.a"));
    }
    
    @Test
    public void testLeadingZeros() {
        assertEquals(0, checker.isValidIPV4("01.01.01.01"));
        assertEquals(0, checker.isValidIPV4("192.168.001.001"));
    }
    
    @Test
    public void testSpecialCases() {
        assertEquals(0, checker.isValidIPV4(".1.1.1"));
        assertEquals(0, checker.isValidIPV4("1.1.1."));
    }
}