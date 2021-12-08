package com.springmvc.validator;

import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springmvc.dao.AccountDAO;

@Component
public class AccountsInfoValidator implements Validator{

	@Autowired
	private AccountDAO accountDAO;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}
	

}
