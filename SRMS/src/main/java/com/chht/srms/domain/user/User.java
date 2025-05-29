package com.chht.srms.domain.user;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer user_id;
    private String username;
    private String password;
}