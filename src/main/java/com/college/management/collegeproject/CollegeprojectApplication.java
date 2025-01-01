package com.college.management.collegeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;

@SpringBootApplication
public class CollegeprojectApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(CollegeprojectApplication.class, args);
	
	}
}
