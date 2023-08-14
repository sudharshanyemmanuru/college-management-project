package com.college.management.collegeproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.college.management.collegeproject.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>{
	
	public Users findByEmail(String email);
	public Users findByuserName(String userName);

}
