package palindrome;

public class MissingArgumentException extends Exception {
    public MissingArgumentException() {}

    public MissingArgumentException(String message) {
        super(message);
    }
}
