package com.moviebookingapp.movieservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@SpringBootApplication
public class MovieServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieServiceApplication.class, args);
	}

	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("*");
            }
        };
    }
	
	
	@Configuration
	class OpenApiConfig
	{
		@Bean
		public OpenAPI customconfig()
		{
			final String securitySchemeName = "bearerAuth";
			
			return new OpenAPI().addSecurityItem(new SecurityRequirement()
					.addList(securitySchemeName)).components(new Components().addSecuritySchemes(securitySchemeName, new SecurityScheme()
							.name(securitySchemeName).type(SecurityScheme.Type.HTTP).scheme("bearer")
							.bearerFormat("JWT")));
					
					}
		
	}
}
