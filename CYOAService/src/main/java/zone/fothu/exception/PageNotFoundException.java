package zone.fothu.exception;

public class PageNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2329992299469419407L;

	  public PageNotFoundException(String message) {
	    super(message);
	  }
}
