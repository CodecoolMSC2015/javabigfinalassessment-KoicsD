package searching;

public class DefaultCaseException extends RuntimeException {

	public DefaultCaseException() {
		super();
	}

	public DefaultCaseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DefaultCaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public DefaultCaseException(String message) {
		super(message);
	}

	public DefaultCaseException(Throwable cause) {
		super(cause);
	}

}
