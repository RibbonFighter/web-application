package com.springmvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springmvc.entity.Products;

public interface ProductsRepository {
	
	@Query(value = "SELECT PRO FROM Products PRO WHERE PRO.code LIKE '%' || :keyword || '%'"
			+ "OR PRO.name LIKE '%' || :keyword || '%'"
			+ "OR PRO.price LIKE '%' || :keyword || '%'")
	public List<Products> search(@Param("keyword") String keyword);
}
