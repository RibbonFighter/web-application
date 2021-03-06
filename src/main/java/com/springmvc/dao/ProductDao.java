package com.springmvc.dao;


import java.util.List;

import org.springframework.data.repository.query.Param;

import com.springmvc.entity.Products;
import com.springmvc.models.PaginationResult;
import com.springmvc.models.ProductInfo;

public interface ProductDao {
	
	public PaginationResult<ProductInfo> getAllProductInfos(int page, int maxResult, int maxNavigationPage, String likeName);
	
	public Products getProductByCode(String code);

	public ProductInfo getProductInfoByCode(String code);

	public void saveProductInfo(ProductInfo productInfo);
	
	public boolean deleteProductByCode(String code);
	
	//public List<Products> search(@Param("keyword") String keyword);
	
}
