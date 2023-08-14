package com.college.management.collegeproject.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.college.management.collegeproject.model.Users;
import com.college.management.collegeproject.repositories.UsersRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class AuthenticateProvider implements AuthenticationProvider{
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Users users=usersRepository.findByEmail(authentication.getName());
		List<GrantedAuthority> authorities= new ArrayList<>();
		if(users!=null ) {
			String enocder=passwordEncoder.encode(authentication.getCredentials().toString());
			if (users.getPassword().equals(enocder)) {
				System.out.println("User Identified");
				authorities.add(new SimpleGrantedAuthority(users.getRoles().getRole_name()));
				return new UsernamePasswordAuthenticationToken(users.getUserName(), users.getPassword(), authorities);
			}else {
				throw new BadCredentialsException("Inavalid Credintials");
			}
		}throw new BadCredentialsException("Inavalid Credintials");
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
