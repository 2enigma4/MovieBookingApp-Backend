package com.auth.authentication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.auth.authentication.helper.EmailValidator;
import com.auth.authentication.jwt.JwtTokenUtil;
import com.auth.authentication.model.AuthRequest;
import com.auth.authentication.model.AuthResponse;
import com.auth.authentication.model.User;
import com.auth.authentication.model.UserDtoUpdatePassword;
import com.auth.authentication.model.UserDtoRegister;
import com.auth.authentication.response.ResponseHandler;
import com.auth.authentication.service.UserService;

@RestController
@RequestMapping("auth/v1.0/moviebooking")
@CrossOrigin(origins ="*")
public class AuthController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JwtTokenUtil jwtUtil;
	
	@Autowired
	private UserService userService;
	

	@PostMapping("/register/user")
	public ResponseEntity<?> registerUser(@RequestBody UserDtoRegister user) {
		
		LOGGER.info("Register User start");
		
		User userAdded = userService.addUser(user);
		if (userAdded != null) {
			return ResponseHandler.generateResponse("success: user registered", HttpStatus.CREATED, userAdded);
		}
		return ResponseHandler.generateResponse("error: user not registered", HttpStatus.INTERNAL_SERVER_ERROR, null);
		
	}
	

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		
		LOGGER.info("Login User Start");
		if(request.checkNull())
			throw new NullPointerException("error: null values found");
		else if(!EmailValidator.isValid(request.getEmail()))
			throw new IllegalArgumentException("error: invalid email");	
		
		try {
			
			Authentication authentication = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

			User user = (User) authentication.getPrincipal();
			String accessToken = jwtUtil.generateAccessToken(user);
			AuthResponse response = new AuthResponse(user, accessToken);

			return ResponseHandler.generateResponse("success: logged in", HttpStatus.OK, response);

		} 
		catch (Exception ex) {
			return ResponseHandler.generateResponse("error: invalid credentials", HttpStatus.UNAUTHORIZED, null);
		}
	}

	@PostMapping("/update/password")
	public ResponseEntity<?> updatePassword(@RequestBody UserDtoUpdatePassword userDTO) {
		LOGGER.info("Update Password Start");
		User user = userService.updatePassword(userDTO);
		if (user != null)
			return ResponseHandler.generateResponse("success: password updated", HttpStatus.OK, user);
		else
			return ResponseHandler.generateResponse("error: password not updated", HttpStatus.INTERNAL_SERVER_ERROR,
					null);
	}
}
