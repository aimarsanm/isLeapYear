package isstrobogrammic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;





public class GPTo4minipreGTTest {

    @Test
    public void testNullReturnsTrue() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(null));
    }

    @Test
    public void testEmptyStringReturnsTrue() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(""));
    }

    @Test
    public void testSingleDigitValid() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("0"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("1"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("8"));
    }

    @Test
    public void testSingleDigitInvalid() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("2"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("3"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("5"));
    }

    @Test
    public void testEvenLengthValid() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("69"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("96"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("88"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("00"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("6969"));
    }

    @Test
    public void testEvenLengthInvalid() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("10"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("8180"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("1234"));
    }

    @Test
    public void testOddLengthValid() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("101"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("609"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("808"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("010"));
    }

    @Test
    public void testOddLengthInvalid() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("1212"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("962"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("6192"));
    }

    @Test
    public void testRandomInvalidStrings() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("abc"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("!@#"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("12a21"));
    }
}