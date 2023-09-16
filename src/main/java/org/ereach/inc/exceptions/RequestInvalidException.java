package org.ereach.inc.exceptions;

public class RequestInvalidException extends EReachBaseException{
	
	public RequestInvalidException(String message){
		super(message);
	}
	
	public RequestInvalidException(String message, Throwable throwable){
		super(message, throwable);
	}
}
