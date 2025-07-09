package isleapyear;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;




class GPTo4miniGTTest {
    private final LeapYear leapYear = new LeapYear();

    @Test
    void testNullInputThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(null));
    }

    @Test
    void testEmptyInputThrowsEmptyException() {
        assertThrows(EmptyException.class, () -> leapYear.isLeapYear(""));
    }

    @Test
    void testNonNumericInputThrowsNumberFormatException() {
        assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear("abcd"));
    }

    @Test
    void testZeroYearReturnsFalse() throws Exception {
        assertFalse(leapYear.isLeapYear("0"));
    }

    @Test
    void testNegativeYearReturnsFalse() throws Exception {
        assertFalse(leapYear.isLeapYear("-4"));
    }

    @Test
    void testYearAbove2100ReturnsFalse() throws Exception {
        assertFalse(leapYear.isLeapYear("2101"));
    }

    @Test
    void testNonLeapYearsReturnFalse() throws Exception {
        assertFalse(leapYear.isLeapYear("1"));
        assertFalse(leapYear.isLeapYear("2"));
        assertFalse(leapYear.isLeapYear("3"));
        assertFalse(leapYear.isLeapYear("5"));
    }

    @Test
    void testLeapYearsReturnTrue() throws Exception {
        assertTrue(leapYear.isLeapYear("4"));
        assertTrue(leapYear.isLeapYear("100"));
        assertTrue(leapYear.isLeapYear("1900"));
        assertTrue(leapYear.isLeapYear("2000"));
        assertTrue(leapYear.isLeapYear("2100"));
    }
}