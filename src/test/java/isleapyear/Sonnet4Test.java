package isleapyear;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;





@DisplayName("LeapYear Tests")
class Sonnet4Test {

	private final LeapYear leapYear = new LeapYear();

	@ParameterizedTest
	@ValueSource(strings = {"4", "8", "12", "16", "20", "24", "28", "32", "36", "40", "44", "48", "52", "56", "60", "64", "68", "72", "76", "80", "84", "88", "92", "96", "100", "104", "108", "112", "116", "120", "1996", "2000", "2004", "2008", "2012", "2016", "2020", "2024", "2028", "2032", "2036", "2040", "2044", "2048", "2052", "2056", "2060", "2064", "2068", "2072", "2076", "2080", "2084", "2088", "2092", "2096", "2100"})
	@DisplayName("Should return true for valid leap years")
	void shouldReturnTrueForValidLeapYears(String year) {
		try {
			assertTrue(leapYear.isLeapYear(year));
		} catch (Exception e) {
			fail("Should not throw exception for valid leap year: " + year);
		}
	}

	@ParameterizedTest
	@ValueSource(strings = {"1", "2", "3", "5", "6", "7", "9", "10", "11", "13", "14", "15", "17", "18", "19", "21", "22", "23", "25", "26", "27", "29", "30", "31", "33", "34", "35", "37", "38", "39", "41", "42", "43", "45", "46", "47", "49", "50", "51", "53", "54", "55", "57", "58", "59", "61", "62", "63", "65", "66", "67", "69", "70", "71", "73", "74", "75", "77", "78", "79", "81", "82", "83", "85", "86", "87", "89", "90", "91", "93", "94", "95", "97", "98", "99", "101", "102", "103", "105", "106", "107", "109", "110", "111", "113", "114", "115", "117", "118", "119", "121", "122", "123", "1997", "1998", "1999", "2001", "2002", "2003", "2005", "2006", "2007", "2009", "2010", "2011", "2013", "2014", "2015", "2017", "2018", "2019", "2021", "2022", "2023", "2025", "2026", "2027", "2029", "2030", "2031", "2033", "2034", "2035", "2037", "2038", "2039", "2041", "2042", "2043", "2045", "2046", "2047", "2049", "2050", "2051", "2053", "2054", "2055", "2057", "2058", "2059", "2061", "2062", "2063", "2065", "2066", "2067", "2069", "2070", "2071", "2073", "2074", "2075", "2077", "2078", "2079", "2081", "2082", "2083", "2085", "2086", "2087", "2089", "2090", "2091", "2093", "2094", "2095", "2097", "2098", "2099"})
	@DisplayName("Should return false for non-leap years within range")
	void shouldReturnFalseForNonLeapYearsWithinRange(String year) {
		try {
			assertFalse(leapYear.isLeapYear(year));
		} catch (Exception e) {
			fail("Should not throw exception for valid non-leap year: " + year);
		}
	}

	@ParameterizedTest
	@ValueSource(strings = {"0", "-4", "-8", "-12", "2101", "2104", "2108", "3000", "10000"})
	@DisplayName("Should return false for years outside valid range")
	void shouldReturnFalseForYearsOutsideRange(String year) {
		try {
			assertFalse(leapYear.isLeapYear(year));
		} catch (Exception e) {
			fail("Should not throw exception for out of range year: " + year);
		}
	}

	@Test
	@DisplayName("Should throw NullPointerException when input is null")
	void shouldThrowNullPointerExceptionWhenInputIsNull() {
		assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(null));
	}

	@Test
	@DisplayName("Should throw EmptyException when input is empty string")
	void shouldThrowEmptyExceptionWhenInputIsEmpty() {
		assertThrows(EmptyException.class, () -> leapYear.isLeapYear(""));
	}

	@ParameterizedTest
	@ValueSource(strings = {"abc", "12.5", "12a", "a12", "1 2", " 12", "12 ", "1,2", "1.0", "twelve", "@@", "12@", "@12"})
	@DisplayName("Should throw NumberFormatException for invalid number formats")
	void shouldThrowNumberFormatExceptionForInvalidFormats(String year) {
		assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear(year));
	}

	@ParameterizedTest
	@CsvSource({
		"1, false, true, true, false",
		"2, false, true, false, false", 
		"3, false, true, false, false",
		"4, true, true, true, true",
		"2099, true, true, false, false",
		"2100, true, true, true, true",
		"2101, false, false, true, false",
		"0, false, false, true, false",
		"-4, false, false, true, false"
	})
	@DisplayName("White Box: Condition/Decision Coverage for complex boolean expression")
	void shouldCoverAllConditionDecisionCombinations(String year, boolean expectedGreaterThan0, boolean expectedLessEqual2100, boolean expectedDivisibleBy4, boolean expectedResult) {
		try {
			boolean result = leapYear.isLeapYear(year);
			assertEquals(expectedResult, result);
		} catch (Exception e) {
			fail("Should not throw exception for year: " + year);
		}
	}

	@Test
	@DisplayName("Boundary: Should return true for year 1 (not leap year)")
	void shouldReturnFalseForYear1() {
		try {
			assertFalse(leapYear.isLeapYear("1"));
		} catch (Exception e) {
			fail("Should not throw exception for year 1");
		}
	}

	@Test
	@DisplayName("Boundary: Should return true for year 2100 (leap year)")
	void shouldReturnTrueForYear2100() {
		try {
			assertTrue(leapYear.isLeapYear("2100"));
		} catch (Exception e) {
			fail("Should not throw exception for year 2100");
		}
	}

	@Test
	@DisplayName("Should return false for year 0 (explicitly not leap year)")
	void shouldReturnFalseForYear0() {
		try {
			assertFalse(leapYear.isLeapYear("0"));
		} catch (Exception e) {
			fail("Should not throw exception for year 0");
		}
	}

	@Test
	@DisplayName("Should return false for negative leap year multiples")
	void shouldReturnFalseForNegativeLeapYearMultiples() {
		try {
			assertFalse(leapYear.isLeapYear("-4"));
		} catch (Exception e) {
			fail("Should not throw exception for negative year");
		}
	}

	@Test
	@DisplayName("Should return false for year above upper bound")
	void shouldReturnFalseForYearAboveUpperBound() {
		try {
			assertFalse(leapYear.isLeapYear("2101"));
		} catch (Exception e) {
			fail("Should not throw exception for year above bound");
		}
	}

	@Test
	@DisplayName("Edge case: Leading zeros should work correctly")
	void shouldHandleLeadingZeros() {
		try {
			assertTrue(leapYear.isLeapYear("0004"));
		} catch (Exception e) {
			fail("Should not throw exception for year with leading zeros");
		}
	}

	@Test
	@DisplayName("Edge case: Maximum integer value should return false")
	void shouldReturnFalseForMaxIntValue() {
		try {
			assertFalse(leapYear.isLeapYear("2147483647"));
		} catch (Exception e) {
			fail("Should not throw exception for max int value");
		}
	}

	@Test
	@DisplayName("Edge case: Minimum integer value should return false")
	void shouldReturnFalseForMinIntValue() {
		try {
			assertFalse(leapYear.isLeapYear("-2147483648"));
		} catch (Exception e) {
			fail("Should not throw exception for min int value");
		}
	}
}