package isstrobogrammic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


public class GPTo1preGTTest {

    @Test
    public void testNullString() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(null));
    }

    @Test
    public void testEmptyString() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(""));
    }

    @Test
    public void testSingleDigitStrobogrammic() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("0"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("1"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("8"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("2"));
    }

    @Test
    public void testTwoDigitStrobogrammic() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("69"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("96"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("11"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("12"));
    }

    @Test
    public void testThreeDigitStrobogrammic() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("818"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("609"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("906"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("123"));
    }

    @Test
    public void testMultipleDigits() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("1001"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("808"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("1002"));
    }
}