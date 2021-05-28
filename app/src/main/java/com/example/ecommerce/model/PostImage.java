package com.example.ecommerce.model;

import java.util.ArrayList;

public class PostImage {

    private String postId;
    private String imageUrl;
    private String description;
    private String userId;
    private ArrayList<String> hashTags;

    public PostImage(){

    }

    public PostImage(String postId, String imageUrl, String description, String userId, ArrayList<String> hashTags) {
        this.postId = postId;
        this.imageUrl = imageUrl;
        this.description = description;
        this.userId = userId;
        this.hashTags = hashTags;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getHashTags() {
        return hashTags;
    }

    public void setHashTags(ArrayList<String> hashTags) {
        this.hashTags = hashTags;
    }
}
