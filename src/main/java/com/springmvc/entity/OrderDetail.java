package com.springmvc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="Order_Details")
public class OrderDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", length = 50, nullable = false)
	private String id;
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "ORDER_ID" , nullable = false, foreignKey = @ForeignKey(name = "ORDER_DETAIL_ORD_FK"), updatable = true, insertable = true)
	private Orders order;

	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "PRODUCT_ID" , nullable = false, foreignKey = @ForeignKey(name = "ORDER_DETAIL_PROD_FK"), updatable = true, insertable = true)
	private Products product;
	
	@Column(name = "Quantity", nullable = false)
	private int quantity;
	
	@Column(name = "Price", nullable = false)
	private double price;
	
	@Column(name = "Amount", nullable = false)
	private double amount;

	public OrderDetail() {
		
	}

	public OrderDetail(String id, Orders order, Products product, int quantity, double price, double amount) {
		this.id = id;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	
	
}
