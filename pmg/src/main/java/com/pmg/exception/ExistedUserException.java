package com.pmg.exception;

public class ExistedUserException extends RuntimeException {
	public ExistedUserException(String message) {
		super(message);
	}

	public ExistedUserException(String message, Throwable cause) {
		super(message, cause);
	}
}
