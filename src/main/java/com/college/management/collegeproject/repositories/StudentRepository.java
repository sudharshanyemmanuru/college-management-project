package com.college.management.collegeproject.repositories;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.college.management.collegeproject.model.Student;

@Repository
public interface StudentRepository extends ListCrudRepository<Student, Integer>{
	public List<Student> findByBatch(String batch);
	public Student findByRollNum(String rollNum);
	
}
