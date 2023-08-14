package com.college.management.collegeproject.repositories;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.college.management.collegeproject.model.Department;
@Repository
public interface DepartmentRepository extends ListCrudRepository<Department, Integer>{
	public Department getByName(String name);
}
