package isipv4valid;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;




@DisplayName("IP Address Checker Tests")
class Sonnet37Test {

    private final IPAddressChecker checker = new IPAddressChecker();

    @Nested
    @DisplayName("isValidIPV4Part method tests")
    class IsValidIPV4PartTests {

        @ParameterizedTest(name = "Valid IP part: {0}")
        @DisplayName("Should return true for valid IP parts")
        @ValueSource(strings = {"0", "1", "10", "100", "199", "255"})
        void shouldReturnTrueForValidIPParts(String input) {
            assertTrue(checker.isValidIPV4Part(input));
        }

        @ParameterizedTest(name = "Invalid IP part: {0}")
        @DisplayName("Should return false for invalid IP parts")
        @ValueSource(strings = {"256", "300", "999", "1000", "-1", "00", "01", "001", "a", "1a", "1.1"})
        void shouldReturnFalseForInvalidIPParts(String input) {
            assertFalse(checker.isValidIPV4Part(input));
        }

        @Test
        @DisplayName("Should handle parts with length greater than 3")
        void shouldHandlePartsWithLengthGreaterThanThree() {
            assertFalse(checker.isValidIPV4Part("1234"));
        }

        @Test
        @DisplayName("Should handle non-digit characters")
        void shouldHandleNonDigitCharacters() {
            assertFalse(checker.isValidIPV4Part("12a"));
        }

        @Test
        @DisplayName("Should handle leading zeros")
        void shouldHandleLeadingZeros() {
            assertFalse(checker.isValidIPV4Part("01"));
            assertFalse(checker.isValidIPV4Part("001"));
            assertFalse(checker.isValidIPV4Part("05"));
        }

        @Test
        @DisplayName("Should accept zero as valid")
        void shouldAcceptZeroAsValid() {
            assertTrue(checker.isValidIPV4Part("0"));
        }

        @ParameterizedTest(name = "Boundary value: {0}")
        @DisplayName("Should handle boundary values correctly")
        @CsvSource({
            "0, true",
            "255, true",
            "256, false"
        })
        void shouldHandleBoundaryValuesCorrectly(String input, boolean expected) {
            assertEquals(expected, checker.isValidIPV4Part(input));
        }
    }

    @Nested
    @DisplayName("isValidIPV4 method tests")
    class IsValidIPV4Tests {

        @ParameterizedTest(name = "Valid IP: {0}")
        @DisplayName("Should return 1 for valid IP addresses")
        @ValueSource(strings = {
            "0.0.0.0", 
            "1.1.1.1", 
            "192.168.0.1", 
            "255.255.255.255", 
            "127.0.0.1",
            "10.0.0.1",
            "172.16.0.1"
        })
        void shouldReturnOneForValidIPAddresses(String input) {
            assertEquals(1, checker.isValidIPV4(input));
        }

        @ParameterizedTest(name = "Invalid IP: {0}")
        @DisplayName("Should return 0 for invalid IP addresses")
        @ValueSource(strings = {
            "256.0.0.0",
            "255.256.255.255",
            "192.168.0",
            "192.168.0.1.1",
            "a.b.c.d",
            "1.1.1.1a",
            "01.1.1.1",
            "1.01.1.1",
            ".1.1.1",
            "1..1.1",
            "1.1.1.",
            "1.1.1.01"
        })
        void shouldReturnZeroForInvalidIPAddresses(String input) {
            assertEquals(0, checker.isValidIPV4(input));
        }

        @ParameterizedTest
        @DisplayName("Should return 0 for null input")
        @NullSource
        void shouldReturnZeroForNullInput(String input) {
            assertEquals(0, checker.isValidIPV4(input));
        }

        @Test
        @DisplayName("Should return 0 for empty string")
        void shouldReturnZeroForEmptyString() {
            assertEquals(0, checker.isValidIPV4(""));
        }

        @Test
        @DisplayName("Should return 0 when IP has more than 3 dots")
        void shouldReturnZeroWhenIPHasMoreThanThreeDots() {
            assertEquals(0, checker.isValidIPV4("192.168.0.1.1"));
        }

        @Test
        @DisplayName("Should return 0 when IP has less than 3 dots")
        void shouldReturnZeroWhenIPHasLessThanThreeDots() {
            assertEquals(0, checker.isValidIPV4("192.168.0"));
        }

        @ParameterizedTest(name = "IP with consecutive dots: {0}")
        @DisplayName("Should return 0 when IP has consecutive dots")
        @ValueSource(strings = {"192..168.0.1", "192.168..1", "192.168.0.."})
        void shouldReturnZeroWhenIPHasConsecutiveDots(String input) {
            assertEquals(0, checker.isValidIPV4(input));
        }

        @Test
        @DisplayName("Should return 0 when IP starts with a dot")
        void shouldReturnZeroWhenIPStartsWithDot() {
            assertEquals(0, checker.isValidIPV4(".192.168.0.1"));
        }

        @Test
        @DisplayName("Should return 0 when IP ends with a dot")
        void shouldReturnZeroWhenIPEndsWithDot() {
            assertEquals(0, checker.isValidIPV4("192.168.0."));
        }

        @ParameterizedTest(name = "Special case: {0}")
        @DisplayName("Should handle special cases correctly")
        @MethodSource("specialCasesProvider")
        void shouldHandleSpecialCasesCorrectly(String input, int expected) {
            assertEquals(expected, checker.isValidIPV4(input));
        }

        static Stream<Arguments> specialCasesProvider() {
            return Stream.of(
                Arguments.of("0.0.0.0", 1),
                Arguments.of("255.255.255.255", 1),
                Arguments.of("1...1", 0),
                Arguments.of("1.1.1.1.", 0),
                Arguments.of(".1.1.1.1", 0)
            );
        }

        @ParameterizedTest(name = "Boundary IP: {0}")
        @DisplayName("Should handle boundary value IPs correctly")
        @CsvSource({
            "0.0.0.0, 1",
            "255.255.255.255, 1",
            "255.255.255.0, 1",
            "255.255.0.255, 1",
            "255.0.255.255, 1",
            "0.255.255.255, 1",
            "256.0.0.0, 0",
            "0.256.0.0, 0",
            "0.0.256.0, 0",
            "0.0.0.256, 0"
        })
        void shouldHandleBoundaryValueIPsCorrectly(String input, int expected) {
            assertEquals(expected, checker.isValidIPV4(input));
        }
    }

    @Nested
    @DisplayName("Edge cases and combined conditions")
    class EdgeCasesAndCombinedConditions {

        @ParameterizedTest(name = "IP with leading zeros in segments: {0}")
        @DisplayName("Should handle leading zeros in segments correctly")
        @CsvSource({
            "0.0.0.0, 1",
            "01.0.0.0, 0",
            "0.01.0.0, 0",
            "0.0.01.0, 0",
            "0.0.0.01, 0"
        })
        void shouldHandleLeadingZerosInSegments(String input, int expected) {
            assertEquals(expected, checker.isValidIPV4(input));
        }

        @ParameterizedTest(name = "IP with special characters: {0}")
        @DisplayName("Should handle special characters correctly")
        @ValueSource(strings = {
            "192.168.0.1a",
            "a.168.0.1",
            "192.a.0.1",
            "192.168.a.1",
            "192.168.0.a"
        })
        void shouldHandleSpecialCharacters(String input) {
            assertEquals(0, checker.isValidIPV4(input));
        }

        @ParameterizedTest(name = "Unusual dot patterns: {0}")
        @DisplayName("Should handle unusual dot patterns correctly")
        @ValueSource(strings = {
            "192...1",
            "...192",
            "192...",
            "."
        })
        void shouldHandleUnusualDotPatterns(String input) {
            assertEquals(0, checker.isValidIPV4(input));
        }

        @ParameterizedTest(name = "IP segment with length > 3: {0}")
        @DisplayName("Should reject IP segments with length greater than 3")
        @ValueSource(strings = {
            "1234.0.0.0",
            "0.1234.0.0",
            "0.0.1234.0",
            "0.0.0.1234"
        })
        void shouldRejectSegmentsWithLengthGreaterThanThree(String input) {
            assertEquals(0, checker.isValidIPV4(input));
        }
    }
}