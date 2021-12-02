package com.springmvc.validator;


import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import com.springmvc.models.CustomerInfo;

@Validated
@Component //tao ra 1 spring bean ten customerValidator trong spring iOC COntainer
public class CustomerInfoValidator implements Validator{

	private EmailValidator emailValidator = EmailValidator.getInstance();
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return CustomerInfo.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		CustomerInfo customerInfo = (CustomerInfo) target;
		
		//kiem tra cac truong (field) cua CustomerInfo.
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.customerForm.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.customerForm.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.customerForm.address");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty.customerForm.phone");
		
		if (!emailValidator.isValid(customerInfo.getEmail())) {
			errors.rejectValue("email", "Pattern.customerForm.email");
		}
	}

}
