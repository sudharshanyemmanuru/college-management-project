package com.college.management.collegeproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.college.management.collegeproject.model.Subject;
import com.college.management.collegeproject.repositories.SubjectRepository;

@Service
public class SubjectService {
	@Autowired
	private SubjectRepository subjectRepository;
	public Subject saveSubjectDetails(Subject subject) {
		return subjectRepository.save(subject);
		
	}
	public Subject getSubjectById(String id) {
		
		return subjectRepository.findBysubjectCode(id);
	}

}
