package isprime;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;



class IsPrimeGTTest {

    @Test
    void testPrimeNumber() throws Exception {
        String[] args = {"7"};
        assertTrue(IsPrime.isPrime(args));
    }

    @Test
    void testNonPrimeNumber() throws Exception {
        String[] args = {"8"};
        assertFalse(IsPrime.isPrime(args));
    }

    @Test
    void testOneIsPrime() throws Exception {
        String[] args = {"1"};
        assertTrue(IsPrime.isPrime(args));
    }

    @Test
    void testZeroThrowsNoPositiveNumberException() {
        String[] args = {"0"};
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(args));
    }

    @Test
    void testNegativeNumberThrowsNoPositiveNumberException() {
        String[] args = {"-5"};
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(args));
    }

    @Test
    void testNonIntegerThrowsNoPositiveNumberException() {
        String[] args = {"abc"};
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(args));
    }

    @Test
    void testNullArgsThrowsMissingArgumentException() {
        assertThrows(MissingArgumentException.class, () -> IsPrime.isPrime(null));
    }

    @Test
    void testMultipleArgsThrowsOnly1ArgumentException() {
        String[] args = {"3", "5"};
        assertThrows(Only1ArgumentException.class, () -> IsPrime.isPrime(args));
    }

    @Test
    void testFloatValueIsCastToInt() throws Exception {
        String[] args = {"5.9"};
        // 5.9 cast to int is 5, which is prime
        assertTrue(IsPrime.isPrime(args));
    }
}