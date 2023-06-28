package com.auth.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.authentication.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	 
	public Optional<Role> getByName(String name);
}
