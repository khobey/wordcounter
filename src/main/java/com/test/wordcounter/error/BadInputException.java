package com.test.wordcounter.error;

public class BadInputException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6455875821444755638L;

	
	public BadInputException(Exception causeException){
	     super(causeException);
	   }

	public BadInputException(String errorMsg) {
		super(errorMsg);
	}
}
