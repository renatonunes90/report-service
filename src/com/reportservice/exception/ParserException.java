package com.reportservice.exception;

import com.reportservice.util.ErrorMessage;

public class ParserException extends Exception {

	private static final long serialVersionUID = -6325578533427480388L;
	
	public ParserException( ErrorMessage message ) {
		super(message.getMessage());
	}
}
