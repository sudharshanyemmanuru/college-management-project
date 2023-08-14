package com.college.management.collegeproject.repositories;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.college.management.collegeproject.model.Role;
import com.college.management.collegeproject.model.Roles;
@Repository
public interface RolesRepository extends ListCrudRepository<Roles, Integer>{
	public Roles getById(int id);

}
