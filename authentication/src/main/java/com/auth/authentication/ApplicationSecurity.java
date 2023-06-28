package com.auth.authentication;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth.authentication.jwt.JwtTokenFilter;
import com.auth.authentication.jwt.JwtTokenUtil;
import com.auth.authentication.repository.UserRepository;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
	    prePostEnabled = false, securedEnabled = false, jsr250Enabled = true
	)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
	
	@Autowired private UserRepository userRepo;
	
	@Autowired private JwtTokenFilter jwtTokenFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		LOGGER.info("In Auth Configure Start");
		auth.userDetailsService(username -> (Optional.of(userRepo.findByEmail(username)))
				.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found.")));
		LOGGER.info("In Auth Configure End");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		LOGGER.info("Configure Start");
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.authorizeRequests()
				.antMatchers("/auth/v1.0/moviebooking/register/user").permitAll()
				.antMatchers("/auth/v1.0/moviebooking/login").permitAll()
				.antMatchers("/auth/v1.0/moviebooking/update/password").permitAll()
				.antMatchers("/actuator/**").permitAll()
				.anyRequest().authenticated();
		
        http.exceptionHandling()
                .authenticationEntryPoint(
                    (request, response, ex) -> {
                        response.sendError(
                            HttpServletResponse.SC_UNAUTHORIZED,
                            ex.getMessage()
                        );
                    }
                );
        
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		LOGGER.info("Configure STOP");
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}

