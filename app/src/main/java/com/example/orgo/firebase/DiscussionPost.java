package com.example.orgo.firebase;

import com.google.firebase.auth.FirebaseAuth;

public class DiscussionPost {
    private int postId;
    private String postMessage;
    private String userEmail;
    private FirebaseAuth auth;

    /**
     * Empty construtor. Uses current logged in user.
     */
    DiscussionPost() {
        auth = FirebaseAuth.getInstance();
        this.postId = 0;
        this.postMessage = "";
        this.userEmail = auth.getCurrentUser().getEmail();
    }

    /**
     * Standard constructor, uses logged in user and takes in a post ID and postMessage.
     * @param postId The ID of the current post.
     * @param postMessage The message to be posted.
     */
    DiscussionPost(int postId, String postMessage) {
        auth = FirebaseAuth.getInstance();
        this.postId = postId;
        this.postMessage = postMessage;
        this.userEmail = auth.getCurrentUser().getEmail();
    }

    // Getters
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostMessage() {
        return postMessage;
    }

    // Setters
    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
