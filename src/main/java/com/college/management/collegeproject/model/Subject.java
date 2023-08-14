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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="subjects")
public class Subject {
	@Id
	@Column(name = "subject_id")
	@GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
	@GenericGenerator(name="native",strategy = "native")
	private int subjectId;
	@Column(name = "name")
	private String name;
	@Column(name="year_sem")
	private String yearSem;
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
	@JoinColumn(name = "department_id",referencedColumnName = "department_id",nullable = false)
	private Department subDepartment;
	@Column(name = "subject_code")
	private String subjectCode;
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "subject")
	private List<UploadFile> documentUploads;
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST,mappedBy = "subjects")
	private List<Student> students= new ArrayList<>();
	@ManyToMany(mappedBy = "subjects",fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
	private List<Faculty> faculties= new ArrayList<>();
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST,mappedBy = "subject")
	private List<Assignment> assigenments;
	public List<Assignment> getAssigenments() {
		return assigenments;
	}
	public List<Student> getStudents(){
		return this.students;
	}
	public List<Faculty> getFaculties() {
		return faculties;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getYearSem() {
		return yearSem;
	}
	public void setYearSem(String yearSem) {
		this.yearSem = yearSem;
	}
	public Department getSubDepartment() {
		return subDepartment;
	}
	public void setSubDepartment(Department subDepartment) {
		this.subDepartment = subDepartment;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String toString() {
		return "subject :"+this.name+" Dept : "+this.subDepartment.getName();
	}
	@Override
	public boolean equals(Object s) {
		Subject sub=(Subject)s;
		if(this.subjectCode.equals(sub.subjectCode))
			return true;
		return false;
	}
	public List<UploadFile> getDocumentUploads() {
		return documentUploads;
	}
	
	

}
