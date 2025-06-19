package isleapyear;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class GenerateGPTo4miniPreTest {
    private final LeapYear leapYear = new LeapYear();


    @Test
     void testNullInput() {
        assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(null));
    }
    @Test
     void testEmptyInput() {
        assertThrows(EmptyException.class, () -> leapYear.isLeapYear(""));
    }
   
    @Test
     void testNonNumericInput() {
        assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear("abc"));
    }
    
    @Test
     void testWhitespaceInput() {
        assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear(" 16"));
    }
    

    @Test
     void testYearZero() throws EmptyException {
        assertFalse(leapYear.isLeapYear("0"));
    }

    @Test
     void testNonLeapYears() throws EmptyException {
        assertFalse(leapYear.isLeapYear("1"));
        assertFalse(leapYear.isLeapYear("3"));
        assertFalse(leapYear.isLeapYear("2099"));
        assertFalse(leapYear.isLeapYear("2101"));
    }

    @Test
     void testLeapYears() throws EmptyException {
        assertTrue(leapYear.isLeapYear("4"));
        assertTrue(leapYear.isLeapYear("100"));
        assertTrue(leapYear.isLeapYear("400"));
        assertTrue(leapYear.isLeapYear("2100"));
        assertTrue(leapYear.isLeapYear("0032"));
    }

    @Test
     void testNegativeYears() throws EmptyException {
        assertFalse(leapYear.isLeapYear("-4"));
    }
}