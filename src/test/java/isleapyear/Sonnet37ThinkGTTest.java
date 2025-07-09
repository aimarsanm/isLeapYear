package isleapyear;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Sonnet37ThinkGTTest {
    
    private LeapYear leapYear;
    
    @BeforeEach
    public void setUp() {
        leapYear = new LeapYear();
    }
    
    @Test
    public void testValidLeapYears() throws NumberFormatException, NullPointerException, EmptyException {
        assertTrue(leapYear.isLeapYear("2000"));
        assertTrue(leapYear.isLeapYear("2020"));
        assertTrue(leapYear.isLeapYear("2024"));
        assertTrue(leapYear.isLeapYear("1984"));
        assertTrue(leapYear.isLeapYear("4"));
        assertTrue(leapYear.isLeapYear("2096"));
    }
    
    @Test
    public void testNonLeapYears() throws NumberFormatException, NullPointerException, EmptyException {
        assertFalse(leapYear.isLeapYear("2023"));
        assertFalse(leapYear.isLeapYear("1999"));
        assertFalse(leapYear.isLeapYear("2021"));
        assertFalse(leapYear.isLeapYear("1"));
        assertFalse(leapYear.isLeapYear("3"));
        assertFalse(leapYear.isLeapYear("2097"));
    }
    
    @Test
    public void testOutOfRangeYears() throws NumberFormatException, NullPointerException, EmptyException {
        assertFalse(leapYear.isLeapYear("0"));
        assertFalse(leapYear.isLeapYear("-4"));
        assertFalse(leapYear.isLeapYear("2104")); // Leap year, but > 2100
    }
   /* 
    @Test
    public void testEmptyInput() throws NumberFormatException, NullPointerException, EmptyException {
        leapYear.isLeapYear("");
    }
    
    @Test
    public void testNullInput() throws NumberFormatException, NullPointerException, EmptyException {
        leapYear.isLeapYear(null);
    }
    
    @Test
    public void testNonNumericInput() throws NumberFormatException, NullPointerException, EmptyException {
        leapYear.isLeapYear("not a number");
    }
    
    @Test
    public void testAlphanumericInput() throws NumberFormatException, NullPointerException, EmptyException {
        leapYear.isLeapYear("2020a");
    }
    
    @Test
    public void testSpecialCharactersInput() throws NumberFormatException, NullPointerException, EmptyException {
        leapYear.isLeapYear("2020!");
    }*/ 
}