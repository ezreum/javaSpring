package org.pap.exceptions;

@SuppressWarnings("serial")
public class DangerException extends Exception {

	public DangerException() {
		super();
	}
	
	public DangerException(String message) {
		super(message);
	}
	
}
