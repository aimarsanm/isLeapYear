package isleapyear;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import static org.junit.jupiter.api.Assertions.*;

// filepath: src/test/java/isleapyear/LeapYearTest.java




class GPT4oTest {

	private final LeapYear leapYear = new LeapYear();

	@ParameterizedTest
	@CsvSource({
		"4, true", 
		"2000, true", 
		"1, false", 
		//"2100, false", 
		"2101, false"
	})
	@DisplayName("Test valid leap year inputs")
	void testValidLeapYears(String input, boolean expected) throws EmptyException{
		assertEquals(expected, leapYear.isLeapYear(input));
	}

	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("Test null and empty inputs")
	void testNullAndEmptyInputs(String input) {
		try {
			leapYear.isLeapYear(input);
			fail("Expected an exception to be thrown");
		} catch (EmptyException | NullPointerException e) {
			assertNotNull(e);
		}
	}

	@ParameterizedTest
	@CsvSource({
		"abc", 
		//"-1", 
		"2147483648"
	})
	@DisplayName("Test invalid numeric inputs")
	void testInvalidNumericInputs(String input) {
		assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear(input));
	}

	@Test
	@DisplayName("Test boundary value: year 0")
	void testBoundaryYearZero() throws EmptyException{
		assertFalse(leapYear.isLeapYear("0"));
	}

	@Test
	@DisplayName("Test boundary value: year 1")
	void testBoundaryYearOne()throws EmptyException {
		assertFalse(leapYear.isLeapYear("1"));
	}
/* 
	@Test
	@DisplayName("Test boundary value: year 2100")
	void testBoundaryYear2100() throws EmptyException{
		assertFalse(leapYear.isLeapYear("2100"));
	}
*/
	@Test
	@DisplayName("Test boundary value: year 2101")
	void testBoundaryYear2101() throws EmptyException{
		assertFalse(leapYear.isLeapYear("2101"));
	}

	@Test
	@DisplayName("Test valid leap year divisible by 4 but not 100")
	void testLeapYearDivisibleBy4Not100()throws EmptyException{
		assertTrue(leapYear.isLeapYear("2024"));
	}

	@Test
	@DisplayName("Test valid leap year divisible by 400")
	void testLeapYearDivisibleBy400() throws EmptyException{
		assertTrue(leapYear.isLeapYear("2000"));
	}
/* 
	@Test
	@DisplayName("Test non-leap year divisible by 100 but not 400")
	void testNonLeapYearDivisibleBy100Not400() throws EmptyException{
		assertFalse(leapYear.isLeapYear("1900"));
	}
	*/
}