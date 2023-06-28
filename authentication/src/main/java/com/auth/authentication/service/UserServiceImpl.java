package com.auth.authentication.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.auth.authentication.helper.EmailValidator;
import com.auth.authentication.model.Role;
import com.auth.authentication.model.User;
import com.auth.authentication.model.UserDtoUpdatePassword;
import com.auth.authentication.model.UserDtoRegister;
import com.auth.authentication.repository.RoleRepository;
import com.auth.authentication.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	public static final String roleUser = "ROLE_USER";

	@Override
	public User addUser(UserDtoRegister user) {
		if (user.checkNull())
			throw new NullPointerException("Null value found in the entity");
		
		if(!EmailValidator.isValid(user.getEmail()))
			throw new IllegalArgumentException("Email id is not valid");
		
		if (userRepo.findByEmail(user.getEmail()) != null)
			throw new IllegalArgumentException("Email id already registered");
		

		User newUser = new User();
		newUser.setUserName(user.getUserName());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
		newUser.setSecretAnswer(user.getSecretAnswer());
		
		Optional<Role> role = roleRepo.getByName(roleUser);
		if(role.isEmpty())
			throw new EntityNotFoundException("Role not found in database");
		
		newUser.addRole(role.get());
		return userRepo.saveAndFlush(newUser);
	}


	@Override
	public List<User> getAllUsers() {
		List<User> userList = userRepo.findAll();
		if (userList != null & userList.size() > 0)
			return userList;
		else
			return null;
	}

	@Override
	public User updatePassword(UserDtoUpdatePassword user) {
		if (user.isValueNull())
			throw new NullPointerException("Null value found in the entity");

		User userByEmail = userRepo.findByEmail(user.getEmail());
		if (userByEmail==null)
			throw new EntityNotFoundException("Email Id Not Registered With Us");
		else if (!user.getSecretAnswer().equalsIgnoreCase(userByEmail.getSecretAnswer()))
			throw new IllegalArgumentException("Secret Answer Provided Is Wrong");
		else {
			userByEmail.setPassword(user.getPassword());
			User updatedUser = userRepo.saveAndFlush(userByEmail);
			return updatedUser;
		}

	}

}
