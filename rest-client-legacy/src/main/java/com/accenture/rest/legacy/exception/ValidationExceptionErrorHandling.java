package com.accenture.rest.legacy.exception;

public class ValidationExceptionErrorHandling extends ValidationException {

	private static final long serialVersionUID = 8027857750746017477L;

	public ValidationExceptionErrorHandling() {
		super();
	}
	
	private String errorCode;
	private String description;
    private String tid;
    
	/**
	 * @param message
	 */
	public ValidationExceptionErrorHandling(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ValidationExceptionErrorHandling(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ValidationExceptionErrorHandling(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
}