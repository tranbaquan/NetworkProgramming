package tbq.rmi.data;

import java.util.ArrayList;

import tbq.rmi.model.User;

public class Data {
	public static ArrayList<User> createData() {
		ArrayList<User> data = new ArrayList<>();
		User user1 = new User("abc", "abc");
		User user2 = new User("xyz", "abc");
		User user3 = new User("Quan", "abc");
		User user4 = new User("mmm", "abc");
		data.add(user1);
		data.add(user2);
		data.add(user3);
		data.add(user4);
		return data;
	}
}
