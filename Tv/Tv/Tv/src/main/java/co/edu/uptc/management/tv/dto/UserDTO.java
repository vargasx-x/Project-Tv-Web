package co.edu.uptc.management.library.dto;

public class UserDTO {
	private String userName;
	private String password;

	public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
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

	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + "]";
	}

}
