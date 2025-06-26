package isprime;

public class MissingArgumentException extends Exception {

	private static final long serialVersionUID = 1L;

	public MissingArgumentException() {
		super("El string esta vacío");
	}
	

}
