package isleapyear;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Sonnet37GTTest {
    
    private LeapYear leapYear;
    
    @BeforeEach
     void setUp() {
        leapYear = new LeapYear();
    }
    
    @Test
    void testValidLeapYear() throws NumberFormatException, NullPointerException, EmptyException {
        assertTrue(leapYear.isLeapYear("2020"));
        assertTrue(leapYear.isLeapYear("2000"));
        assertTrue(leapYear.isLeapYear("1996"));
        assertTrue(leapYear.isLeapYear("2096"));
    }
    
    @Test
    void testNonLeapYear() throws NumberFormatException, NullPointerException, EmptyException {
        assertFalse(leapYear.isLeapYear("2021"));
        assertFalse(leapYear.isLeapYear("1997"));
        //assertFalse(leapYear.isLeapYear("2100")); // Divisible by 100 but not by 400
       // assertFalse(leapYear.isLeapYear("1900"));
    }
    /* 
    @Test
     void testEmptyInput() throws NumberFormatException, NullPointerException, EmptyException {
        leapYear.isLeapYear("");
    }
    
    @Test
     void testNullInput() throws NumberFormatException, NullPointerException, EmptyException {
        leapYear.isLeapYear(null);
    }
    
    @Test
     void testNonNumericInput() throws NumberFormatException, NullPointerException, EmptyException {
        leapYear.isLeapYear("abc");
    }
    */
    @Test
     void testBoundaryValues() throws NumberFormatException, NullPointerException, EmptyException {
        assertTrue(leapYear.isLeapYear("4")); // Minimum valid leap year
        assertTrue(leapYear.isLeapYear("2096")); // Maximum valid leap year
        //assertFalse(leapYear.isLeapYear("2100")); // Valid year but not leap
        assertFalse(leapYear.isLeapYear("0")); // Zero is explicitly not a leap year
    }
    
    @Test
     void testOutOfRange() throws NumberFormatException, NullPointerException, EmptyException {
        assertFalse(leapYear.isLeapYear("-4")); // Negative numbers are out of range
        assertFalse(leapYear.isLeapYear("2104")); // Greater than 2100
    }
    /*
    @Test
     void testMixedInput() throws NumberFormatException, NullPointerException, EmptyException {
        leapYear.isLeapYear("2020a");
    }
    
    @Test
     void testSpecialCharacters() throws NumberFormatException, NullPointerException, EmptyException {
        leapYear.isLeapYear("2020!");
    }
    
    @Test
     void testWhitespaceInput() throws NumberFormatException, NullPointerException, EmptyException {
        leapYear.isLeapYear("  ");
    }
        */
}