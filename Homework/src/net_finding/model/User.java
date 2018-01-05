package net_finding.model;

public class User {
	private String username;
	private String password;
	
	public User() {
		this.username = "";
		this.password = "";
	}
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean checkLogin(String username, String password) {
		return this.username.equals(username) && this.password.equals(password);
	}

}
