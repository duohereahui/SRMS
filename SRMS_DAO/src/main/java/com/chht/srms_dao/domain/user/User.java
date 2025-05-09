package com.chht.srms_dao.domain.user;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer user_id;
    private String phone;
    private String department_id;
    private String lab_id;
    private String teacher_id;
    private String email;
    private String username;
    private String password;
    private String role;
    private Date created_at;

    private String description;
}