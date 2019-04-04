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

@Entity
@Table(name="role")
public class Role implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="role_id")
	private Long id;
	
	@Column(name="role_name")
	@NotNull
	private String name;
	
	@ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        },mappedBy="roles")
	private Set<User> users = new HashSet<User>();
	
	@ManyToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinTable(
			name="role_privilege",
			joinColumns= {@JoinColumn(name="role_id")},
			inverseJoinColumns= {@JoinColumn(name="privilege_id")})
	private Set<Privilege> privileges;
	
	public Role() {
		super();
	}
	
	public Role(Long id,String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Role(Long id) {
		super();
		this.id = id;
	}
	
	public Role(String name) {
		super();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "[id: "+id+",name:"+name+"]";
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	
	
	
	
	
	
}
