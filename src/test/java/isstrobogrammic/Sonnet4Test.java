package isstrobogrammic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;




class Sonnet4Test {

    @Test
    @DisplayName("Should return true for null input")
    void shouldReturnTrueForNull() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should return true for null and empty string")
    void shouldReturnTrueForNullAndEmptyString(String input) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "8"})
    @DisplayName("Should return true for valid single digit strobogrammic numbers")
    void shouldReturnTrueForValidSingleDigitStrobogrammic(String number) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2", "3", "4", "5", "7"})
    @DisplayName("Should return false for invalid single digit numbers")
    void shouldReturnFalseForInvalidSingleDigit(String number) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"69", "96", "00", "11", "88"})
    @DisplayName("Should return true for valid two digit strobogrammic numbers")
    void shouldReturnTrueForValidTwoDigitStrobogrammic(String number) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12", "23", "34", "45", "56", "67", "78", "89", "90"})
    @DisplayName("Should return false for invalid two digit numbers")
    void shouldReturnFalseForInvalidTwoDigit(String number) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"101", "111", "181", "609", "619", "689", "808", "818", "888", "906", "916", "986"})
    @DisplayName("Should return true for valid three digit strobogrammic numbers")
    void shouldReturnTrueForValidThreeDigitStrobogrammic(String number) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"102", "120", "123", "456", "789"})
    @DisplayName("Should return false for invalid three digit numbers")
    void shouldReturnFalseForInvalidThreeDigit(String number) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1001", "1111", "1881", "6009", "6119", "6889", "8008", "8118", "8888", "9006", "9116", "9886"})
    @DisplayName("Should return true for valid four digit strobogrammic numbers")
    void shouldReturnTrueForValidFourDigitStrobogrammic(String number) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1002", "1200", "1234", "5678"})
    @DisplayName("Should return false for invalid four digit numbers")
    void shouldReturnFalseForInvalidFourDigit(String number) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(number));
    }

    @Test
    @DisplayName("Should return true for longer valid strobogrammic number")
    void shouldReturnTrueForLongerValidStrobogrammic() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("1000001"));
    }

    @Test
    @DisplayName("Should return false for longer invalid strobogrammic number")
    void shouldReturnFalseForLongerInvalidStrobogrammic() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("1000002"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"6", "9"})
    @DisplayName("Should return false for single digit 6 and 9")
    void shouldReturnFalseForSingleDigitSixAndNine(String number) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(number));
    }

    @Test
    @DisplayName("Should return false when left character is not in map")
    void shouldReturnFalseWhenLeftCharacterNotInMap() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("2"));
    }

    @Test
    @DisplayName("Should return false when right character is not in map")
    void shouldReturnFalseWhenRightCharacterNotInMap() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("3"));
    }

    @Test
    @DisplayName("Should return false when characters do not match rotated pair")
    void shouldReturnFalseWhenCharactersDoNotMatchRotatedPair() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("61"));
    }
/*
    @Test
    @DisplayName("Should handle complex valid strobogrammic with mixed digits")
    void shouldHandleComplexValidStrobogrammic() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("69896"));
    }
*/
    @Test
    @DisplayName("Should handle complex invalid strobogrammic with mixed digits")
    void shouldHandleComplexInvalidStrobogrammic() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("69816"));
    }

    @Test
    @DisplayName("Should return false for number containing invalid character in middle")
    void shouldReturnFalseForInvalidCharacterInMiddle() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("626"));
    }

    @Test
    @DisplayName("Should return true for palindromic strobogrammic number")
    void shouldReturnTrueForPalindromicStrobogrammic() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("88088"));
    }

    @Test
    @DisplayName("Should return false for palindromic non-strobogrammic number")
    void shouldReturnFalseForPalindromicNonStrobogrammic() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("22322"));
    }
}