package getbonus.exceptions;

public class QuantityLessThan1Exception extends Exception {
    public QuantityLessThan1Exception() {
        super();
    }

    public QuantityLessThan1Exception(String message) {
        super(message);
    }
}
