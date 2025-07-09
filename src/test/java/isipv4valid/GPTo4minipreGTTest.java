package isipv4valid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class GPTo4minipreGTTest {

    private final IPAddressChecker checker = new IPAddressChecker();

    @Test
    public void testValidIPv4Addresses() {
        assertEquals(1, checker.isValidIPV4("0.0.0.0"));
        assertEquals(1, checker.isValidIPV4("255.255.255.255"));
        assertEquals(1, checker.isValidIPV4("192.168.1.1"));
        assertEquals(1, checker.isValidIPV4("1.2.3.4"));
        assertEquals(1, checker.isValidIPV4("123.45.67.89"));
    }

    @Test
    public void testInvalidIPv4Addresses_WrongDotCount() {
        assertEquals(0, checker.isValidIPV4(null));
        assertEquals(0, checker.isValidIPV4(""));
        assertEquals(0, checker.isValidIPV4("192.168.1"));
        assertEquals(0, checker.isValidIPV4("192.168.1.1.1"));
        assertEquals(0, checker.isValidIPV4("19216811"));
    }

    @Test
    public void testInvalidIPv4Addresses_EmptyOrExtraDots() {
        assertEquals(0, checker.isValidIPV4("192..168.1"));
        assertEquals(0, checker.isValidIPV4(".192.168.1.1"));
        assertEquals(0, checker.isValidIPV4("192.168.1.1."));
        assertEquals(0, checker.isValidIPV4("..."));
    }

    @Test
    public void testInvalidIPv4Addresses_NonNumericOrSpaces() {
        assertEquals(0, checker.isValidIPV4("abc.def.gha.bcd"));
        assertEquals(0, checker.isValidIPV4("1 2.3.4.5"));
        assertEquals(0, checker.isValidIPV4("1.2.3.4 "));
        assertEquals(0, checker.isValidIPV4(" 1.2.3.4"));
    }

    @Test
    public void testInvalidIPv4Addresses_LeadingZeros() {
        assertEquals(0, checker.isValidIPV4("01.2.3.4"));
        assertEquals(0, checker.isValidIPV4("00.00.00.00"));
        assertEquals(0, checker.isValidIPV4("001.002.003.004"));
    }

    @Test
    public void testInvalidIPv4Addresses_OutOfRange() {
        assertEquals(0, checker.isValidIPV4("256.100.100.100"));
        assertEquals(0, checker.isValidIPV4("100.256.100.100"));
        assertEquals(0, checker.isValidIPV4("100.100.256.100"));
        assertEquals(0, checker.isValidIPV4("100.100.100.256"));
        assertEquals(0, checker.isValidIPV4("-1.2.3.4"));
    }

    @Test
    public void testValidIPv4Part() {
        assertTrue(checker.isValidIPV4Part("0"));
        assertTrue(checker.isValidIPV4Part("5"));
        assertTrue(checker.isValidIPV4Part("10"));
        assertTrue(checker.isValidIPV4Part("255"));
        assertTrue(checker.isValidIPV4Part("123"));
    }

    @Test
    public void testInvalidIPv4Part_NonDigitOrLength() {
        assertFalse(checker.isValidIPV4Part(""));
        assertFalse(checker.isValidIPV4Part(" "));
        assertFalse(checker.isValidIPV4Part("a1"));
        assertFalse(checker.isValidIPV4Part("1a"));
        assertFalse(checker.isValidIPV4Part("1000"));
    }

    @Test
    public void testInvalidIPv4Part_LeadingZeroOrOutOfRange() {
        assertFalse(checker.isValidIPV4Part("00"));
        assertFalse(checker.isValidIPV4Part("01"));
        assertFalse(checker.isValidIPV4Part("001"));
        assertFalse(checker.isValidIPV4Part("256"));
        assertFalse(checker.isValidIPV4Part("-1"));
    }
}