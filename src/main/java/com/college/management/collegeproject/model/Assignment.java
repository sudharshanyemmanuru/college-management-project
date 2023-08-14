package com.college.management.collegeproject.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="assignments")
public class Assignment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "assignment_id")
	private int assignmentId;
	@Column(name="assignment_num")
	private int assignmentNumber;
	@Column(name="batch")
	private String batch;
	@Column(name="year_sem")
	private String yearSem;
	@Column(name="section")
	private String section;
	@Column(name="dept")
	private String dept;
	@Column(name = "topic")
	private String topic;
	@Column(name = "questions")
	private String questions;
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
	@JoinColumn(name = "sub_id",referencedColumnName = "subject_id")
	private Subject subject;
	@OneToOne(mappedBy = "assignment",fetch = FetchType.EAGER,cascade=CascadeType.PERSIST)
	private AssignmentSubmission assignmentSubmission;
	public int getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}
	public int getAssignmentNumber() {
		return assignmentNumber;
	}
	public void setAssignmentNumber(int assignmentNumber) {
		this.assignmentNumber = assignmentNumber;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getYearSem() {
		return yearSem;
	}
	public void setYearSem(String yearSem) {
		this.yearSem = yearSem;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getQuestions() {
		return questions;
	}
	public void setQuestions(String questions) {
		this.questions = questions;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public AssignmentSubmission getAssignmentSubmission() {
		return assignmentSubmission;
	}
	public void setAssignmentSubmission(AssignmentSubmission assignmentSubmission) {
		this.assignmentSubmission = assignmentSubmission;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	

}
