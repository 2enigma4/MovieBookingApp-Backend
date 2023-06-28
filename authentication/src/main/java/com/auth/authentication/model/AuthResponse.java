package com.auth.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
	
//	private Integer userId;
//	private String email;
	private User user;
	private String accessToken;

}
