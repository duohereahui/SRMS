package com.chht.srms.domain.person;

import lombok.Data;

import java.util.Date;
@Data
public class Person {
    private Integer per_id;
    private String name;
    private String phone;
    private String email;
    private String role;
    private String teacher_id;
    private String lab_id;
    private String description;
    private String lab_name;
}
