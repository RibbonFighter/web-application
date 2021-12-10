package com.springmvc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.springmvc.dao.AccountDAO;
import com.springmvc.entity.Accounts;
import com.springmvc.models.AccountInfo;
import com.springmvc.models.CustomerInfo;

@Component
public class AccountsInfoValidator implements Validator{

	@Autowired
	private AccountDAO accountDAO;
	private Accounts account;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return CustomerInfo.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		AccountInfo accountInfo = (AccountInfo) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.accountForm.userName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.accountForm.password");

		String username = accountInfo.getUserName();
		account = accountDAO.getAccountByUsername(username);
		if(username.equals(account.getUserName())) {
			errors.rejectValue("username", "Duplicate.accountForm.userName");
		}
	}
	

}
