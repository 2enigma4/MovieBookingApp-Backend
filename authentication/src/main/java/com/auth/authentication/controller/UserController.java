package com.auth.authentication.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.authentication.model.User;
import com.auth.authentication.response.ResponseHandler;
import com.auth.authentication.service.UserService;

@RestController
@RequestMapping("api/v1.0/moviebooking")
@CrossOrigin(origins ="*")
public class UserController
{
	@Autowired
	private UserService uService;
	
	@RolesAllowed("ROLE_ADMIN")
	@GetMapping("/all/users")
	public ResponseEntity<?> getAllUsers()
	{
		List<User> userlist = uService.getAllUsers();
		if(userlist!=null)
		{
			return ResponseHandler.generateResponse("success: retrieved user list", HttpStatus.OK ,userlist);
		}
		return ResponseHandler.generateResponse("error: user list is empty", HttpStatus.NO_CONTENT , new ArrayList<>());
	}
	
}