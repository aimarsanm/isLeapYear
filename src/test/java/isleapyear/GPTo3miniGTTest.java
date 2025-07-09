package isleapyear;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class GPTo3miniGTTest {

    private LeapYear leapYear = new LeapYear();

    @Test
    public void testValidLeapYears() throws Exception {
        // Test a few valid leap years within the range.
        assertTrue(leapYear.isLeapYear("4"));
        assertTrue(leapYear.isLeapYear("8"));
        assertTrue(leapYear.isLeapYear("2000"));
    }

    @Test
    public void testNonLeapYears() throws Exception {
        // Test numbers that are not leap years or out of valid range.
        assertFalse(leapYear.isLeapYear("1"));
        assertFalse(leapYear.isLeapYear("5"));
        // Year 0 is not allowed since it must be > 0.
        assertFalse(leapYear.isLeapYear("0"));
        // Negative year.
        assertFalse(leapYear.isLeapYear("-4"));
        // Year above 2100.
        assertFalse(leapYear.isLeapYear("2101"));
    }
/* 
    @Test
    public void testNullYearThrowsException() throws Exception {
        // Passing null should throw NullPointerException.
        leapYear.isLeapYear(null);
    }

    @Test
    public void testEmptyYearThrowsEmptyException() throws Exception {
        // Passing an empty string should throw EmptyException.
        leapYear.isLeapYear("");
    }

    @Test
    public void testInvalidNumberThrowsNumberFormatException() throws Exception {
        // Passing a non-numeric string should throw NumberFormatException.
        leapYear.isLeapYear("abc");
    }*/
}