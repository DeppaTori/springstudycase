package com.mitrais.springlearn.studycase.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="user")
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;
	
	@Column(name="username")
	@NotNull
	@Size(min=3, max=30)
	private String username;
	
	@Column(name="password")
	@NotNull
	@Size(min=5, max=100)
	private String password;
	

	
	@ManyToMany(cascade = { CascadeType.PERSIST,CascadeType.MERGE })
    @JoinTable(
        name = "user_role", 
        joinColumns = { @JoinColumn(name = "user_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private Set<Role> roles = new HashSet<Role>();
	
	public User() {
		super();

	}
	
	public User(Long id) {
		super();
		this.id = id;
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
	
	

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "[id:"+id+" ,username: "+username+", password: "+password+"]";
	}

	

	
	
	
	
	
	
	
}
