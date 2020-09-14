package com.accenture.rest.legacy.exception;

public class ValidationException extends Exception {

	private String statusCode;

	/**
	 * 
	 */
	private static final long serialVersionUID = -6393213609730967229L;

	public ValidationException() {
		super();
	}

	/**
	 * @param message
	 */
	public ValidationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ValidationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
}
