package com.example.orgo.homepage;

public class DiscussionTile {
    int id;
    String userEmail;
    String content;

    public DiscussionTile(int id, String userEmail, String content) {
        this.id = id;
        this.userEmail = userEmail;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
