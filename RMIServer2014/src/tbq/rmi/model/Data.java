package tbq.rmi.model;

import java.util.ArrayList;

public class Data {
	public static ArrayList<User> getData(){
		ArrayList<User> users = new ArrayList<>();
		User user1 = new User("admin", "admin");
		User user2 = new User("system", "system");
		User user3 = new User("user", "user");
		User user4 = new User("tbq", "tbq");
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		return users;
	}
}
