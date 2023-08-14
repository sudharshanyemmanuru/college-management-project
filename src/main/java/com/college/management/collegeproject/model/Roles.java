package com.college.management.collegeproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Roles {
	@Id
	@Column(name = "role_id")
	private int id;
	
	@Column(name="role_name")
	private String role_name;

	public int getRole_id() {
		return id;
	}

	public void setRole_id(int role_id) {
		this.id = role_id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

}
