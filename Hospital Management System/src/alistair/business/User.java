package alistair.business;

public class User {
    private int id;
    private String password;
    private int role;

    public User() {
    }

    public User(int id, String password, int role) {
        this.id = id;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
