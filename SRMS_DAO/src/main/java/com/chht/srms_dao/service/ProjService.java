package com.chht.srms_dao.service;

import com.chht.srms_dao.domain.project.ProjectDetail;

import java.util.List;

public interface ProjService {
    List<ProjectDetail> getAllProjects();

    List<ProjectDetail> getProjectsByPart(Long userId);

    List<ProjectDetail> getByStatus(String status);

    List<ProjectDetail> searchProjects(String keyword);

    List<ProjectDetail> searchProjectsByDept(Long deptId);

    ProjectDetail createProject(ProjectDetail project);

    Object updateProject(Long projectId, ProjectDetail updateProj);
    
    void deleteProject(Long projectId);
}
