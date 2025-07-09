package isipv4valid;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;






@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GPTo3miniTest {

    private IPAddressChecker ipChecker;

    @BeforeAll
    public void initAll() {
        // Runs once before all tests
    }
    
    @BeforeEach
    public void init() {
        ipChecker = new IPAddressChecker();
    }
    
    @AfterEach
    public void tearDown() {
        // Runs after each test
    }
    
    @AfterAll
    public void tearDownAll() {
        // Runs once after all tests
    }
    
    @DisplayName("Test isValidIPV4Part with various inputs (White Box & Black Box)")
    @ParameterizedTest(name = "[{index}] Input {0} => Expected {1}")
    @CsvSource({
        // Length greater than 3
        "'1234', false",
        // Contains non-digit character
        "'1a2', false",
        "'abc', false",
        // Leading zero checks
        "'00', false",
        "'001', false",
        "'05', false",
        // Valid single and multiple digit cases, including boundary value 255 and 256 (out-of-bound)
        "'1', true",
        "'10', true",
        "'255', true",
        "'256', false",
        // Empty string input
        "'', false"
    })
    public void testIsValidIPV4Part(String input, boolean expected) {
        try {
            boolean actual = ipChecker.isValidIPV4Part(input);
            assertEquals(expected, actual, "Expected isValidIPV4Part(" + input + ") to be " + expected);
        } catch (Exception e) {
            fail("Exception thrown in testIsValidIPV4Part with input: " + input + " - " + e.getMessage());
        }
    }
    
    @DisplayName("Test isValidIPV4 with various IP addresses (White Box & Black Box)")
    @ParameterizedTest(name = "[{index}] IP \"{0}\" => Expected {1}")
    @MethodSource("provideIPV4TestCases")
    public void testIsValidIPV4(String ipInput, int expected) {
        try {
            int actual = ipChecker.isValidIPV4(ipInput);
            assertEquals(expected, actual, "Expected isValidIPV4(" + ipInput + ") to return " + expected);
        } catch (Exception e) {
            fail("Exception thrown in testIsValidIPV4 with input: " + ipInput + " - " + e.getMessage());
        }
    }
    
    static Stream<Arguments> provideIPV4TestCases() {
        return Stream.of(
            // Black Box: null IP string should return 0
            Arguments.of(null, 0),
            // Not exactly three dots
            Arguments.of("1.2.3", 0),
            Arguments.of("1.2.3.4.5", 0),
            // Valid IP addresses (equivalence partitioning and boundary analysis)
            Arguments.of("1.2.3.4", 1),
            Arguments.of("0.0.0.0", 1),
            Arguments.of("10.10.10.10", 1),
            Arguments.of("255.255.255.255", 1),
            // IP with invalid part due to leading zero
            Arguments.of("01.2.3.4", 0),
            // IP with part out-of-range
            Arguments.of("256.2.3.4", 0),
            // Case where tokenization loses parts: multiple dots together (e.g., "1...1")
            Arguments.of("1...1", 0)
        );
    }
}