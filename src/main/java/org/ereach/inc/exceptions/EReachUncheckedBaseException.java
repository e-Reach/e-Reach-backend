package org.ereach.inc.exceptions;

public class EReachUncheckedBaseException extends RuntimeException {
	
	private String exceptionCause;
	private String[] reasons;
	
	public EReachUncheckedBaseException(String message) {
		super(message);
	}
	
	public EReachUncheckedBaseException(Throwable throwable) {
		super(throwable);
	}
}
