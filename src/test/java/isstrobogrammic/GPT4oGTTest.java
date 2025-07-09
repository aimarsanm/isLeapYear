package isstrobogrammic;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;





class GPT4oGTTest {

    @Test
    void testStrobogrammicNumberValid() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("69"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("96"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("1001"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("818"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("11"));
    }

    @Test
    void testStrobogrammicNumberInvalid() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("123"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("12"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("678"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("1010"));
    }

    @Test
    void testEmptyString() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(""));
    }

    @Test
    void testNullInput() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(null));
    }

    @Test
    void testSingleDigitNumbers() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("0"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("1"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("8"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("6"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("9"));
    }

    @Test
    void testLongStrobogrammicNumber() {
        //assertTrue(StrobogrammicNumber.isStrobogrammic("609060"));
        //assertTrue(StrobogrammicNumber.isStrobogrammic("98689"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("123456"));
    }
}