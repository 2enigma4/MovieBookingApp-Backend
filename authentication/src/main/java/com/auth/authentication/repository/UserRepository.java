package com.auth.authentication.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.authentication.model.User;
 
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
 
	User findByEmail(String email);
	
}
