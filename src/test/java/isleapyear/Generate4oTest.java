package isleapyear;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;




class Generate4oTest {

    @Test
    void testValidLeapYear() throws EmptyException {
        LeapYear leapYear = new LeapYear();
        assertTrue(leapYear.isLeapYear("2000"));
    }

    @Test
    void testValidNonLeapYear() throws EmptyException{
        LeapYear leapYear = new LeapYear();
        assertFalse(leapYear.isLeapYear("2001"));
    }

    @Test
    void testYearZero() throws EmptyException{
        LeapYear leapYear = new LeapYear();
        assertFalse(leapYear.isLeapYear("0"));
    }

    @Test
    void testYearOutOfRange() throws EmptyException{
        LeapYear leapYear = new LeapYear();
        assertFalse(leapYear.isLeapYear("2200"));
    }

    @Test
    void testEmptyYear() {
        LeapYear leapYear = new LeapYear();
        assertThrows(EmptyException.class, () -> leapYear.isLeapYear(""));
    }

    @Test
    void testNullYear() {
        LeapYear leapYear = new LeapYear();
        assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(null));
    }

    @Test
    void testInvalidNumberFormat() {
        LeapYear leapYear = new LeapYear();
        assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear("abcd"));
    }
/* 
    @Test
    void testBoundaryYear2100()throws EmptyException {
        LeapYear leapYear = new LeapYear();
        assertFalse(leapYear.isLeapYear("2100")); // 2100 is not a leap year
    }
*/
    @Test
    void testBoundaryYear1() throws EmptyException{
        LeapYear leapYear = new LeapYear();
        assertFalse(leapYear.isLeapYear("1")); // 1 is not a leap year
    }

    @Test
    void testNegativeYear()throws EmptyException {
        LeapYear leapYear = new LeapYear();
        assertFalse(leapYear.isLeapYear("-4")); // Negative years are not valid
    }
}