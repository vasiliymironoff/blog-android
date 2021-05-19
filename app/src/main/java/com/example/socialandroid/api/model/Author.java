package com.example.socialandroid.api.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Author {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("image")
    @Expose
    private Bitmap image;

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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

}
