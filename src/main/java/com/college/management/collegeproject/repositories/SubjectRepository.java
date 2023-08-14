package com.college.management.collegeproject.repositories;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.college.management.collegeproject.model.Subject;

@Repository
public interface SubjectRepository extends ListCrudRepository<Subject,Integer>{
	public Subject findBysubjectCode(String subjectCode);
}
