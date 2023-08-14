package com.college.management.collegeproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.college.management.collegeproject.model.Department;
import com.college.management.collegeproject.model.Student;
import com.college.management.collegeproject.repositories.StudentRepository;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	public List<Student> getAll(){
		return studentRepository.findAll();
	}
	public Student saveStudentDetails(Student student) {
		return studentRepository.save(student);
	}
	public List<Student> getStudentsByDepartmentAndBatch(Department dept,String batch){
		List<Student> students=studentRepository.findByBatch(batch);
		return 
				students.stream().filter(s->s.getDepartment().getName().equals(dept.getName())).toList();
	}
	public Student getStudentByRollNum(String num) {
		return studentRepository.findByRollNum(num);
	}
	public List<Student> getById(Iterable<Integer> ids) {
		List<Student>stu=studentRepository.findAllById(ids);
		return stu;
	}
	public Student getStudentByMailId(String mailId) {
		String[] arr=mailId.split("@");
		return getStudentByRollNum(arr[0]);
	}

}
