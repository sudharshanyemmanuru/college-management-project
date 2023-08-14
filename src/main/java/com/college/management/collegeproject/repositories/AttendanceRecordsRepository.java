package com.college.management.collegeproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.college.management.collegeproject.model.AttendanceRecords;
@Repository
public interface AttendanceRecordsRepository extends JpaRepository<AttendanceRecords, Integer>{

}
