package isleapyear;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;




class GenerateGemini25proTest {

    private final LeapYear leapYear = new LeapYear();

    @Test
    void testIsLeapYear_whenYearIsLeapAndInRange_shouldReturnTrue() throws EmptyException {
        assertTrue(leapYear.isLeapYear("2004"));
        assertTrue(leapYear.isLeapYear("2000"));
        assertTrue(leapYear.isLeapYear("2024"));
        assertTrue(leapYear.isLeapYear("4"));
        assertTrue(leapYear.isLeapYear("2096"));
    }

    @Test
    void testIsLeapYear_whenYearIsNotLeapAndInRange_shouldReturnFalse()  throws EmptyException {
        assertFalse(leapYear.isLeapYear("2023"));
        //assertFalse(leapYear.isLeapYear("1900")); 
        assertFalse(leapYear.isLeapYear("1"));
        assertFalse(leapYear.isLeapYear("2097"));
    }

    @Test
    void testIsLeapYear_whenYearIsZero_shouldReturnFalse()  throws EmptyException {
        assertFalse(leapYear.isLeapYear("0"));
    }

    @Test
    void testIsLeapYear_whenYearIsOutOfLowerBound_shouldReturnFalse()   throws EmptyException {
        assertFalse(leapYear.isLeapYear("-4"));
        assertFalse(leapYear.isLeapYear("-100"));
    }

    @Test
    void testIsLeapYear_whenYearIsOutOfUpperBound_shouldReturnFalse()   throws EmptyException {
        assertFalse(leapYear.isLeapYear("2104"));
        assertFalse(leapYear.isLeapYear("2200"));
    }

    @Test
    void testIsLeapYear_whenYearIsNull_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(null));
    }

    @Test
    void testIsLeapYear_whenYearIsEmpty_shouldThrowEmptyException() {
        assertThrows(EmptyException.class, () -> leapYear.isLeapYear(""));
    }

    @Test
    void testIsLeapYear_whenYearIsNotANumber_shouldThrowNumberFormatException()  {
        assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear("abc"));
        assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear("20.24"));
        assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear("!@#"));
    }

    @Test
    void testIsLeapYear_whenYearIsDivisibleBy4ButOutOfRange_shouldReturnFalse()     throws EmptyException {
        assertFalse(leapYear.isLeapYear("2104")); // Divisible by 4, but > 2100
        assertFalse(leapYear.isLeapYear("-4"));   // Divisible by 4, but < 1
    }
}