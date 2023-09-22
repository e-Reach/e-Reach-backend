package org.ereach.inc.exceptions;

public class EReachBaseException extends RuntimeException{
	
	private String exceptionCause;
	private String[] reasons;
	
	public EReachBaseException(String message) {
		super(message);
	}
	public EReachBaseException(Throwable throwable) {
		super(throwable);
	}
	public EReachBaseException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
