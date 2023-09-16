package org.ereach.inc.exceptions;

public class RegistrationFailedException extends EReachBaseException{
	
	public RegistrationFailedException(String message) {
		super(message);
	}
	public RegistrationFailedException(Throwable throwable) {
		super(throwable);
	}
	public RegistrationFailedException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
