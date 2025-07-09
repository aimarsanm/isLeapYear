package isstrobogrammic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;





public class Sonnet37thinkGTTest {
    
    @Test
    public void testValidStrobogrammicNumbers() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("0"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("1"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("8"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("11"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("69"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("88"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("96"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("609"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("818"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("1001"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("6009"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("8008"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("9006"));
        //assertTrue(StrobogrammicNumber.isStrobogrammic("69896"));
    }
    
    @Test
    public void testNonStrobogrammicNumbers() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("2"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("3"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("4"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("5"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("7"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("12"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("123"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("678"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("789"));
    }
    
    @Test
    public void testEdgeCases() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(null));
        assertTrue(StrobogrammicNumber.isStrobogrammic(""));
    }
    
    @Test
    public void testNonDigitCharacters() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("a"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("abc"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("1a1"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("6a9"));
    }
    
    @Test
    public void testLongStrobogrammicNumbers() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("1001001"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("10001"));
       // assertTrue(StrobogrammicNumber.isStrobogrammic("69696"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("88888888"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("11111111"));
    }
    
    @Test
    public void testMixedCases() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("1234567890"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("1689"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("6897"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("1802"));
    }
}