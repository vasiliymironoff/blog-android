package com.example.socialandroid.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileDetail {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("about")
    @Expose
    private String about;

    @SerializedName("birth_date")
    @Expose
    private String birthDate;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("posts")
    @Expose
    private List<PostForProfile> posts = null;

    @SerializedName("is_men")
    @Expose
    private Boolean isMen;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<PostForProfile> getPosts() {
        return posts;
    }

    public void setPosts(List<PostForProfile> posts) {
        this.posts = posts;
    }

    public Boolean getIsMen() {
        return isMen;
    }

    public void setIsMen(Boolean isMen) {
        this.isMen = isMen;
    }
}
