package isprime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



class GPT4oGTTest {

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
    void testMissingArgumentException() {
        String[] args = null;
        assertThrows(MissingArgumentException.class, () -> IsPrime.isPrime(args));
    }

    @Test
    void testOnly1ArgumentException() {
        String[] args = {"7", "11"};
        assertThrows(Only1ArgumentException.class, () -> IsPrime.isPrime(args));
    }

    @Test
    void testNoPositiveNumberExceptionNegativeNumber() {
        String[] args = {"-5"};
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(args));
    }

    @Test
    void testNoPositiveNumberExceptionZero() {
        String[] args = {"0"};
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(args));
    }

    @Test
    void testNoPositiveNumberExceptionInvalidInput() {
        String[] args = {"abc"};
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(args));
    }

    @Test
    void testPrimeNumberOne() throws Exception {
        String[] args = {"1"};
        assertTrue(IsPrime.isPrime(args)); // 1 is considered prime in this implementation
    }
}