package isleapyear;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

 class LeapYearGenerateTest {

    @Test
     void testLeapYearValidLeap() throws Exception {
        assertTrue(LeapYear.isLeapYear("2020"));
        assertTrue(LeapYear.isLeapYear("2000"));
        assertTrue(LeapYear.isLeapYear("4"));
        assertTrue(LeapYear.isLeapYear("2100"));
    }

    @Test
     void testLeapYearValidNonLeap() throws Exception {
        assertFalse(LeapYear.isLeapYear("2019"));
        assertFalse(LeapYear.isLeapYear("2101"));
        assertFalse(LeapYear.isLeapYear("0"));
        assertFalse(LeapYear.isLeapYear("1"));
    }

    @Test
    void testLeapYearNullInput() {
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
            LeapYear.isLeapYear(null);
        });
       
    }

    @Test
    void testLeapYearEmptyInput() {
        org.junit.jupiter.api.Assertions.assertThrows(EmptyException.class, () -> {
            LeapYear.isLeapYear("");
        });
    }

    @Test
    void testLeapYearNonNumericInput() {
        org.junit.jupiter.api.Assertions.assertThrows(NumberFormatException.class, () -> {
            LeapYear.isLeapYear("abc");
        });
    }

    @Test
    void testLeapYearDecimalInput() {
        org.junit.jupiter.api.Assertions.assertThrows(NumberFormatException.class, () -> {
            LeapYear.isLeapYear("2020.0");
        });
    }

   
}