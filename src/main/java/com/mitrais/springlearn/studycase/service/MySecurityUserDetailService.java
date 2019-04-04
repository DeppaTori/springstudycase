package com.mitrais.springlearn.studycase.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mitrais.springlearn.studycase.model.Role;
import com.mitrais.springlearn.studycase.model.User;

@Service
public class MySecurityUserDetailService implements UserDetailsService{

	
	@Autowired
	@Qualifier("userServiceJPA")
	UserService userService;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
	//	System.out.println(user.getUserRoles());
		
		// set roles
//		String[] roles = new String[user.getUserRoles().size()];
//		int i=0;
//		for(UserRole userRole : user.getUserRoles()) {
//			roles[i] = userRole.getRole();
//			i++;
//		}
//		if(roles.length > 0) {
//			user.setRoles(roles);
//		}
		//end set roles
		
		if (user == null) {
            throw new UsernameNotFoundException(username);
        }
		
		
		
		 return new org.springframework.security.core.userdetails.User(
	                user.getUsername(), user.getPassword(), getAuthorities(user.getRoles()));
		
		
//		
//        UserBuilder userBuilder = null;
//        userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
//        userBuilder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
//      //  userBuilder.roles(user.getRoles());
//        return userBuilder.build();
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(
            Set<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }
	
	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
	
	private List<String> getPrivileges(Set<Role> roles) {
        List<String> privileges = roles.stream().
        							flatMap(role->role.getPrivileges().stream()).
        							map(privilege -> privilege.getName()).
        							collect(Collectors.toList());
        
        for(String string:privileges) {
        	   System.out.println(string); 
        }
     
        
        return privileges;
    }

	
	

}
