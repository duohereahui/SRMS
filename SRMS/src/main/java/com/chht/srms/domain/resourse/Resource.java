package com.chht.srms.domain.resourse;

import lombok.Data;

import java.util.Date;

@Data
public class Resource {
    private Long resource_id;
    private String name;
    private String location;
    private String type;  //equipment,consumables,software
    private String status;
    private Date purchase_date;
    private Long lab_id;
}