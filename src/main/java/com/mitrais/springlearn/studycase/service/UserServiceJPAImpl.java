package com.mitrais.springlearn.studycase.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mitrais.springlearn.studycase.model.Role;
import com.mitrais.springlearn.studycase.model.User;
import com.mitrais.springlearn.studycase.repository.RoleRepository;
import com.mitrais.springlearn.studycase.repository.UserRepository;

@Service
@Qualifier("userServiceJPA")
public class UserServiceJPAImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<User> list() {
		
		return userRepository.findAll();
	}

	@Override
	public User find(Long id) {
		User user = userRepository.findById(id).get();
		return user;
	}

	@Override
	public String delete(Long id) {
		userRepository.deleteById(id);
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
			
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			if (user.getId() == null || user.getId() == 0) {
				
				String[] roleInputList = roles.split(",");
				Set<Role> newRoles = new HashSet<Role>();
				
				for(String newRoleStr:roleInputList) {
					if(!newRoleStr.equals("")) {
						Long tempRoleID = Long.parseLong(newRoleStr);
						if(tempRoleID > 0) {
							Role role = roleRepository.getOne(tempRoleID);
						
							//newRole.setName("CONTOH");
							user.getRoles().add(role);
							role.getUsers().add(user);
							
						}
					}
					
				}
				
				
				userRepository.save(user);
				
				
				
				/// store roles
//				String[] roleInputList = roles.split(",");
//				
//				
//				for (String newRoleStr : roleInputList) {
//					newRoleStr = newRoleStr.trim();
//					if (newRoleStr.length() > 0) {
//						UserRole newUserRole = new UserRole();
//						newUserRole.setRole(newRoleStr);
//						newUserRole.setUserId(user.getId());
//						boolean addNewRoleResult = roleDAO.add(newUserRole);
//					}
//
//				}
				///end store roles


//				if (userRepository.save(user)==null)
//					errorMessage = "Something problem when saving record.";
//				else {
//					
//					
//					
//					result = true;
//					
//				}
				result = true;
					
			} else {
				mode = "update";
//				UserRole userRole = new UserRole();
//				userRole.setRole("ADMIN");
//				user.addUserRole(userRole);

				// get all user role from db
//				String[] roleInputList = roles.split(",");
//				User userDb = userDAO.find(user.getId());
//
//				// check new role
//				List<String> newRoleList = new ArrayList();
//
//				for (String userRoleInput : roleInputList) {
//					boolean found = false;
//					userRoleInput = userRoleInput.trim();
//					if (userRoleInput.length() > 0) {
//						for (UserRole userRole : userDb.getUserRoles()) {
//							if (userRoleInput.equals(userRole.getRole())) {
//								found = true;
//							}
//						}
//					}
//					if (!found) {
//						newRoleList.add(userRoleInput);
//					}
//
//				}
//
//				// check deleted role
//				List<String> roleDeleted = new ArrayList();
//				for (UserRole userRole : userDb.getUserRoles()) {
//					boolean found = false;
//					for (String userRoleInput : roleInputList) {
//						if (userRole.getRole().equals(userRoleInput)) {
//							found = true;
//						}
//					}
//					if (!found) {
//						roleDeleted.add(userRole.getRole());
//					}
//				}
//
//				// store to db
//				for (String string : roleDeleted) {
//
//					UserRole userRoleDelete = new UserRole();
//					userRoleDelete.setRole(string);
//					userRoleDelete.setUserId(user.getId());
//					boolean deleteRoleResult = roleDAO.delete(userRoleDelete);
//
//				}
//
//				for (String newRoleStr : newRoleList) {
//					newRoleStr = newRoleStr.trim();
//					if (newRoleStr.length() > 0) {
//						UserRole newUserRole = new UserRole();
//						newUserRole.setRole(newRoleStr);
//						newUserRole.setUserId(user.getId());
//						boolean addNewRoleResult = roleDAO.add(newUserRole);
//					}
//
//				}

				

				if (userRepository.save(user)==null)
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
	
	private String valid(User user, String retypePassword) {
		if (!user.getPassword().equals(retypePassword))
			return "Password didn't match.";
		/// check if username exist
		User findUser = userRepository.findFirstByUsername(user.getUsername());
		if (findUser != null) {
			if (findUser.getId() != user.getId()) {
				return "Username already taken. Please choose another one.";
			}
		}
		return null;
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findFirstByUsername(username);
	}

	@Override
	public List<Role> findRolesByUserId(Long id) {
		return userRepository.findRoleById(id);
	}
	


}
