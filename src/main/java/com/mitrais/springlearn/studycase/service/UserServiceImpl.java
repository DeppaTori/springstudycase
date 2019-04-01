package com.mitrais.springlearn.studycase.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.springlearn.studycase.dao.RoleDAO;
import com.mitrais.springlearn.studycase.dao.UserDAO;
import com.mitrais.springlearn.studycase.model.User;
import com.mitrais.springlearn.studycase.model.UserRole;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDAO;

	@Autowired
	RoleDAO roleDAO;

	@Override
	@Transactional
	public List<User> list() {
		return userDAO.list();
	}

	@Override
	@Transactional
	public User find(Long id) {
		return userDAO.find(id);
	}

	private String valid(User user, String retypePassword) {
		if (!user.getPassword().equals(retypePassword))
			return "Password didn't match.";
		/// check if username exist
		User findUser = userDAO.find(user.getUsername());
		if (findUser != null) {
			if (findUser.getId() != user.getId()) {
				return "Username already taken. Please choose another one.";
			}
		}
		return null;
	}

	@Override
	@Transactional
	public String delete(Long id) {
		if (!userDAO.delete(id))
			return "Something problem when deleting record.";
		return null;
	}

	@Override
	@Transactional
	public Map saveUpdate(User user, String retypePassword, String roles) {
		Map resultMap = new HashMap();

		String errorMessage = valid(user, retypePassword);
		String mode = "save";
		boolean result = false;
		if (errorMessage == null) {

			if (user.getId() == null || user.getId() == 0) {
				
				


				if (!userDAO.save(user))
					errorMessage = "Something problem when saving record.";
				else {
					
					/// store roles
					String[] roleInputList = roles.split(",");
					
					
					for (String newRoleStr : roleInputList) {
						newRoleStr = newRoleStr.trim();
						if (newRoleStr.length() > 0) {
							UserRole newUserRole = new UserRole();
							newUserRole.setRole(newRoleStr);
							newUserRole.setUserId(user.getId());
							boolean addNewRoleResult = roleDAO.add(newUserRole);
						}

					}
					///end store roles
					
					result = true;
					
				}
					
			} else {
				mode = "update";
//				UserRole userRole = new UserRole();
//				userRole.setRole("ADMIN");
//				user.addUserRole(userRole);

				// get all user role from db
				String[] roleInputList = roles.split(",");
				User userDb = userDAO.find(user.getId());

				// check new role
				List<String> newRoleList = new ArrayList();

				for (String userRoleInput : roleInputList) {
					boolean found = false;
					userRoleInput = userRoleInput.trim();
					if (userRoleInput.length() > 0) {
						for (UserRole userRole : userDb.getUserRoles()) {
							if (userRoleInput.equals(userRole.getRole())) {
								found = true;
							}
						}
					}
					if (!found) {
						newRoleList.add(userRoleInput);
					}

				}

				// check deleted role
				List<String> roleDeleted = new ArrayList();
				for (UserRole userRole : userDb.getUserRoles()) {
					boolean found = false;
					for (String userRoleInput : roleInputList) {
						if (userRole.getRole().equals(userRoleInput)) {
							found = true;
						}
					}
					if (!found) {
						roleDeleted.add(userRole.getRole());
					}
				}

				// store to db
				for (String string : roleDeleted) {

					UserRole userRoleDelete = new UserRole();
					userRoleDelete.setRole(string);
					userRoleDelete.setUserId(user.getId());
					boolean deleteRoleResult = roleDAO.delete(userRoleDelete);

				}

				for (String newRoleStr : newRoleList) {
					newRoleStr = newRoleStr.trim();
					if (newRoleStr.length() > 0) {
						UserRole newUserRole = new UserRole();
						newUserRole.setRole(newRoleStr);
						newUserRole.setUserId(user.getId());
						boolean addNewRoleResult = roleDAO.add(newUserRole);
					}

				}

				/*
				 * //check deleted Role
				 * 
				 * String[] roleDeleted = deletedRoles.split(",");
				 * 
				 * for(String string:roleDeleted) { if(string.length() > 2) { UserRole
				 * userRoleDelete = new UserRole(); userRoleDelete.setRole(string);
				 * userRoleDelete.setUserId(user.getId()); boolean deleteRoleResult =
				 * roleDAO.delete(userRoleDelete); } }
				 * 
				 * //end check deleted Role
				 * 
				 * //check new roles String[] newRoleList = newRoles.split(","); for(String
				 * newRoleStr:newRoleList) { if(newRoleStr.length() > 2) { UserRole newUserRole
				 * = new UserRole(); newUserRole.setRole(newRoleStr);
				 * newUserRole.setUserId(user.getId()); boolean addNewRoleResult =
				 * roleDAO.add(newUserRole); } } //end check new roles
				 * 
				 */

				if (!userDAO.update(user))
					errorMessage = "Something problem when updating record.";
				else
					result = true;
			}

		}

		resultMap.put("result", result);
		resultMap.put("message", errorMessage);
		resultMap.put("mode", mode);

		return resultMap;
	}

}
