package isstrobogrammic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;





public class Sonnet4GTTest {

    @Test
    public void testNullInput() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(null));
    }

    @Test
    public void testEmptyString() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(""));
    }

    @Test
    public void testSingleValidDigits() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("0"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("1"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("8"));
    }

    @Test
    public void testSingleInvalidDigits() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("2"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("3"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("4"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("5"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("7"));
    }

    @Test
    public void testValidStrobogrammaticNumbers() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("69"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("96"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("1001"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("11"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("88"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("101"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("609"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("808"));
    }

    @Test
    public void testInvalidStrobogrammaticNumbers() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("12"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("123"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("67"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("89"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("1234"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("16"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("61"));
    }

    @Test
    public void testLongerValidNumbers() {
       // assertTrue(StrobogrammicNumber.isStrobogrammic("96869"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("18881"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("1000001"));
    }

    @Test
    public void testLongerInvalidNumbers() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("12321"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("10203"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("123456"));
    }
}