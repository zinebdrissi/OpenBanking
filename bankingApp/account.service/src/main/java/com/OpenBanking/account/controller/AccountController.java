package com.OpenBanking.account.controller;

import java.security.Principal;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OpenBanking.account.entity.Account;
import com.OpenBanking.account.repository.AccountRepository;
import com.OpenBanking.account.service.AccountService;


import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;
	private HttpServletRequest request;
	
	
	
		
	@PostMapping("/")
	@PermitAll
	public Account saveAccount(@RequestBody Account account) {
		return accountService.saveAccount(account);
		
	}
	
	@GetMapping("/")
	@PermitAll
    public String index(){
        return "index";
    }
	
	@GetMapping("/{id}")
	
	public Account findAccountById(@PathVariable("id") Long accountId) {
		return accountService.findAccountById(accountId);
	}
	
	@GetMapping("/users/{id}")
	
	public Account findAccountByUserId(@PathVariable("id") Long userId) {
		return accountService.findAccountByUserId(userId);
	}
	
	
	@GetMapping("/all")
	
	public List<Account> getAllAccount(){
		
		return accountService.getAllAccount();
		
	}
	


	@GetMapping("/home")
	@RolesAllowed({"user" , "admin"})
	public String viewAllAccount(Model model) {
	  model.addAttribute("account",accountService.getAllAccount());
		return "home";	
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request ) throws ServletException {
		request.logout();
		return "redirect:/";
	}
	
	}
