package com.moviebookingapp.movieservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class ApiErrorTest {

	ApiError error = new ApiError();

	@Test
	public void Test_getStatus() {
		error.setStatus(HttpStatus.OK);
		assertEquals(HttpStatus.OK, error.getStatus());
	}

	@Test
	public void Test_getTimeStamp() {
		LocalDateTime date = LocalDateTime.now();
		error.setTimestamp(date);
		assertEquals(date, error.getTimestamp());
	}

	@Test
	public void Test_getMessage() {
		error.setMessage("error");
		assertEquals("error", error.getMessage());
	}
}
