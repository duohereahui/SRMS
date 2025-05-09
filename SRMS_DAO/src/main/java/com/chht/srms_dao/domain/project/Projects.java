package com.chht.srms_dao.domain.project;

import java.util.Date;

import lombok.Data;

@Data
public class Projects {
    private Long project_id;
    private String title;
    private String description;
    private Long leader_id;
    private String status;
    private Date start_date;
    private Date end_date;
    private String actual_ent_time;
    private Double budget;
    private Double actual_funds;
    private Long department_id;
    private Long lab_id;
}