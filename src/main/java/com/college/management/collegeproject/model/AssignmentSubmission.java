package com.college.management.collegeproject.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="assignmentsubmission")
public class AssignmentSubmission {
	@Id
	@Column(name="submission_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sumissionId;
	@Column(name = "submission_date")
	private String date;
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "student_id",referencedColumnName = "student_id")
	private Student student;
	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "assignment_id",referencedColumnName = "assignment_id")
	private Assignment assignment;
	@Lob
	@Column(name ="content")
	private byte[] content;
	public int getSumissionId() {
		return sumissionId;
	}
	public void setSumissionId(int sumissionId) {
		this.sumissionId = sumissionId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Assignment getAssignment() {
		return assignment;
	}
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
	public byte[] getContent() {
		return this.content;
	}
	public void setContent(byte[] content) {
		this.content=content;
	}

}
