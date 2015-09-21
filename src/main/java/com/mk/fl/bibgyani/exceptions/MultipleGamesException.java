/**
 *
 */
package com.mk.fl.bibgyani.exceptions;

/**
 * @author Mouli Kalakota
 */
public class MultipleGamesException extends Exception {

	private static final long serialVersionUID = 1L;

	public MultipleGamesException() {
		super();
	}

	/**
	 * @param message
	 */
	public MultipleGamesException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public MultipleGamesException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MultipleGamesException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MultipleGamesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
