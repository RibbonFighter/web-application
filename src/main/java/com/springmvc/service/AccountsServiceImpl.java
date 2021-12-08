package com.springmvc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springmvc.entity.Accounts;
import com.springmvc.repository.AccountsRepository;

@Service
public class AccountsServiceImpl {
	
	@Autowired
	private AccountsRepository accountRepository;
	
	public List<Accounts> getAllAccounts(){
		return (List<Accounts>) accountRepository.findAll();
		
	}
	
	public void saveAccounts(Accounts accounts) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = encoder.encode(accounts.getPassword());
		accounts.setPassword(password);
		
		accountRepository.save(accounts);
	}
	
	public Accounts getAccountsByUsername(String userName) {
		Optional<Accounts> result = accountRepository.findById(userName);
		return result.get();
	}
	
	public void deleteAccountsByUsername(String userName) {
		accountRepository.deleteById(userName);
	}
	
	public List<Accounts> search(String keyword) {
		return accountRepository.search(keyword);
	}
}
