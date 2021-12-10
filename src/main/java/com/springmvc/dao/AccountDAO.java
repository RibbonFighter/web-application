package com.springmvc.dao;


import com.springmvc.entity.Accounts;
import com.springmvc.models.AccountInfo;
import com.springmvc.models.PaginationResult;

public interface AccountDAO {
	public Accounts getAccountByUsername(String userName);
	
	public PaginationResult<AccountInfo> getAllAccountInfos(int page, int maxResult, int maxNavigationPage);

	public void saveAccountInfo(AccountInfo accountInfo);

	public boolean deleteAccountByUsername(String userName);
}
