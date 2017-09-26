package com.opinta.model;

import java.io.Serializable;

public class MessageDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String email;
    private String text;

    public MessageDto() {
    }

    public MessageDto(int statusCode, String email, String text) {
        this.id = id;
        this.email = email;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
