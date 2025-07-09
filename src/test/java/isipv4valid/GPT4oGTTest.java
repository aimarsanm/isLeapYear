package isipv4valid;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GPT4oGTTest {

    private final IPAddressChecker checker = new IPAddressChecker();

    @Test
    public void testIsValidIPV4_withValidIPs() {
        assertEquals(1, checker.isValidIPV4("1.1.1.1"));
        assertEquals(1, checker.isValidIPV4("255.255.255.255"));
        assertEquals(1, checker.isValidIPV4("0.0.0.0"));
        assertEquals(1, checker.isValidIPV4("192.168.1.1"));
    }

    @Test
    public void testIsValidIPV4_withNullInput() {
        assertEquals(0, checker.isValidIPV4(null));
    }

    @Test
    public void testIsValidIPV4_withEmptyString() {
        assertEquals(0, checker.isValidIPV4(""));
    }

    @Test
    public void testIsValidIPV4_withIncorrectNumberOfParts() {
        assertEquals(0, checker.isValidIPV4("1.1.1"));
        assertEquals(0, checker.isValidIPV4("1.1.1.1.1"));
    }

    @Test
    public void testIsValidIPV4_withPartsOutOfRange() {
        assertEquals(0, checker.isValidIPV4("256.1.1.1"));
        assertEquals(0, checker.isValidIPV4("1.256.1.1"));
        assertEquals(0, checker.isValidIPV4("1.1.256.1"));
        assertEquals(0, checker.isValidIPV4("1.1.1.256"));
    }

    @Test
    public void testIsValidIPV4_withNonNumericParts() {
        assertEquals(0, checker.isValidIPV4("a.1.1.1"));
        assertEquals(0, checker.isValidIPV4("1.b.1.1"));
        assertEquals(0, checker.isValidIPV4("1.1.c.1"));
        assertEquals(0, checker.isValidIPV4("1.1.1.d"));
        assertEquals(0, checker.isValidIPV4("abc.def.ghi.jkl"));
    }

    @Test
    public void testIsValidIPV4_withLeadingZeros() {
        assertEquals(0, checker.isValidIPV4("01.1.1.1"));
        assertEquals(0, checker.isValidIPV4("1.02.1.1"));
        assertEquals(0, checker.isValidIPV4("1.1.03.1"));
        assertEquals(0, checker.isValidIPV4("1.1.1.04"));
    }

    @Test
    public void testIsValidIPV4_withEmptyParts() {
        assertEquals(0, checker.isValidIPV4("1..1.1"));
        assertEquals(0, checker.isValidIPV4(".1.1.1"));
        assertEquals(0, checker.isValidIPV4("1.1.1."));
        assertEquals(0, checker.isValidIPV4("1.1..1"));
    }

    @Test
    public void testIsValidIPV4_withNegativeNumbers() {
        // The isValidIPV4Part method handles this by checking for non-digit characters.
        assertEquals(0, checker.isValidIPV4("-1.1.1.1"));
        assertEquals(0, checker.isValidIPV4("1.-1.1.1"));
    }

    @Test
    public void testIsValidIPV4_withPartsLongerThanThreeDigits() {
        assertEquals(0, checker.isValidIPV4("1234.1.1.1"));
        assertEquals(0, checker.isValidIPV4("1.1234.1.1"));
    }
}