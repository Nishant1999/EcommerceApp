package com.example.ecommerce.model;

public class User {

    private String userId;
    private String userName;
    private String name;
    private String email;
    private String password;
    private String userBio;
    private String imageUrl;

    public User(String userId, String userName, String name, String email, String password, String userBio, String imageUrl) {
        this.userId = userId;
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userBio = userBio;
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
