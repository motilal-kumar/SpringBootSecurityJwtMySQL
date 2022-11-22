package com.neosoft.springboot.security.jwt.mysql.model;

import lombok.Data;

@Data
public class UserRequest {

    private String username;
    private String password;
}
