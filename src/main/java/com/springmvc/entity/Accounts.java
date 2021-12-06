package com.springmvc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class Accounts implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static final String ROLE_MANAGER = "MANAGER";
	private static final String ROLE_EMPLOYEE = "EMPLOYEE";
	
	@Id
	@Column(name = "user_name", length = 20, nullable = false)
	private String userName;
	
	@Column(name = "password", length = 20, nullable = false)
	private String password;
	
	@Column(name = "enabled", length = 1, nullable = false)
	private String enabled;
	
	@Column(name = "user_Role", length = 20, nullable = false)
	private String userRole;

	public Accounts(String userName, String password, String enabled, String userRole) {
		super();
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
		this.userRole = userRole;
	}

	public Accounts() {
		super();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	@Override
	public String toString() {
		return this.userRole;
	}

	
}
