package com.example.socialandroid.api.model;

import androidx.annotation.RequiresPermission;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("sender")
    @Expose
    private int sender;

    @SerializedName("recipient")
    @Expose
    private int recipient;

    public boolean my;

    public String senderUsername;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getRecipient() {
        return recipient;
    }

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }

    public Message(String text, String image, int sender, int recipient) {
        this.text = text;
        this.image = image;
        this.sender = sender;
        this.recipient = recipient;
    }
}
