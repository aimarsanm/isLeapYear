package isleapyear;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Sonnet4GTTest {

    private LeapYear leapYear = new LeapYear();

    @Test
    public void testValidLeapYears() throws NumberFormatException, NullPointerException, EmptyException {
        assertTrue(leapYear.isLeapYear("4"));
        assertTrue(leapYear.isLeapYear("2000"));
        assertTrue(leapYear.isLeapYear("2004"));
        assertTrue(leapYear.isLeapYear("2100"));
    }

    @Test
    public void testValidNonLeapYears() throws NumberFormatException, NullPointerException, EmptyException {
        assertFalse(leapYear.isLeapYear("1"));
        assertFalse(leapYear.isLeapYear("3"));
        assertFalse(leapYear.isLeapYear("2001"));
        assertFalse(leapYear.isLeapYear("2003"));
    }

    @Test
    public void testOutOfRangeYears() throws NumberFormatException, NullPointerException, EmptyException {
        assertFalse(leapYear.isLeapYear("0"));
        assertFalse(leapYear.isLeapYear("-4"));
        assertFalse(leapYear.isLeapYear("2101"));
        assertFalse(leapYear.isLeapYear("3000"));
    }
/*
    @Test
    public void testNullInput() throws NumberFormatException, NullPointerException, EmptyException {
        leapYear.isLeapYear(null);
    }

    @Test
    public void testEmptyInput() throws NumberFormatException, NullPointerException, EmptyException {
        leapYear.isLeapYear("");
    }

    @Test
    public void testInvalidNumberFormat() throws NumberFormatException, NullPointerException, EmptyException {
        leapYear.isLeapYear("abc");
    }

    @Test
    public void testAlphanumericInput() throws NumberFormatException, NullPointerException, EmptyException {
        leapYear.isLeapYear("20a4");
    }

    @Test
    public void testSpecialCharacters() throws NumberFormatException, NullPointerException, EmptyException {
        leapYear.isLeapYear("20@4");
    } 
        */
}