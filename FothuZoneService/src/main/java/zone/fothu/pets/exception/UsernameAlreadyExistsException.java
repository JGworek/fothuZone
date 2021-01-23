package zone.fothu.pets.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 3119836115460384589L;

	public UsernameAlreadyExistsException(String message) {
		super(message);
	}

}
