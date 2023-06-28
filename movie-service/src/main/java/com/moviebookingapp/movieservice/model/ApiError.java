package com.moviebookingapp.movieservice.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiError {

	private HttpStatus status;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;

	public ApiError() {
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
	
}
