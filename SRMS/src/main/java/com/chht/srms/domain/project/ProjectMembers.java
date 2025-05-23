package com.chht.srms.domain.project;

import lombok.Data;

@Data
public class ProjectMembers {
    private Long project_id;
    private Long user_id;
    private String username;
    private String role;
}