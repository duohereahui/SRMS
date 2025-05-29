package com.chht.srms.domain.project;

import lombok.Data;

@Data
public class ProjectMembers {
    private String project_id;
    private Long per_id;
    private String name;
    private String role;
}