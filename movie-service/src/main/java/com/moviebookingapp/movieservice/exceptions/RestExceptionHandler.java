package com.moviebookingapp.movieservice.exceptions;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import javax.persistence.EntityNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.moviebookingapp.movieservice.model.ApiError;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage()));
	}

	@ExceptionHandler(DuplicateEntityFoundException.class)
	protected ResponseEntity<Object> handleDuplicateEntityFoundException(DuplicateEntityFoundException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, ex.getMessage()));
	}
	
	@ExceptionHandler(NullPointerException.class)
	protected ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
