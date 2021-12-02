package com.springmvc.models;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.springmvc.entity.Products;

public class ProductInfo {
	
	private String code;
	
	private String name;
	
	private double price;
	
	private boolean newProduct = false;
	
	//upload file
	private CommonsMultipartFile fileData;

	public ProductInfo() {
		super();
	}
	
	public ProductInfo(Products product) {
		this.code = product.getCode();
		this.name = product.getName();
		this.price = product.getPrice();
	}

	public ProductInfo(String code, String name, double price) { //S01, JAVA , 5522
		super();
		this.code = code;
		this.name = name;
		this.price = price;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isNewProduct() {
		return newProduct;
	}

	public void setNewProduct(boolean newProduct) {
		this.newProduct = newProduct;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	
	
	
	
}
