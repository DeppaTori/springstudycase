package com.mitrais.springlearn.studycase.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mitrais.springlearn.studycase.model.User;
import com.mitrais.springlearn.studycase.service.UserService;

@Controller
@RequestMapping("/")
public class HomeController {
	
//	@Autowired
//	UserService userService;
	
	@Autowired
	@Qualifier("userServiceJPA")
	UserService userService;
	
	@GetMapping
	public String index(Principal principal,Model model) {
		String userName = principal != null?principal.getName():null;
		model.addAttribute("username_session",userName);
		
		return "home/index";
	}
	
	
	@PreAuthorize("hasAuthority('USER_READ_PRIVILEGE')")
	@GetMapping("/user_list")
	public String userList(Principal principal,Model model) {
		String userName = principal != null?principal.getName():null;
		model.addAttribute("username_session",userName);
		
		List<User> user = userService.list();
		model.addAttribute("users",user);
		return "home/user_list";
	}
	
	
	@RequestMapping("/login")
	public String getLogin() {
		return "login/login";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied() {
		return "home/access-denied";
	}
	
	@RequestMapping("/logout")
	public void logout() {

	}
	
	
	
	

}
