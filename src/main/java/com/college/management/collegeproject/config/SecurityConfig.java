package com.college.management.collegeproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain getFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeHttpRequests(auth->auth.requestMatchers("/home").authenticated()
				.requestMatchers("/profile").permitAll()
				.requestMatchers("/static/**").permitAll()
				.requestMatchers("/downloadFile").authenticated()
				.requestMatchers("/login").permitAll()
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/student/**").hasRole("STUDENT")
		        .requestMatchers("/faculty/**").hasRole("FACULTY")).
		formLogin(form->form.loginPage("/login").defaultSuccessUrl("/home",true).failureUrl("/login?error=true").permitAll())
		.logout(out->out.logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll());
		return http.build();
	}
	@Bean
	public PasswordEncoder getEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
