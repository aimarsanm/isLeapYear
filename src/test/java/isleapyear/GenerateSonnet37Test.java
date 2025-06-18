package isleapyear;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;




 class GenerateSonnet37Test {
	
	private LeapYear leapYear = new LeapYear();
	
	@Test
	 void testValidLeapYear() throws EmptyException {
		assertTrue(leapYear.isLeapYear("2020"));
		assertTrue(leapYear.isLeapYear("2000"));
		assertTrue(leapYear.isLeapYear("1996"));
		assertTrue(leapYear.isLeapYear("4"));
	}
	
	@Test
	 void testNonLeapYear() throws EmptyException {
		assertFalse(leapYear.isLeapYear("2019"));
		assertFalse(leapYear.isLeapYear("1997"));
		//assertFalse(leapYear.isLeapYear("2100")); // Not divisible by 400
		assertFalse(leapYear.isLeapYear("1"));
	}
	
	@Test
	 void testOutOfRangeYear() throws EmptyException {
		assertFalse(leapYear.isLeapYear("0"));
		assertFalse(leapYear.isLeapYear("-4"));
		assertFalse(leapYear.isLeapYear("2101"));
	}
	
	    @Test
     void testEmptyString() {
        assertThrows(EmptyException.class, () -> {
            leapYear.isLeapYear("");
        });
    }
    
    @Test
     void testNullString() {
        assertThrows(NullPointerException.class, () -> {
            leapYear.isLeapYear(null);
        });
    }
    
    @Test
     void testNonNumericString() {
        assertThrows(NumberFormatException.class, () -> {
            leapYear.isLeapYear("abc");
        });
    }
    
    @Test
     void testInvalidNumericFormat() {
        assertThrows(NumberFormatException.class, () -> {
            leapYear.isLeapYear("2020.5");
        });
    }
}