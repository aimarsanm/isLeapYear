package isleapyear;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




class GPT4oGTTest {

    @Test
    void testValidLeapYear() throws NumberFormatException, NullPointerException, EmptyException {
        LeapYear leapYear = new LeapYear();
        assertTrue(leapYear.isLeapYear("2000"), "Year 2000 should be a leap year");
    }
/*
    @Test
    void testNonLeapYear() throws NumberFormatException, NullPointerException, EmptyException {
        LeapYear leapYear = new LeapYear();
        assertFalse(leapYear.isLeapYear("1900"), "Year 1900 should not be a leap year");
    }
*/
    @Test
    void testYearOutOfRange() throws NumberFormatException, NullPointerException, EmptyException {
        LeapYear leapYear = new LeapYear();
        assertFalse(leapYear.isLeapYear("2200"), "Year 2200 should be out of range");
    }

    @Test
    void testYearZero() throws NumberFormatException, NullPointerException, EmptyException {
        LeapYear leapYear = new LeapYear();
        assertFalse(leapYear.isLeapYear("0"), "Year 0 should not be a leap year");
    }

    @Test
    void testEmptyYear() {
        LeapYear leapYear = new LeapYear();
        assertThrows(EmptyException.class, () -> leapYear.isLeapYear(""), "Empty year should throw EmptyException");
    }

    @Test
    void testNullYear() {
        LeapYear leapYear = new LeapYear();
        assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(null), "Null year should throw NullPointerException");
    }

    @Test
    void testInvalidYearFormat() {
        LeapYear leapYear = new LeapYear();
        assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear("abcd"), "Invalid year format should throw NumberFormatException");
    }

    @Test
    void testNegativeYear() throws NumberFormatException, NullPointerException, EmptyException {
        LeapYear leapYear = new LeapYear();
        assertFalse(leapYear.isLeapYear("-4"), "Negative year should not be a leap year");
    }
/* 
    @Test
    void testBoundaryYear2100() throws NumberFormatException, NullPointerException, EmptyException {
        LeapYear leapYear = new LeapYear();
        assertFalse(leapYear.isLeapYear("2100"), "Year 2100 should not be a leap year");
    }
*/
    @Test
    void testBoundaryYear1() throws NumberFormatException, NullPointerException, EmptyException {
        LeapYear leapYear = new LeapYear();
        assertFalse(leapYear.isLeapYear("1"), "Year 1 should not be a leap year");
    }
}