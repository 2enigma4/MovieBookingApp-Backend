package com.moviebookingapp.movieservice.exceptions;

@SuppressWarnings("serial")
public class DuplicateEntityFoundException extends Exception{
	
	public DuplicateEntityFoundException() {
		super();
	}
	
	public DuplicateEntityFoundException(String message) {
		super(message);
	}
	
}
