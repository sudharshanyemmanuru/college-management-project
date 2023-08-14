package com.college.management.collegeproject.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.college.management.collegeproject.model.Assignment;
import com.college.management.collegeproject.repositories.AssignmentRepository;

@Service
public class AssignmentService {
	@Autowired
	private AssignmentRepository assignmentRepository;
	public Assignment uploadAssignment(Assignment assignment) {
		return assignmentRepository.save(assignment);
	}
	public Optional<Assignment> getById(int id) {
		return assignmentRepository.findById(id);
	}

}
