package com.college.management.collegeproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.college.management.collegeproject.model.Faculty;
import com.college.management.collegeproject.repositories.FacultyRepository;

@Service
public class FacultyService {
	@Autowired
	private FacultyRepository facultyRepository;
	public List<Faculty> getAll(){
		return facultyRepository.findAll();
	}
	public Faculty saveDetails(Faculty faculty) {
		return facultyRepository.save(faculty);
	}
	public Faculty getByEmail(String emailId) {
		return facultyRepository.findByEmail(emailId);
	}
}
