package com.auth.authentication.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {

	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;

	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	public ApiError(HttpStatus status) {
		this();
		this.setStatus(status);
	}

	public ApiError(HttpStatus status, String message) {
		this();
		this.setStatus(status);
		this.setMessage(message);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
