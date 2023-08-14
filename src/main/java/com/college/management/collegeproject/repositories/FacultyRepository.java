package com.college.management.collegeproject.repositories;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.college.management.collegeproject.model.Faculty;
@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer>{
	@Query("SELECT f FROM Faculty f WHERE f.email=:email")//JPQL Java Persistance Query Language. it is Platform independent Object oriented Query Language
	public Faculty findByEmail(String email);
	
}
