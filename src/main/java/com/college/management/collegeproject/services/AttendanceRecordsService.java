package com.college.management.collegeproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.college.management.collegeproject.model.AttendanceRecords;
import com.college.management.collegeproject.repositories.AttendanceRecordsRepository;

@Service
public class AttendanceRecordsService {
	@Autowired
	private AttendanceRecordsRepository attendanceRecordsRepository;
	public AttendanceRecords saveAttendanceRecord(AttendanceRecords attendanceRecords) {
		return attendanceRecordsRepository.save(attendanceRecords);
	}

}
