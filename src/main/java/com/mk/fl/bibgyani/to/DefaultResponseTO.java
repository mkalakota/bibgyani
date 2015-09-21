/**
 *
 */
package com.mk.fl.bibgyani.to;

/**
 * @author Mouli Kalakota
 */
public class DefaultResponseTO {

	private boolean error = false;
	private String message;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
