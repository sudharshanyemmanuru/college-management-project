package com.college.management.collegeproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.college.management.collegeproject.model.AssignmentSubmission;
@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, Integer>{

}
