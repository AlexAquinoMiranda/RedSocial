package com.example.redsocial.dto;

public class History {
    private String imageUrl;
    private String userImageUrl;
    private String username;

    public History() {
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "History{" +
                "imageUrl='" + imageUrl + '\'' +
                ", userImageUrl='" + userImageUrl + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
