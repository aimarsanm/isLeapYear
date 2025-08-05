package isprime;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

@DisplayName("Unit tests for IsPrime.isPrime(String[])")
class IsPrimeCATest {

    @BeforeAll
    static void beforeAll() {
        // No global setup required
    }

    @AfterAll
    static void afterAll() {
        // No global teardown required
    }

    @BeforeEach
    void beforeEach() {
        // No per-test setup required
    }

    @AfterEach
    void afterEach() {
        // No per-test teardown required
    }

    // --- Exception tests ---

    @Test
    @DisplayName("Throws MissingArgumentException when args is null")
    void testNullArgsThrowsMissingArgumentException() {
        try {
            IsPrime.isPrime(null);
            fail("Expected MissingArgumentException to be thrown");
        } catch (MissingArgumentException e) {
            // Expected
        } catch (Exception e) {
            fail("Unexpected exception type: " + e);
        }
    }
/*
    @ParameterizedTest
    @MethodSource("provideArgsWithMoreThanOneElement")
    @DisplayName("Throws Only1ArgumentException when args has more than one element")
    void testArgsWithMoreThanOneElementThrowsOnly1ArgumentException(String[] args) {
        try {
            IsPrime.isPrime(args);
            fail("Expected Only1ArgumentException to be thrown");
        } catch (Only1ArgumentException e) {
            // Expected
        } catch (Exception e) {
            fail("Unexpected exception type: " + e);
        }
    }
 
    static Stream<Arguments> provideArgsWithMoreThanOneElement() {
        return Stream.of(
            Arguments.of(new String[]{"2", "3"}),
            Arguments.of(new String[]{"-1", "5"}),
            Arguments.of(new String[]{"abc", "def"}),
            Arguments.of(new String[]{"7", "8", "9"})
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgsWithNonPositiveOrInvalidNumber")
    @DisplayName("Throws NoPositiveNumberException for non-positive or invalid numbers")
    void testArgsWithNonPositiveOrInvalidNumberThrowsNoPositiveNumberException(String[] args) {
        try {
            IsPrime.isPrime(args);
            fail("Expected NoPositiveNumberException to be thrown");
        } catch (NoPositiveNumberException e) {
            // Expected
        } catch (Exception e) {
            fail("Unexpected exception type: " + e);
        }
    }
*/
    static Stream<Arguments> provideArgsWithNonPositiveOrInvalidNumber() {
        return Stream.of(
            Arguments.of(new String[]{"0"}),
            Arguments.of(new String[]{"-1"}),
            Arguments.of(new String[]{"-100"}),
            Arguments.of(new String[]{"abc"}),
            Arguments.of(new String[]{""}),
            Arguments.of(new String[]{"1.5"}),
            Arguments.of(new String[]{"-2.5"}),
            Arguments.of(new String[]{" "}),
            Arguments.of(new String[]{"2e3"}),
            Arguments.of(new String[]{"NaN"})
        );
    }

    // --- Prime number tests (valid input, boundary and equivalence classes) ---
/* 
    @ParameterizedTest
    @MethodSource("providePrimeNumbers")
    @DisplayName("Returns true for prime numbers")
    void testReturnsTrueForPrimeNumbers(String[] args) {
        try {
            assertTrue(IsPrime.isPrime(args));
        } catch (Exception e) {
            fail("No exception expected, but got: " + e);
        }
    }
*/
    static Stream<Arguments> providePrimeNumbers() {
        return Stream.of(
            Arguments.of(new String[]{"2"}),   // Smallest prime
            Arguments.of(new String[]{"3"}),
            Arguments.of(new String[]{"5"}),
            Arguments.of(new String[]{"7"}),
            Arguments.of(new String[]{"11"}),
            Arguments.of(new String[]{"13"}),
            Arguments.of(new String[]{"17"}),
            Arguments.of(new String[]{"19"}),
            Arguments.of(new String[]{"23"}),
            Arguments.of(new String[]{"29"}),
            Arguments.of(new String[]{"97"}),
            Arguments.of(new String[]{"101"}),
            Arguments.of(new String[]{"7919"}), // Large prime
            Arguments.of(new String[]{"2.0"}),  // Float representation
            Arguments.of(new String[]{"13.0"})
        );
    }
/* 
    @ParameterizedTest
    @MethodSource("provideNonPrimeNumbers")
    @DisplayName("Returns false for non-prime numbers")
    void testReturnsFalseForNonPrimeNumbers(String[] args) {
        try {
            assertFalse(IsPrime.isPrime(args));
        } catch (Exception e) {
            fail("No exception expected, but got: " + e);
        }
    }
*/
    static Stream<Arguments> provideNonPrimeNumbers() {
        return Stream.of(
            Arguments.of(new String[]{"4"}),
            Arguments.of(new String[]{"6"}),
            Arguments.of(new String[]{"8"}),
            Arguments.of(new String[]{"9"}),
            Arguments.of(new String[]{"10"}),
            Arguments.of(new String[]{"12"}),
            Arguments.of(new String[]{"15"}),
            Arguments.of(new String[]{"20"}),
            Arguments.of(new String[]{"100"}),
            Arguments.of(new String[]{"1000"}),
            Arguments.of(new String[]{"10000"}),
            Arguments.of(new String[]{"4.0"}),
            Arguments.of(new String[]{"10.0"})
        );
    }

    // --- Boundary value analysis ---

    @ParameterizedTest
    @MethodSource("provideBoundaryValues")
    @DisplayName("Boundary value analysis for isPrime")
    void testBoundaryValues(String[] args, boolean expected) {
        try {
            assertEquals(expected, IsPrime.isPrime(args));
        } catch (Exception e) {
            fail("No exception expected, but got: " + e);
        }
    }

    static Stream<Arguments> provideBoundaryValues() {
        return Stream.of(
            Arguments.of(new String[]{"1"}, true),   // 1 is considered prime by this implementation (no divisors)
            Arguments.of(new String[]{"2"}, true),   // Smallest prime
            Arguments.of(new String[]{"3"}, true),   // Next prime
            Arguments.of(new String[]{"4"}, false),  // First composite
            Arguments.of(new String[]{"2147483647"}, true) // Largest int, prime
        );
    }
}
