package com.auth.authentication.exception;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.auth.authentication.model.ApiError;




@Order(value = Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(UsernameNotFoundException.class)
	protected ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage()));
	}

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage()));
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}
	
	@ExceptionHandler(NullPointerException.class)
	protected ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage()));
	}
	
	@ExceptionHandler(ServletException.class)
	protected ResponseEntity<Object> handleServletException(ServletException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage()));
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
