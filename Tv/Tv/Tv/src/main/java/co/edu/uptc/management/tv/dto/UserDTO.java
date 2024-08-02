package co.edu.uptc.management.library.dto;

public class UserDTO {
	private String nameUser;
	private String password;
	
	public UserDTO() {
		super();
	}
	
	public UserDTO(String nameUser, String password) {
		super();
		this.nameUser = nameUser;
		this.password = password;
	}

	public String getNameUser() {
		return nameUser;
	}
	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDTO [nameUser=" + nameUser + ", password=" + password + "]";
	}
	
}
