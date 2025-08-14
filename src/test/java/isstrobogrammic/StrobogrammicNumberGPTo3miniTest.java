package isstrobogrammic;

import org.junit.jupiter.api.AfterAll; 
import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeAll; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.DisplayName; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.params.ParameterizedTest; 
import org.junit.jupiter.params.provider.CsvSource; 
import org.junit.jupiter.params.provider.NullAndEmptySource; 
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StrobogrammicNumber Tests (White Box and Black Box)") 
public class StrobogrammicNumberGPTo3miniTest {


@BeforeAll
static void setUpBeforeAll() {
    // Setup that runs before all tests (if needed)
}

@AfterAll
static void tearDownAfterAll() {
    // Cleanup that runs after all tests (if needed)
}

@BeforeEach
void setUp() {
    // Setup that runs before each test (if needed)
}

@AfterEach
void tearDown() {
    // Cleanup that runs after each test (if needed)
}

@ParameterizedTest
@NullAndEmptySource
@DisplayName("Test for null and empty string input should return true (boundary/black box)")
void testNullAndEmptyInput(String input) {
    try {
        boolean result = StrobogrammicNumber.isStrobogrammic(input);
        assertTrue(result, "Null or empty string should be considered strobogrammic.");
    } catch (Exception e) {
        fail("Unexpected exception for input [" + input + "]: " + e.getMessage());
    }
}

@ParameterizedTest
@CsvSource({
    // Valid single-digit numbers that have a proper symmetric mapping
    "'0', true",
    "'1', true",
    "'8', true",
    // Valid two-digit pairs
    "'69', true",
    "'96', true",
    "'11', true",
    "'88', true",
    "'00', true",
    // Valid numbers with even/odd length
    "'1001', true",
    "'818', true",
    "'101', true",
    "'609', true",
    "'906', true"
})
@DisplayName("Test valid strobogrammic numbers (white box condition/decision and black box partitioning)")
void testValidStrobogrammicNumbers(String input, boolean expected) {
    try {
        boolean result = StrobogrammicNumber.isStrobogrammic(input);
        assertEquals(expected, result, "Expected strobogrammic status for input: " + input);
    } catch (Exception e) {
        fail("Unexpected exception for input [" + input + "]: " + e.getMessage());
    }
}

@ParameterizedTest
@CsvSource({
    // Invalid single-digit numbers where mapping does not match
    "'6', false",
    "'9', false",
    // Invalid due to left/right mismatch in two-digit number
    "'16', false",
    "'60', false",
    "'91', false",
    // Invalid because of an unmapped character
    "'2', false",
    "'962', false",
    "'A', false"
})
@DisplayName("Test invalid strobogrammic numbers (covering failure branches in white box and black box partitioning)")
void testInvalidStrobogrammicNumbers(String input, boolean expected) {
    try {
        boolean result = StrobogrammicNumber.isStrobogrammic(input);
        assertEquals(expected, result, "Expected non strobogrammic result for input: " + input);
    } catch (Exception e) {
        fail("Unexpected exception for input [" + input + "]: " + e.getMessage());
    }
}

@Test
@DisplayName("Test a typical valid multi-digit strobogrammic number (white box loop execution)")
void testTypicalValidCase() {
    String input = "69";
    try {
        boolean result = StrobogrammicNumber.isStrobogrammic(input);
        assertTrue(result, "Input " + input + " should be strobogrammic.");
    } catch (Exception e) {
        fail("Unexpected exception for input [" + input + "]: " + e.getMessage());
    }
}

@Test
@DisplayName("Test a typical invalid multi-digit strobogrammic number (white box inner condition failure)")
void testTypicalInvalidCase() {
    String input = "16";
    try {
        boolean result = StrobogrammicNumber.isStrobogrammic(input);
        assertFalse(result, "Input " + input + " is not strobogrammic.");
    } catch (Exception e) {
        fail("Unexpected exception for input [" + input + "]: " + e.getMessage());
    }
}
}