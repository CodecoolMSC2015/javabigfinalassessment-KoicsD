package reader;

public class ReaderException extends Exception {

	public ReaderException() {
	}

	public ReaderException(String message) {
		super(message);
	}

	public ReaderException(Throwable cause) {
		super(cause);
	}

	public ReaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
