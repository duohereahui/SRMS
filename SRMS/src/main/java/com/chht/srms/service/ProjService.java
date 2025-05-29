package com.chht.srms.service;

import com.chht.srms.domain.project.ProjectOutputs;

import java.util.List;

public interface ProjService {
    List<ProjectOutputs> getAllProjects();

    List<ProjectOutputs> getProjectsByPart(Long userId);

    List<ProjectOutputs> getByStatus(String status);

    List<ProjectOutputs> searchProjects(String keyword);

    List<ProjectOutputs> searchProjectsByDept(Long deptId);

    ProjectOutputs createProject(ProjectOutputs project);

    Object updateProject(Long projectId, ProjectOutputs updateProj);
    
    void deleteProject(Long projectId);
}
