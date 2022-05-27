package com.epam.billing.entity;

public class User {

    private int userId;
    private String name;
    private boolean admin;
    private String email;
    private String password;


    @Override
    public String toString() {
        String status;
        if (admin) {
            status = " Администратор";
        } else {
            status = " Пользователь";
        }

        return userId + ". " +
                name + " " +
                email + " " +
                status;
    }

    public int getUserId() {
        return userId;
    }

    public User setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isAdmin() {
        return admin;
    }

    public User setAdmin(boolean admin) {
        this.admin = admin;
        return this;
    }


    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

}
