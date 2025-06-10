package isleapyear;

public class EmptyException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmptyException() {
		super("El string esta vacío");
	}
	

}
