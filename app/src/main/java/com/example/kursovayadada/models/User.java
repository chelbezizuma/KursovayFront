package com.example.kursovayadada.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private int id;
    private String group_id;
    private String name;
    private String role;
    private String login;
    private String password;

    public User() {
    }
}
