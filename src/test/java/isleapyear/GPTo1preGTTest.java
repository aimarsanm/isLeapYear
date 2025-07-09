package isleapyear;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;




public class GPTo1preGTTest {

    @Test
    public void testValidLeapYear() throws NumberFormatException, NullPointerException, EmptyException {
        LeapYear leapYear = new LeapYear();
        assertTrue(leapYear.isLeapYear("4"));
        assertTrue(leapYear.isLeapYear("8"));
        assertTrue(leapYear.isLeapYear("2100"));
    }

    @Test
    public void testValidNonLeapYear() throws NumberFormatException, NullPointerException, EmptyException {
        LeapYear leapYear = new LeapYear();
        assertFalse(leapYear.isLeapYear("1"));
        assertFalse(leapYear.isLeapYear("3"));
        assertFalse(leapYear.isLeapYear("0"));
    }
/* 
    @Test
    public void testNullYear() throws NumberFormatException, NullPointerException, EmptyException {
        LeapYear leapYear = new LeapYear();
        leapYear.isLeapYear(null);
    }

    @Test
    public void testEmptyYear() throws NumberFormatException, NullPointerException, EmptyException {
        LeapYear leapYear = new LeapYear();
        leapYear.isLeapYear("");
    }

    @Test
    public void testInvalidFormat() throws NumberFormatException, NullPointerException, EmptyException {
        LeapYear leapYear = new LeapYear();
        leapYear.isLeapYear("ABC");
    }
*/
    @Test
    public void testBoundaryYears() throws NumberFormatException, NullPointerException, EmptyException {
        LeapYear leapYear = new LeapYear();
        assertFalse(leapYear.isLeapYear("2110"));
        assertTrue(leapYear.isLeapYear("2100"));
    }
}