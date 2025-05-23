package com.chht.srms.domain.project;

import lombok.Data;

import java.util.List;
//展示类
@Data
public class ProjectDetail extends Projects {

    private String leader_name;
    private List<ProjectMembers> members;

//    public class ProjectMembers {
//        private Long project_id;
//        private Long user_id;
//        private String username;
//        private String role;
//    }
}