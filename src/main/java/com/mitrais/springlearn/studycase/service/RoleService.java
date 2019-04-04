package com.mitrais.springlearn.studycase.service;

import java.util.List;
import java.util.Map;

import com.mitrais.springlearn.studycase.model.Role;

public interface RoleService {
	public List<Role> findAll();
	public Map save(Role role);
}
