package com.college.management.collegeproject.services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.college.management.collegeproject.model.Assignment;
import com.college.management.collegeproject.model.AssignmentSubmission;
import com.college.management.collegeproject.model.Student;
import com.college.management.collegeproject.repositories.AssignmentSubmissionRepository;

@SuppressWarnings("unused")
@Service
public class AssignmentSubmissionService {
	@Autowired
	private AssignmentSubmissionRepository assignmentSubmissionRepository;
	public AssignmentSubmission submitAssignment(Student student,Assignment assignment,MultipartFile assignmentFile) throws IOException {
		AssignmentSubmission submission=new AssignmentSubmission();
		submission.setAssignment(assignment);
		submission.setStudent(student);
		submission.setContent(assignmentFile.getBytes());
		submission.setDate(assignmentFile.getOriginalFilename());
		return assignmentSubmissionRepository.save(submission);
		
	}
	public Optional<AssignmentSubmission> getResponseById(int id) {
		return assignmentSubmissionRepository.findById(id);
	}

}
