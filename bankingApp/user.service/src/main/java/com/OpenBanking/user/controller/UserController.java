package com.OpenBanking.user.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OpenBanking.user.VO.ResponseTemplateVO;
import com.OpenBanking.user.entity.User;
import com.OpenBanking.user.service.UserService;


import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/users")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	
	public User saveUser(@RequestBody User user) {
		return userService.saveUser(user);
	}

	@GetMapping("/{id}")
	
		public ResponseTemplateVO getUserWithAccount(@PathVariable("id") Long userId) {
		return userService.getUserWithAccount(userId);
	}
	
	@GetMapping("/users/")
	
	public List<User> getAllUser(){
		
		return userService.getAllUser();
		
	}
	
@GetMapping("/home")
	
	public String viewAllAccount(Model model) {
	  model.addAttribute("user",userService.getAllUser());
		return "home";	
}
}