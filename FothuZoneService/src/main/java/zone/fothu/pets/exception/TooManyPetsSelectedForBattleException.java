package zone.fothu.pets.exception;

public class TooManyPetsSelectedForBattleException extends RuntimeException {

	private static final long serialVersionUID = 6387847635725553019L;

	public TooManyPetsSelectedForBattleException(String message) {
		super(message);
	}

}
