package com.example.socialandroid.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

public class EditProfile {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("is_men")
    @Expose
    private Boolean isMen;

    @SerializedName("about")
    @Expose
    private String about;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("birth_date")
    @Expose
    private String birthDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsMen() {
        return isMen;
    }

    public void setIsMen(Boolean isMen) {
        this.isMen = isMen;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public EditProfile(String status, Boolean sisMen, String about, String image, String birthDate) {
        this.status = status;
        this.isMen = sisMen;
        this.about = about;
        this.image = image;
        this.birthDate = birthDate;
    }
}
