package com.springmvc.dao;

import com.springmvc.entity.Accounts;

public interface AccountDAO {
	public Accounts getAccountByUsername(String userName);
	
	
}
