package com.moviebookingapp.movieservice.model;

import java.util.Objects;
import java.util.stream.Stream;


import lombok.Getter;
import lombok.Setter;
 
@Getter
@Setter
public class AuthRequest {

    private String email;
     
    private String password;
 
    public boolean checkNull() {
		return Stream.of(email,password)
				.anyMatch(Objects::isNull);
	}
}
