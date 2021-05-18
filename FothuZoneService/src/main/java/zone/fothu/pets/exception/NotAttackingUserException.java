package zone.fothu.pets.exception;

public class NotAttackingUserException extends RuntimeException {

	private static final long serialVersionUID = -8190780563570044781L;

	public NotAttackingUserException(String message) {
		super(message);
	}

}
