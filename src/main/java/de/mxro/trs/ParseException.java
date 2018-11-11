package de.mxro.trs;

/**
 * 
 * <p>Exception issued when invalid input supplied for parsing commands or directions.
 * <p>This is an unchecked exception to keep method signatures simple and free of checked exceptions.
 *
 */
public class ParseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParseException(String message) {
		super(message);
	}

	
	
}
