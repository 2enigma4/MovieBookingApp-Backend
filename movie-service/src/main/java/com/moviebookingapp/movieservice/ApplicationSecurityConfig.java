package com.moviebookingapp.movieservice;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.moviebookingapp.movieservice.filter.JwtTokenFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = false, securedEnabled = false, jsr250Enabled = true
)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtTokenFilter jwtTokenFilter;

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		http.csrf().disable();
//        http.authorizeRequests().anyRequest().permitAll();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	
		http.cors().and().csrf().disable();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		
		http.authorizeRequests()
				.antMatchers("/auth/consumer/v1.0/login","/auth/consumer/v1.0/register/user","/auth/consumer/v1.0/update/password").permitAll()
				.antMatchers("/swagger-ui/**","/v3/api-docs/**","/swagger-ui/index.html/**").permitAll()
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
		
	}
	
	
}
