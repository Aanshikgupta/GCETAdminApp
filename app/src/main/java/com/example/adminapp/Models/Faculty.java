package com.example.adminapp.Models;

public class Faculty {
    String key,facultyName,facultyEmail,facultyPost,facultyDepartment,imageUrl;

    public Faculty() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyEmail() {
        return facultyEmail;
    }

    public void setFacultyEmail(String facultyEmail) {
        this.facultyEmail = facultyEmail;
    }

    public String getFacultyPost() {
        return facultyPost;
    }

    public void setFacultyPost(String facultyPost) {
        this.facultyPost = facultyPost;
    }

    public String getFacultyDepartment() {
        return facultyDepartment;
    }

    public void setFacultyDepartment(String facultyDepartment) {
        this.facultyDepartment = facultyDepartment;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Faculty(String key, String facultyName, String facultyEmail, String facultyPost, String facultyDepartment, String imageUrl) {
        this.key = key;
        this.facultyName = facultyName;
        this.facultyEmail = facultyEmail;
        this.facultyPost = facultyPost;
        this.facultyDepartment = facultyDepartment;
        this.imageUrl = imageUrl;
    }
}
