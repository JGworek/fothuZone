package zone.fothu.pets.exception;

public class DeadBattlePetException extends RuntimeException {

	private static final long serialVersionUID = -7950989766423168189L;

	public DeadBattlePetException(String message) {
		super(message);
	}
}