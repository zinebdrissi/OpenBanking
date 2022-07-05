package com.OpenBanking.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.OpenBanking.user.VO.Account;
import com.OpenBanking.user.VO.ResponseTemplateVO;
import com.OpenBanking.user.entity.User;
import com.OpenBanking.user.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public ResponseTemplateVO getUserWithAccount(Long userId) {
		
		ResponseTemplateVO vo = new ResponseTemplateVO();
		User user = userRepository.findByUserId(userId);
		Account account = 
				restTemplate.getForObject("http://ACCOUNT-SERVICE/accounts/users/" + user.getUserId()
				,Account.class);
		
	
		vo.setUser(user);
		vo.setAccount(account);
		
		return vo;
	}

	public List<User> getAllUser() {
		
		List<User> user1 = userRepository.findAll();
		
	
		return user1;
	}

}
