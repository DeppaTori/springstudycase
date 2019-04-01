package com.mitrais.springlearn.studycase.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="user_role")
public class UserRole implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@NotNull
	@Column(name="user_id")
	private Long userId;
	
	@Id
	@NotNull
	@Column(name="role")
	@Size(min=2, max=10)
	private String role;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	public UserRole() {
		super();
	}
	
	public UserRole(Long userId,String role) {
		this.userId = userId;
		this.role = role;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "[user_id: "+userId+", role: "+role+"]";
	}
	
	
	
}
