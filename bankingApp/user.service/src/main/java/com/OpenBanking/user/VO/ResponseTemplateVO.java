package com.OpenBanking.user.VO;

import com.OpenBanking.user.entity.User;


import lombok.Data;

@Data
public class ResponseTemplateVO {
	
	private User user;
	private Account account;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	


}
