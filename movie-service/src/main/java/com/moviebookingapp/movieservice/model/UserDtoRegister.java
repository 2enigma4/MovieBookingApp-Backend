package com.moviebookingapp.movieservice.model;

import java.util.Objects;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDtoRegister {

	private String userName;

	private String email;

	private String password;

	private String secretAnswer;
	
	public boolean checkNull() {
		return Stream.of(userName,email,password,secretAnswer)
				.anyMatch(Objects::isNull);
	}
}
