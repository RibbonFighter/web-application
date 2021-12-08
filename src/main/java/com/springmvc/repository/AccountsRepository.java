package com.springmvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.springmvc.entity.Accounts;

public interface AccountsRepository extends CrudRepository<Accounts, String>{
	
	@Query(value = "SELECT ACC FROM Accounts ACC WHERE ACC.userName LIKE '%' || :keyword || '%'"
			+ "OR ACC.userRole LIKE '%' || :keyword || '%'" )
	public List<Accounts> search(@Param("keyword") String keyword);

	@Query("SELECT ACC FROM Accounts ACC WHERE ACC.userName = :USERNAME")
	public Accounts getAccountByUsername(@Param("userName") String userName);
}
