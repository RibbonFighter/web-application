package com.springmvc.models;

public class AccountInfo {
	private String userName;

	private String password;

	private boolean enabled;

	private String userRole;

	public AccountInfo() {
		super();
	}

	public AccountInfo(String userName, String password, boolean enabled, String userRole) {
		super();
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
		this.userRole = userRole;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	
	
}
