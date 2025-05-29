package com.chht.srms.domain.lab;

import lombok.Data;

import java.sql.Date;

@Data
public class Lab {
    private Long lab_id;
    private Long leader_id;
    private String leader_name;
    private String name;
    private String description;
    private String location;
}
