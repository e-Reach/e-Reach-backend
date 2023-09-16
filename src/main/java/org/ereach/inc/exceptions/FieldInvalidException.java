package org.ereach.inc.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldInvalidException extends EReachBaseException{
	
	private String exceptionCause;
	private String[] reasons;
	
	public FieldInvalidException(String message) {
		super(message);
	}
	public FieldInvalidException(Throwable throwable) {
		super(throwable);
	}
	public FieldInvalidException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
