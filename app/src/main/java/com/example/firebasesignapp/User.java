package com.example.firebasesignapp;

public class User {

    public String name;
    public String email;
    public String userId;

    public User() {
    }

    public User(String userId, String name, String email) {
        this.name = name;
        this.email = email;
        this.userId = userId;
    }

}
