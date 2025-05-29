package com.chht.srms.domain.project;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
public class ProjectOutputs{

    private String type;  //国家级 省级 市厅 横向 教育
    private String project_id;
    private String title;
    private String proj_type;

    private String leader_id;
    private String leader_name;  //需要连接查找

    private Double funds;
    private Date start_date;
    private Date end_date;
    private String source;
    private String description;

    private Long lab_id;
    private String lab_name;    //需要连接查找


    private List<ProjectMembers> members;

}