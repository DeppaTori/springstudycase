package com.mitrais.springlearn.studycase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mitrais.springlearn.studycase.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{
	
}
