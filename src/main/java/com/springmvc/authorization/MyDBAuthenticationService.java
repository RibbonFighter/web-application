package com.springmvc.authorization;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springmvc.dao.AccountDAO;
import com.springmvc.entity.Accounts;

@Service
public class MyDBAuthenticationService implements UserDetailsService{

	@Autowired
	private AccountDAO accountDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Accounts account = accountDAO.getAccountByUsername(username);
		System.out.println("Account = " + account);
		
		if (account == null) {
			throw new  UsernameNotFoundException("User " + username + " doesn't exist !");
		}
		
		//EMPLOYEE - MANAGER
		String role = account.getUserRole();
		
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		
		//ROLE_EMPLOYEE, ROLE_MANAGER
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role); //ADD ROLE MANAGER-EMP
		grantList.add(authority); // ADD VO CAI LIST
		
		UserDetails userDetails = (UserDetails) new User(account.getUserName(), account.getPassword(), grantList);
		return userDetails;
	}

}
