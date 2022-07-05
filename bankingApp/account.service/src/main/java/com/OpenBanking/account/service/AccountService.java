package com.OpenBanking.account.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OpenBanking.account.entity.Account;
import com.OpenBanking.account.repository.AccountRepository;

@Service
public class AccountService {

	private static List<Account> Accounts = new ArrayList<>();

	static {

	}

	@Autowired
	private AccountRepository accountRepository;

	
	public Account saveAccount(Account account) {

		return accountRepository.save(account);
	}

	public Account findAccountById(Long accountId) {

		return accountRepository.findByAccountId(accountId);

	}

	public Account findAccountByUserId(Long userId) {

		return accountRepository.findByUserId(userId);
	}

	public List<Account> getAllAccount() {
		List<Account> account = accountRepository.findAll();

		return account;
	}

}
