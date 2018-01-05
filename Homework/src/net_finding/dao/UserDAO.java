package net_finding.dao;

import java.util.*;

import net_finding.model.User;

public class UserDAO {
	List<User> usersData;

	public UserDAO() {
		setVirtualData();
	}

	private void setVirtualData() {
		User user1 = new User("admin", "admin");
		User user2 = new User("system", "system");
		User user3 = new User("user", "user");
		usersData = new ArrayList<>();
		usersData.add(user1);
		usersData.add(user2);
		usersData.add(user3);
	}

	public boolean findUsername(String username) {
		for (User user : usersData) {
			if (user.getUsername().equals(username))
				return true;
		}
		return false;
	}
	public boolean checkLogin(String username, String password) {
		for (User user : usersData) {
			if (user.checkLogin(username, password))
				return true;
		}
		return false;
	}
}
