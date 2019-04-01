package com.mitrais.springlearn.studycase.service;

import java.util.List;
import java.util.Map;

import com.mitrais.springlearn.studycase.model.User;

public interface UserService {
	public List<User> list();
	public User find(Long id);
	public String delete(Long id);
	public Map saveUpdate(User user,String retypePassword,String roles);
}
