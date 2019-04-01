package com.mitrais.springlearn.studycase.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mitrais.springlearn.studycase.dao.UserDAO;
import com.mitrais.springlearn.studycase.model.User;
import com.mitrais.springlearn.studycase.model.UserRole;

@Service
public class MySecurityUserDetailService implements UserDetailsService{

	@Autowired
	UserDAO userDAO;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.find(username);
	//	System.out.println(user.getUserRoles());
		
		// set roles
		String[] roles = new String[user.getUserRoles().size()];
		int i=0;
		for(UserRole userRole : user.getUserRoles()) {
			roles[i] = userRole.getRole();
			i++;
		}
		if(roles.length > 0) {
			user.setRoles(roles);
		}
		//end set roles
		
		if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        UserBuilder userBuilder = null;
        userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
        userBuilder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
        userBuilder.roles(user.getRoles());
        return userBuilder.build();
	}

}
