package com.college.management.collegeproject.model;

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
import jakarta.persistence.Table;

@Entity
@Table(name="faculty")
public class Faculty {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
	@GenericGenerator(strategy = "native",name="native")
	@Column(name="faculty_id")
	private int facultyId;
	@Column(name="name")
	private String name;
	private String email;
	private String contact;
	@Column(name="designation")
	private String designation;
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
	@JoinColumn(name = "department_id",referencedColumnName = "department_id",nullable = false)
	private Department department;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
	@JoinTable(
			name="faculty_subject",
			joinColumns = {@JoinColumn(name="faculty_id",referencedColumnName = "faculty_id")},
			inverseJoinColumns = {@JoinColumn(name="subject_id",referencedColumnName ="subject_id")}
			)
	private List<Subject> subjects=new ArrayList<>();
	
	public List<Subject> getSubjects(){return this.subjects;}
	public int getFacultyId() {
		return facultyId;
	}
	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	@Override
	public boolean equals(Object f) {
		Faculty faculty=(Faculty)f;
		if(this.email.equals(faculty.email))
			return true;
		return false;
	}
}
