package tbq.rmi.model;

public class User {
	private String name;
	private String pass;

	public User() {
	}

	public User(String name, String pass) {
		super();
		this.name = name;
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
