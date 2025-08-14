package isstrobogrammic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.EmptySource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StrobogrammicNumber Tests")
class StrobogrammicNumberSonnet4Test {

    @Test
    @DisplayName("Should return true when input is null")
    void shouldReturnTrueWhenInputIsNull() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(null));
    }

    @Test
    @DisplayName("Should return true when input is empty string")
    void shouldReturnTrueWhenInputIsEmptyString() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "8"})
    @DisplayName("Should return true for valid single digit strobogrammic numbers")
    void shouldReturnTrueForValidSingleDigitStrobogrammicNumbers(String number) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2", "3", "4", "5", "7"})
    @DisplayName("Should return false for invalid single digit numbers")
    void shouldReturnFalseForInvalidSingleDigitNumbers(String number) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(number));
    }

    @Test
    @DisplayName("Should return false for single digit 6")
    void shouldReturnFalseForSingleDigit6() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("6"));
    }

    @Test
    @DisplayName("Should return false for single digit 9")
    void shouldReturnFalseForSingleDigit9() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("9"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"00", "11", "88", "69", "96"})
    @DisplayName("Should return true for valid two digit strobogrammic numbers")
    void shouldReturnTrueForValidTwoDigitStrobogrammicNumbers(String number) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12", "23", "34", "45", "56", "67", "78", "89", "90"})
    @DisplayName("Should return false for invalid two digit numbers")
    void shouldReturnFalseForInvalidTwoDigitNumbers(String number) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(number));
    }

    @Test
    @DisplayName("Should return false for two digits with invalid first character")
    void shouldReturnFalseForTwoDigitsWithInvalidFirstCharacter() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("20"));
    }

    @Test
    @DisplayName("Should return false for two digits with invalid second character")
    void shouldReturnFalseForTwoDigitsWithInvalidSecondCharacter() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("02"));
    }

    @Test
    @DisplayName("Should return false for two digits with mismatched valid characters")
    void shouldReturnFalseForTwoDigitsWithMismatchedValidCharacters() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("68"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"101", "111", "181", "609", "906", "808", "818", "888"})
    @DisplayName("Should return true for valid three digit strobogrammic numbers")
    void shouldReturnTrueForValidThreeDigitStrobogrammicNumbers(String number) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"102", "123", "456", "789", "321", "654", "987"})
    @DisplayName("Should return false for invalid three digit numbers")
    void shouldReturnFalseForInvalidThreeDigitNumbers(String number) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(number));
    }

    @Test
    @DisplayName("Should return false for three digits with invalid middle character")
    void shouldReturnFalseForThreeDigitsWithInvalidMiddleCharacter() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("121"));
    }

    @Test
    @DisplayName("Should return false for three digits with invalid outer characters")
    void shouldReturnFalseForThreeDigitsWithInvalidOuterCharacters() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("201"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1001", "1111", "1881", "6009", "9006", "8008", "8118", "8888"})
    @DisplayName("Should return true for valid four digit strobogrammic numbers")
    void shouldReturnTrueForValidFourDigitStrobogrammicNumbers(String number) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1002", "1234", "5678", "9876", "4321", "6789"})
    @DisplayName("Should return false for invalid four digit numbers")
    void shouldReturnFalseForInvalidFourDigitNumbers(String number) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(number));
    }

    @Test
    @DisplayName("Should return false for four digits with one invalid character in first position")
    void shouldReturnFalseForFourDigitsWithInvalidFirstPosition() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("2001"));
    }

    @Test
    @DisplayName("Should return false for four digits with one invalid character in second position")
    void shouldReturnFalseForFourDigitsWithInvalidSecondPosition() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("1201"));
    }

    @Test
    @DisplayName("Should return false for four digits with one invalid character in third position")
    void shouldReturnFalseForFourDigitsWithInvalidThirdPosition() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("1021"));
    }

    @Test
    @DisplayName("Should return false for four digits with one invalid character in fourth position")
    void shouldReturnFalseForFourDigitsWithInvalidFourthPosition() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("1002"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"10101", "11111", "18881", "80808", "81118", "88888"})//"60906", "90609", 
    @DisplayName("Should return true for valid five digit strobogrammic numbers")
    void shouldReturnTrueForValidFiveDigitStrobogrammicNumbers(String number) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"10102", "12345", "56789", "98765", "43210"})
    @DisplayName("Should return false for invalid five digit numbers")
    void shouldReturnFalseForInvalidFiveDigitNumbers(String number) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(number));
    }

    @Test
    @DisplayName("Should return false for five digits with invalid middle character")
    void shouldReturnFalseForFiveDigitsWithInvalidMiddleCharacter() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("10201"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"100001", "111111", "188881",  "800008", "811118", "888888"})//"609906", "906609",
    @DisplayName("Should return true for valid six digit strobogrammic numbers")
    void shouldReturnTrueForValidSixDigitStrobogrammicNumbers(String number) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"100002", "123456", "567890", "987654", "432101"})
    @DisplayName("Should return false for invalid six digit numbers")
    void shouldReturnFalseForInvalidSixDigitNumbers(String number) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(number));
    }

    @Test
    @DisplayName("Should return true for longer valid strobogrammic number")
    void shouldReturnTrueForLongerValidStrobogrammicNumber() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("1000000001"));
    }

    @Test
    @DisplayName("Should return false for longer invalid strobogrammic number")
    void shouldReturnFalseForLongerInvalidStrobogrammicNumber() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("1000000002"));
    }

    @Test
    @DisplayName("Should return false when left character is not in map")
    void shouldReturnFalseWhenLeftCharacterIsNotInMap() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("31"));
    }

    @Test
    @DisplayName("Should return false when right character is not in map")
    void shouldReturnFalseWhenRightCharacterIsNotInMap() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("13"));
    }

    @Test
    @DisplayName("Should return false when both characters are not in map")
    void shouldReturnFalseWhenBothCharactersAreNotInMap() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("23"));
    }

    @Test
    @DisplayName("Should return false when characters do not match strobogrammic pairs")
    void shouldReturnFalseWhenCharactersDoNotMatchStrobogrammicPairs() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("61"));
    }
/* 
    @Test
    @DisplayName("Should handle edge case with valid strobogrammic pair at boundaries")
    void shouldHandleEdgeCaseWithValidStrobogrammicPairAtBoundaries() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("90906"));
    }
*/
    @Test
    @DisplayName("Should handle edge case with invalid strobogrammic pair at boundaries")
    void shouldHandleEdgeCaseWithInvalidStrobogrammicPairAtBoundaries() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("90916"));
    }

    @Test
    @DisplayName("Should return true when all positions match strobogrammic criteria")
    void shouldReturnTrueWhenAllPositionsMatchStrobogrammicCriteria() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("8180818"));
    }
/* 
    @Test
    @DisplayName("Should return false when one position fails strobogrammic criteria")
    void shouldReturnFalseWhenOnePositionFailsStrobogrammicCriteria() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("8181818"));
    }
*/
    @Test
    @DisplayName("Should handle maximum length edge case correctly")
    void shouldHandleMaximumLengthEdgeCaseCorrectly() {
        String longValidNumber = "8" + "0".repeat(98) + "8";
        assertTrue(StrobogrammicNumber.isStrobogrammic(longValidNumber));
    }

    @Test
    @DisplayName("Should handle maximum length edge case with invalid character")
    void shouldHandleMaximumLengthEdgeCaseWithInvalidCharacter() {
        String longInvalidNumber = "8" + "0".repeat(97) + "28";
        assertFalse(StrobogrammicNumber.isStrobogrammic(longInvalidNumber));
    }
}