package com.mitrais.springlearn.studycase.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mitrais.springlearn.studycase.model.User;
import com.mitrais.springlearn.studycase.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	UserService userService;
	

	
	@GetMapping("/")
	public String userList(Model model,Principal principal) {
	
		List<User> user = userService.list();
		Map modelMap = model.asMap();
	
		model.addAttribute("flash_status",modelMap.get("status"));
		model.addAttribute("flash_message",modelMap.get("message"));
		model.addAttribute("username_logged",principal.getName());
		model.addAttribute("users",user);
		return "admin/userlist";
	}
	
	@GetMapping("/user_add")
	public String addUser(Model model) {
		model.addAttribute("user",new User());
		return "admin/userform";
	}
	
	@GetMapping("/user/{id}/edit")
	public String getEditUser(@PathVariable(value="id") String id,Model model) {
		User user = userService.find(Long.parseLong(id));
	
		model.addAttribute("user",user);
		return "admin/userform";
	}
	
	@PostMapping("/user/postuser")
	public String postUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult ,
			@RequestParam("retype_password") String retypePassword,Model model,
			RedirectAttributes attributes,
			@RequestParam("roles") String roles) {
		if(bindingResult.hasErrors()) {
		
			return "admin/userform";
		}
		
		
		Map saveUpdateResult = userService.saveUpdate(user, retypePassword,roles);
		boolean result = (boolean)saveUpdateResult.get("result");
		String messageResult = (String)saveUpdateResult.get("message");
		String mode = (String)saveUpdateResult.get("mode");
	
		if(!result) {
			model.addAttribute("message",messageResult);
			return "admin/userform";
		}
		
		attributes.addFlashAttribute("message",mode.equals("update")?"User has been updated successfully.":"User has been saved successfully.");
		attributes.addFlashAttribute("status",true);
		
		return "redirect:/admin/";
	}
	
	@GetMapping("/user/{id}/delete")
	public String deleteUser(@PathVariable("id") Long id, RedirectAttributes attributes) {
		String deleteMessage = userService.delete(id);
		if(deleteMessage==null) {
			attributes.addFlashAttribute("status",true);
			attributes.addFlashAttribute("message","User has been deleted successfully.");
		
		} else {
			attributes.addFlashAttribute("status",false);
			attributes.addFlashAttribute("message","Something problem when deleting user.");
		}
			
		
		return "redirect:/admin/";
	}
	
	
}
