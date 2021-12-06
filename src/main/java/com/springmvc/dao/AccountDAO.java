package com.springmvc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springmvc.entity.Accounts;

public interface AccountDAO {
	public Accounts getAccountByUsername(String userName);
	
	@Query(value = "SELECT a FROM Accounts a WHERE a.user_name LIKE '%' || :keyword || '%'"
			+ "OR a.user_Role LIKE '%' || :keyword || '%'" )
	public List<Accounts> search(@Param("keyword") String keyword);
}
