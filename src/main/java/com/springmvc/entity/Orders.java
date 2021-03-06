package com.springmvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "orders")
public class Orders implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", length = 50)
	private String id;

	@Column(name = "order_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date orderDate;

	@Column(name = "order_number", nullable = false)
	private int orderNum;

	@Column(name = "amount", nullable = false)
	private double amount;

	@Column(name = "customer_name", length = 255, nullable = false)
	private String customerName;

	@Column(name = "customer_address", length = 255, nullable = false)
	private String customerAddress;

	@Column(name = "customer_email", length = 128, nullable = false)
	private String customerEmail;

	@Column(name = "customer_phone", length = 128, nullable = false)
	private String customerPhone;

	public Orders(String id, Date orderDate, int orderNum, double amount, String customerName,
			String customerAddress, String customerEmail, String customerPhone) {
		super();
		this.id = id;
		this.orderDate = orderDate;
		this.orderNum = orderNum;
		this.amount = amount;
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
	}

	public Orders() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

}
