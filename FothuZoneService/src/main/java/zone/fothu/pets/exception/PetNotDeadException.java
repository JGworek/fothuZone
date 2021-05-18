package zone.fothu.pets.exception;

public class PetNotDeadException extends RuntimeException {

	private static final long serialVersionUID = 7504670372730611253L;

	public PetNotDeadException(String message) {
		super(message);
	}

}
