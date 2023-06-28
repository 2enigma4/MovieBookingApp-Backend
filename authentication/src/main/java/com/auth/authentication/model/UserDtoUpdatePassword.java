package com.auth.authentication.model;

import java.util.Objects;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoUpdatePassword {

	private String email;

	private String password;

	private String secretAnswer;

	public boolean isValueNull() {
		return Stream.of(email, password, secretAnswer)
				.anyMatch(Objects::isNull);
	}
}
