package zone.fothu.pets.exception;

public class PetAlreadySelectedException extends RuntimeException {

	private static final long serialVersionUID = -8436165591587041441L;

	public PetAlreadySelectedException(String message) {
		super(message);
	}

}
