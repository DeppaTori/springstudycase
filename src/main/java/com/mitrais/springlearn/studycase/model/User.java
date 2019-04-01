package com.mitrais.springlearn.studycase.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="user")
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name="user_name")
	@NotNull
	@Size(min=3, max=30)
	private String username;
	
	@Column(name="password")
	@NotNull
	@Size(min=5, max=30)
	private String password;
	
	@Transient
	private String[] roles;
	
	@OneToMany(mappedBy="user",fetch= FetchType.EAGER)
    private Set<UserRole> userRoles;
	
	public User() {
		super();

	}
	
	public User(String username,String password) {
		super();
		this.username = username;
		this.password = password;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "[id:"+id+" ,username: "+username+", password: "+password+"]";
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	public void addUserRole(UserRole userRole) {
		if(userRoles==null) {
			userRoles = new HashSet<UserRole>();
		}
		this.userRoles.add(userRole);
	}

	
	
	
	
	
	
	
}
