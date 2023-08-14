package com.college.management.collegeproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="student")
public class Student implements Serializable{
	@Id
	@Column(name="student_id")
	@GeneratedValue(strategy = GenerationType.AUTO,generator="native")
	@GenericGenerator(strategy = "native",name="native")
	private int sId;
	private String name;
	@Column(name="roll_number")
	private String rollNum;
	private String batch;
	@ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.PERSIST)
	@JoinColumn(name = "department_id",referencedColumnName = "department_id",nullable=false)
	private Department department1;
	private String section;
	@ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.PERSIST)
	@JoinTable(
		name="student_subject",
		joinColumns = {@JoinColumn(name="student_id",referencedColumnName = "student_id")},
		inverseJoinColumns = {@JoinColumn(name="subject_id",referencedColumnName = "subject_id")}
	)
	private List<Subject> subjects= new ArrayList<>();
	@OneToMany(mappedBy = "student",fetch = FetchType.EAGER,cascade=CascadeType.PERSIST)
	private List<AttendanceRecords> attendanceRecords;
	@OneToMany(mappedBy = "student",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private List<AssignmentSubmission> assignmentSubmissions;
	@Override
	public String toString() {
		return "Student [name=" + name + ", rollNum=" + rollNum + ", batch=" + batch + ", section=" + section
				+ ", subjects=" + subjects + "]";
	}
	public Department getDepartment1() {
		return department1;
	}
	public List<Subject> getSubjects() {
		return subjects;
	}
	public int getsId() {
		return sId;
	}
	public void setsId(int sId) {
		this.sId = sId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRollNum() {
		return rollNum;
	}
	public void setRollNum(String rollNum) {
		this.rollNum = rollNum;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public Department getDepartment() {
		return department1;
	}
	public void setDepartment(Department department) {
		this.department1 = department;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public boolean equals(Object s) {
		Student student=(Student)s;
		if(this.rollNum.equals(student.getRollNum()))
			return true;
		return false;
	}
	public List<AttendanceRecords> getAttendanceRecords(){
		return this.attendanceRecords;
	}
	public List<AssignmentSubmission> getAssignmentSubmissions(){
		return this.assignmentSubmissions;
	}
	

}
