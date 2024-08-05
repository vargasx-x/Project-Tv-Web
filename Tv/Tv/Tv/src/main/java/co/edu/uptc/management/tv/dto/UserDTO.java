package co.edu.uptc.management.tv.dto;

public class UserDTO {
    private String nameUser;
    private String password;

    // Constructor
    public UserDTO(String nameUser, String password) {
        this.nameUser = nameUser;
        this.password = password;
    }

    // Getters y setters
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
}
