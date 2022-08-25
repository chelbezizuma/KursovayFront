package com.example.kursovayadada;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private String login;
    private String password;
    private String role;
    private String name;
}
