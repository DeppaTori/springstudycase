package com.mitrais.springlearn.studycase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mitrais.springlearn.studycase.model.Role;
import com.mitrais.springlearn.studycase.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	public User findFirstByUsername(String Username);
	
	@Query("select r from User u left join u.roles r where u.id=?1")
	public List<Role> findRoleById(Long id);
}
