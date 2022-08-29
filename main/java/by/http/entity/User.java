package by.http.entity;

public class User {
	private String Username;
	private String Password;
	private int Role;
	private int id;
	
	public User(String name, String Surname, int role, int id) {
		this.Username = name;
		this.Password = Surname;
		this.Role = role;
		this.id = id;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public int getRole() {
		return Role;
	}

	public void setRole(int role) {
		Role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
