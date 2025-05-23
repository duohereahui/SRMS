package com.chht.srms.domain.lab;

import lombok.Data;

import java.sql.Date;

@Data
public class lab {
    private Long lab_id;
    private String name;
    private String description;
    private String location;
    private Long department_id;
}
