package com.vb.mca.moviecruiser.exception;

@SuppressWarnings("serial")
public class MovieExistException extends Exception {

	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MovieExistException(final String message) {
		super();
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "MovieExistException [message: " + message + "]";
	}
}
