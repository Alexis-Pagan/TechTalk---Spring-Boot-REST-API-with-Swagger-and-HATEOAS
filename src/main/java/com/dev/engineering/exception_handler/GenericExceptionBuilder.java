package com.dev.engineering.exception_handler;

public class GenericExceptionBuilder extends Exception {

	private static final long serialVersionUID = 1L;
	
	public GenericExceptionBuilder(String errorMessage) {
		super(errorMessage);
	}

}
