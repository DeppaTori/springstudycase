package com.mitrais.springlearn.studycase.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.springlearn.studycase.model.Role;
import com.mitrais.springlearn.studycase.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}



	@Override
	public Map save(Role role) {
		Map<String,Object> results = new HashMap<String,Object>();
		results.put("result",false);
		results.put("message","Something problem when saving your role.");
		if(roleRepository.save(role)!= null) {
			results.put("result",true);
			results.put("message","Success");
			
		}
		return results;
	}

}
