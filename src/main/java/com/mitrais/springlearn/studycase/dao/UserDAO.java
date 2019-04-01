package com.mitrais.springlearn.studycase.dao;

import java.util.List;
import java.util.Map;

import com.mitrais.springlearn.studycase.model.User;

public interface UserDAO {
	public List<User> list();
	public User find(Long id);
	public User find(String username);
	public boolean update(User user);
	public boolean save(User user);
	public boolean delete(Long id);
}
