package isstrobogrammic;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;




class StrobogrammicNumberGTTest {

    @Test
    void testNullInput() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(null));
    }

    @Test
    void testEmptyString() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(""));
    }

    @Test
    void testSingleDigitStrobogrammic() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("0"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("1"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("8"));
    }

    @Test
    void testSingleDigitNonStrobogrammic() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("2"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("3"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("4"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("5"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("7"));
    }

    @Test
    void testValidStrobogrammicNumbers() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("69"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("96"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("818"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("1001"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("609"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("906"));
    }

    @Test
    void testInvalidStrobogrammicNumbers() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("123"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("12"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("678"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("8080"));
        //assertFalse(StrobogrammicNumber.isStrobogrammic("689"));
    }

    @Test
    void testPalindromeButNotStrobogrammic() {
       // assertFalse(StrobogrammicNumber.isStrobogrammic("11"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("22"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("33"));
    }
}