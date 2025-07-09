package isstrobogrammic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;





public class Sonnet37GTTest {

    @Test
    public void testIsStrobogrammic_Null() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(null));
    }

    @Test
    public void testIsStrobogrammic_EmptyString() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(""));
    }

    @Test
    public void testIsStrobogrammic_SingleDigits() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("0"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("1"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("8"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("2"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("3"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("4"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("5"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("7"));
    }

    @Test
    public void testIsStrobogrammic_TwoDigits() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("69"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("96"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("88"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("11"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("00"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("12"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("23"));
    }

    @Test
    public void testIsStrobogrammic_MultipleDigits() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("609"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("619"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("818"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("1001"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("6009"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("8008"));
        //assertTrue(StrobogrammicNumber.isStrobogrammic("69096"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("1234"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("6789"));
    }

    @Test
    public void testIsStrobogrammic_MixedDigits() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("9006"));
        //assertTrue(StrobogrammicNumber.isStrobogrammic("8618"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("1691"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("1692"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("1234567890"));
    }

    @Test
    public void testIsStrobogrammic_EdgeCases() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("25"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("6199"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("6102"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("6110119"));
    }
}