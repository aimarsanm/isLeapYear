package isipv4valid;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;




class GPTo4minipreTest {

    private static IPAddressChecker checker;

    @BeforeAll
    static void init() {
        checker = new IPAddressChecker();
    }

    @AfterAll
    static void tearDown() {
        // no resources to clean up
    }

    // White-Box: isValidIPV4Part

    @ParameterizedTest
    @DisplayName("Valid IPv4 part strings should return true")
    @ValueSource(strings = {"0", "1", "10", "100", "255"})
    void testIsValidIPV4Part_Valid(String part) {
        assertTrue(checker.isValidIPV4Part(part),
                   () -> "Expected valid for part=\"" + part + "\"");
    }

    @ParameterizedTest
    @DisplayName("Invalid IPv4 part strings should return false")
    @ValueSource(strings = {
        "",        // empty
        "1234",    // length > 3
        "12a",     // non-digit
        "05",      // leading zero
        "001",     // leading zeros
        "256"      // > 255
    })
    void testIsValidIPV4Part_Invalid(String part) {
        assertFalse(checker.isValidIPV4Part(part),
                    () -> "Expected invalid for part=\"" + part + "\"");
    }

    @Test
    @DisplayName("Null input to isValidIPV4Part should throw NullPointerException")
    void testIsValidIPV4Part_NullInput() {
        assertThrows(NullPointerException.class,
                     () -> checker.isValidIPV4Part(null),
                     "Expected NPE for null part");
    }

    // Black-Box + boundary: isValidIPV4

    @Test
    @DisplayName("Null input to isValidIPV4 should return 0")
    void testIsValidIPV4_NullInput() {
        assertEquals(0, checker.isValidIPV4(null),
                     "Expected 0 for null IP string");
    }

    @Test
    @DisplayName("Empty input to isValidIPV4 should return 0")
    void testIsValidIPV4_EmptyInput() {
        assertEquals(0, checker.isValidIPV4(""),
                     "Expected 0 for empty IP string");
    }

    @ParameterizedTest(name = "IP \"{0}\" => {1}")
    @DisplayName("Valid and invalid full IPv4 strings")
    @CsvSource({
        // valid
        "0.0.0.0,1",
        "255.255.255.255,1",
        "192.168.1.1,1",
        // invalid dot counts
        "192.168.1,0",
        "192.168.1.1.1,0",
        // invalid parts
        "192.168.1.a,0",
        "256.100.100.100,0",
        "-1.0.0.0,0",
        // empty tokens / multiple dots
        "1...1,0",
        ".168.1.1,0",
        "192.168.1.,0"
        // whitespace
        //" 192.168.1.1,0"
    })
    void testIsValidIPV4_MixedCases(String ip, int expected) {
        assertEquals(expected, checker.isValidIPV4(ip),
                     () -> "IP=\"" + ip + "\" expected " + expected);
    }
}