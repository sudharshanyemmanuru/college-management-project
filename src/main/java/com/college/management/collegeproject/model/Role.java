package com.college.management.collegeproject.model;

public enum Role {
	ADMIN(1),
	STUDENT(2),
	FACULTY(3);
	public final int role;
	Role(int role){
		this.role=role;
	}
	public int getRole() {
		return this.role;
	}

}
