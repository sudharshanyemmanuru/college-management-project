package com.college.management.collegeproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.college.management.collegeproject.model.Department;
import com.college.management.collegeproject.repositories.DepartmentRepository;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepository departmentRepository;
	public Department saveDepartment(Department department) {
		return departmentRepository.save(department);
	}
	public Department getDepartmentByName(String name) {
		return departmentRepository.getByName(name);
	}
	public List<Department> getAll(){
		return departmentRepository.findAll();
	}
}
