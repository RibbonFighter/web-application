package com.springmvc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.springmvc.dao.ProductDao;
import com.springmvc.entity.Products;
import com.springmvc.models.ProductInfo;

@Component
public class ProductInfoValidator implements Validator{

	@Autowired
	private ProductDao productDAO;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return ProductInfo.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ProductInfo productInfo = (ProductInfo) target;
		
		//kiem tra cac truong (field) cua productInfo.
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "NotEmpty.productForm.code");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.productForm.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty.productForm.price");
		
		String code = productInfo.getCode();
		if (code != null && code.length() > 0) {
			if (code.matches("\\s+")) {//
				errors.rejectValue("code", "Pattern.productForm.code");
			} else if (productInfo.isNewProduct()) {
				Products product = productDAO.getProductByCode(code);
				if (product != null) {
					errors.rejectValue("code", "Duplicate.productForm.code");
				}
			} 
		}
	}

}
