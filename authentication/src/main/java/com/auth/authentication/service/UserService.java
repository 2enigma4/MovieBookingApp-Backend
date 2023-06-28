package com.auth.authentication.service;

import java.util.List;

import com.auth.authentication.model.User;
import com.auth.authentication.model.UserDtoUpdatePassword;
import com.auth.authentication.model.UserDtoRegister;

public interface UserService 
{
	public User addUser(UserDtoRegister user);// user registration
	
	public List<User> getAllUsers();// will be visible only if you are logged in
	
	public User updatePassword(UserDtoUpdatePassword user);
}

