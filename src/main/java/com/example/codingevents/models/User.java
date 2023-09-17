package com.example.codingevents.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class User extends AbstractEntity {

    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    public User() {}

    public User(String username, String pwHash) {
        this.username = username;
        this.pwHash = pwHash;
    }

    public String getUsername() {
        return username;
    }
}
