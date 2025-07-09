package isstrobogrammic;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




public class GPTo3miniGTTest {

    @Test
    public void testNullInput() {
        // Null input should return true.
        assertTrue(StrobogrammicNumber.isStrobogrammic(null));
    }

    @Test
    public void testEmptyString() {
        // An empty string is considered strobogrammatic.
        assertTrue(StrobogrammicNumber.isStrobogrammic(""));
    }

    @Test
    public void testSingleDigitStrobogrammatic() {
        // Single valid strobogrammatic digits.
        assertTrue(StrobogrammicNumber.isStrobogrammic("0"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("1"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("8"));
    }

    @Test
    public void testValidStrobogrammaticNumbers() {
        // Multi-digit examples.
        assertTrue(StrobogrammicNumber.isStrobogrammic("69"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("96"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("88"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("818"));
        // "1001" is strobogrammatic: 1 maps to 1, 0 maps to 0.
        assertTrue(StrobogrammicNumber.isStrobogrammic("1001"));
    }

    @Test
    public void testInvalidStrobogrammaticNumbers() {
        // Examples with invalid characters or mismatches.
        assertFalse(StrobogrammicNumber.isStrobogrammic("2"));     // '2' is not a valid strobogrammatic digit.
        assertFalse(StrobogrammicNumber.isStrobogrammic("10"));    // '1' does not match '0'
        assertFalse(StrobogrammicNumber.isStrobogrammic("123"));   // Contains invalid digits.
        assertFalse(StrobogrammicNumber.isStrobogrammic("28"));    // '2' and '8' don't form a valid pair.
    }
}