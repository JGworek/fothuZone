package zone.fothu.pets.exception;

public class WrongTurnException extends RuntimeException {

	private static final long serialVersionUID = -8396350232756748807L;

	public WrongTurnException(String message) {
		super(message);
	}

}
