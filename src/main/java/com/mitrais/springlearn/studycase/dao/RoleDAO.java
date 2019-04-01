package com.mitrais.springlearn.studycase.dao;

import com.mitrais.springlearn.studycase.model.UserRole;

public interface RoleDAO {
	public boolean delete(UserRole userRole);
	public boolean add(UserRole userRole);
}
