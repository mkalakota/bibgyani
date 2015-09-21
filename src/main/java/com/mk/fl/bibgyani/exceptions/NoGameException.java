/**
 *
 */
package com.mk.fl.bibgyani.exceptions;

/**
 * @author Mouli Kalakota
 */
public class NoGameException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoGameException() {
		super();
	}

	/**
	 * @param message
	 */
	public NoGameException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NoGameException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoGameException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NoGameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
