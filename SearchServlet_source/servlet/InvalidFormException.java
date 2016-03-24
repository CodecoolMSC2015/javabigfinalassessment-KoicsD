package servlet;

public class InvalidFormException extends Exception {

	public InvalidFormException() {
		super();
	}

	public InvalidFormException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidFormException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidFormException(String message) {
		super(message);
	}

	public InvalidFormException(Throwable cause) {
		super(cause);
	}
	
}
