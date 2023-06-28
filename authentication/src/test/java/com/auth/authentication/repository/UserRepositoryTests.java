package com.auth.authentication.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.auth.authentication.model.Role;
import com.auth.authentication.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository repo;

//	@Test
//    public void testAssignRoleToUser3() {
//        Integer userId = 204;
//        Integer roleId = 113;
//        User user = repo.findById(userId).get();
//        user.addRole(new Role(roleId));
//         
//        User updatedUser = repo.save(user);
//        assertThat(updatedUser.getRoles()).hasSize(1);
//         
//    }

	
//	    @Test
//	    public void testCreateUser() {
//	        @SuppressWarnings("deprecation")
//			NoOpPasswordEncoder passwordEncoder = (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//	        String password = passwordEncoder.encode("admin");
//	         
//	        User newUser = new User("admin","admin@gmail.com", password,"bruno");
//	        User savedUser = repo.save(newUser);
//	         
//	        assertThat(savedUser).isNotNull();
//	        assertThat(savedUser.getUserId()).isGreaterThan(0);
//	    }

}
