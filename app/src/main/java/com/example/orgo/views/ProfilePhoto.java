package com.example.orgo.views;

public class ProfilePhoto {
    String image;
    String email;

    void setImage(String imageInput) {
        image = imageInput;
    }

    void setEmail(String emailInput) {
        email = emailInput;
    }

    public String getImage() {
        return image;
    }

    public String getEmail() {
        return email;
    }
}
