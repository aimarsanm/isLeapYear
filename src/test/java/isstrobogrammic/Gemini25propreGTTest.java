package isstrobogrammic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;






public class Gemini25propreGTTest {

    @Test
    public void testIsStrobogrammic_NullOrEmpty_ReturnsTrue() {
        assertTrue( StrobogrammicNumber.isStrobogrammic(null));
        assertTrue( StrobogrammicNumber.isStrobogrammic(""));
    }

    @Test
    public void testIsStrobogrammic_SingleDigitStrobogrammic_ReturnsTrue() {
        assertTrue( StrobogrammicNumber.isStrobogrammic("0"));
        assertTrue( StrobogrammicNumber.isStrobogrammic("1"));
        assertTrue( StrobogrammicNumber.isStrobogrammic("8"));
    }

    @Test
    public void testIsStrobogrammic_SingleDigitNonStrobogrammic_ReturnsFalse() {
        assertFalse( StrobogrammicNumber.isStrobogrammic("2"));
        assertFalse( StrobogrammicNumber.isStrobogrammic("3"));
        assertFalse( StrobogrammicNumber.isStrobogrammic("4"));
        assertFalse( StrobogrammicNumber.isStrobogrammic("5"));
        assertFalse( StrobogrammicNumber.isStrobogrammic("7"));
    }

    @Test
    public void testIsStrobogrammic_MultiDigitStrobogrammic_ReturnsTrue() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("11"));
        assertTrue( StrobogrammicNumber.isStrobogrammic("69"));
        assertTrue( StrobogrammicNumber.isStrobogrammic("88"));
        assertTrue( StrobogrammicNumber.isStrobogrammic("96"));
        assertTrue( StrobogrammicNumber.isStrobogrammic("101"));
        assertTrue( StrobogrammicNumber.isStrobogrammic("619"));
        assertTrue( StrobogrammicNumber.isStrobogrammic("888"));
        assertTrue( StrobogrammicNumber.isStrobogrammic("181"));
        assertTrue( StrobogrammicNumber.isStrobogrammic("609"));
        assertTrue( StrobogrammicNumber.isStrobogrammic("986"));
        assertTrue( StrobogrammicNumber.isStrobogrammic("6889"));
    }

    @Test
    public void testIsStrobogrammic_MultiDigitNonStrobogrammic_ReturnsFalse() {
        assertFalse( StrobogrammicNumber.isStrobogrammic("12"));
        assertFalse( StrobogrammicNumber.isStrobogrammic("68"));
        assertFalse( StrobogrammicNumber.isStrobogrammic("91"));
        assertFalse( StrobogrammicNumber.isStrobogrammic("123"));
        assertFalse( StrobogrammicNumber.isStrobogrammic("696"));
        assertFalse( StrobogrammicNumber.isStrobogrammic("100"));
        assertFalse( StrobogrammicNumber.isStrobogrammic("25"));
    }

  
}