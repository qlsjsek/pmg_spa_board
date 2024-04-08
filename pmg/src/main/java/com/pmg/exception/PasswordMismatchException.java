package com.pmg.exception;

public class PasswordMismatchException extends RuntimeException {
	private Object data;

	public PasswordMismatchException(String message) {
		super(message);
	}

	public PasswordMismatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public void setData(Object data) {
		this.data = data;
	}
}
