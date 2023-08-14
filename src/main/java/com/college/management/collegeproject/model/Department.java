package com.college.management.collegeproject.model;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Department")
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
	@GenericGenerator(name="native",strategy = "native")
	private int department_id;
	@Column(name = "name")
	private String name;
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,mappedBy = "department")
	private List<Faculty> faculties;
	@OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.PERSIST,mappedBy = "department1")
	private List<Student> students;
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,mappedBy="subDepartment")
	private List<Subject> subjects;
	public List<Student> getStudents() {
		return students;
	}
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Faculty> getFaculties(){
		return this.faculties;
	}
	public List<Subject> getSubjects(){
		return subjects;
	}
}
