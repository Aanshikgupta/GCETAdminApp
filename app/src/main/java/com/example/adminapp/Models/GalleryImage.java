package com.example.adminapp.Models;

public class GalleryImage {
    String key,imageUrl;

    public GalleryImage() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public GalleryImage(String key, String imageUrl) {
        this.key = key;
        this.imageUrl = imageUrl;
    }
}
