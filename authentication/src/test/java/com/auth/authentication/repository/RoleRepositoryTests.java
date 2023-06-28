package com.auth.authentication.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
 
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.auth.authentication.model.Role;
import com.auth.authentication.model.User;
 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
	
    @Autowired 
    private RoleRepository repo;
     
//    @Test
//    public void testCreateRoles() {
//        Role admin = new Role("ROLE_ADMIN");
//        Role user = new Role("ROLE_USER");
//         
//        repo.saveAll(List.of(admin, user));
//         
//        long count = repo.count();
//        assertEquals(2, count);
//    }
    

}