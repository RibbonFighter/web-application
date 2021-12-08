package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.entity.Products;
import com.springmvc.repository.ProductsRepository;

@Service
public class ProductServiceImpl {
	
	@Autowired
	private ProductsRepository productRepository;
	
	public List<Products> search(String keyword) {
		return productRepository.search(keyword);
	}
}
