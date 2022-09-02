package com.example.kursovayadada.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private int id;
    private String groupId;
    private String name;
    private String role;
    private String login;
    private String password;
}
