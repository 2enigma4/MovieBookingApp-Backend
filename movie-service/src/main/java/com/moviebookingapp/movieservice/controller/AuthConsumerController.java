package com.moviebookingapp.movieservice.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.moviebookingapp.movieservice.model.AuthRequest;
import com.moviebookingapp.movieservice.model.UserDtoRegister;
import com.moviebookingapp.movieservice.model.UserDtoUpdatePassword;
import com.moviebookingapp.movieservice.response.ResponseHandler;

@RestController
@RequestMapping("/auth/consumer/v1.0")
@CrossOrigin(origins ="*")
public class AuthConsumerController {

	@PostMapping("/login")
	public ResponseEntity<?> consumeLogin(@RequestBody AuthRequest request) throws RestClientException, Exception {
//		String baseUrl = "http://44.227.26.206:8084/auth/v1.0/moviebooking/login";// API consumption.. actual api is hidden -not exposed

		
		String baseUrl = "http://localhost:8084/auth/v1.0/moviebooking/login";// API consumption.. actual api is hidden -not exposed

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Object> result;

		try {
			result = restTemplate.exchange(baseUrl, HttpMethod.POST, getHeaders(request),
					new ParameterizedTypeReference<Object>() {
					});
		} catch (Exception e) {
			return ResponseHandler.generateResponse("Invalid Credentials", HttpStatus.UNAUTHORIZED, null);
		}

		return ResponseEntity.status(HttpStatus.OK).body(result.getBody());
	}

	private static HttpEntity<AuthRequest> getHeaders(AuthRequest request) {
		HttpHeaders headers = new HttpHeaders();

		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Access-Control-Allow-Origin", "*");
		return new HttpEntity<AuthRequest>(request, headers);
	}
	
	@PostMapping("/register/user")
	public ResponseEntity<?> consumeRegister(@RequestBody UserDtoRegister user)throws Exception{
//		String baseUrl = "http://44.227.26.206:8084/auth/v1.0/moviebooking/register/user";
		
		String baseUrl = "http://localhost:8084/auth/v1.0/moviebooking/register/user";
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();

		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Access-Control-Allow-Origin", "*");
		HttpEntity<UserDtoRegister> entity =  new HttpEntity<UserDtoRegister>(user, headers);
		
		ResponseEntity<Object> result;
		
		try {
			result = restTemplate.exchange(baseUrl, HttpMethod.POST,entity,
					new ParameterizedTypeReference<Object>() {
					});
		} 
		catch(Exception e) {
			String[] split2 = (e.getMessage().split(","))[2].split(":");
			String msg = split2[1].substring(1, split2[1].length()-3);

			return ResponseHandler.generateResponse(msg, HttpStatus.INTERNAL_SERVER_ERROR, null);
		}

		return ResponseEntity.status(HttpStatus.OK).body(result.getBody());
	}
	
	@PostMapping("/update/password")
	public ResponseEntity<?> consumeUpdatePassword(@RequestBody UserDtoUpdatePassword user)throws Exception{
//		String baseUrl = "http://44.227.26.206:8084/auth/v1.0/moviebooking/update/password";
		
		String baseUrl = "http://localhost:8084/auth/v1.0/moviebooking/update/password";
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();

		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Access-Control-Allow-Origin", "*");
		HttpEntity<UserDtoUpdatePassword> entity =  new HttpEntity<UserDtoUpdatePassword>(user, headers);
		
		ResponseEntity<Object> result;
		
		try {
			result = restTemplate.exchange(baseUrl, HttpMethod.POST,entity,
					new ParameterizedTypeReference<Object>() {
					});
		} 
		catch(Exception e) {
			String[] split2 = (e.getMessage().split(","))[2].split(":");
			String msg = split2[1].substring(1, split2[1].length()-3);

			return ResponseHandler.generateResponse(msg, HttpStatus.INTERNAL_SERVER_ERROR, null);
		}

		return ResponseEntity.status(HttpStatus.OK).body(result.getBody());
	}
}
