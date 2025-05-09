package com.chht.srms_dao.service.impl;

import com.chht.srms_dao.domain.project.ProjectDetail;
import com.chht.srms_dao.domain.project.ProjectMembers;
import com.chht.srms_dao.mapper.ProjMapper;
import com.chht.srms_dao.mapper.ProjectMembersMapper;
import com.chht.srms_dao.mapper.UserMapper;
import com.chht.srms_dao.service.ProjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ProjServiceImpl implements ProjService {
    @Autowired
    private ProjMapper projMapper;
    @Autowired
    private ProjectMembersMapper membersMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProjectMembersMapper projectMembersMapper;

    public List<ProjectDetail> getAllProjects() {
        List<ProjectDetail> projects = projMapper.selectAll();
        projects.forEach(p -> p.setMembers(membersMapper.selectByProject(p.getProject_id())));
        return projects;
    }

    public List<ProjectDetail> searchProjectsByDept(Long deptId){
        List<ProjectDetail> projects = projMapper.selectByDepartment(deptId);
        projects.forEach(p -> p.setMembers(membersMapper.selectByProject(p.getProject_id())));
        return projects;
    }

    public List<ProjectDetail> getProjectsByPart(Long userId) {
        List<ProjectDetail> projects=projMapper.selectByParticipant(userId);
        projects.forEach(p -> p.setMembers(membersMapper.selectByProject(p.getProject_id())));
        return projects;
    }

    public List<ProjectDetail> getByStatus(String status){
        List<ProjectDetail> projects = projMapper.selectByStatus(status);
        projects.forEach(p -> p.setMembers(membersMapper.selectByProject(p.getProject_id())));
        return projects;
    }
    public List<ProjectDetail> searchProjects(String keyword) {
        List<ProjectDetail> projects = projMapper.searchProjects(keyword);
        projects.forEach(p -> p.setMembers(membersMapper.selectByProject(p.getProject_id())));
        return projects;
    }

    @Override
    public ProjectDetail createProject(@RequestBody ProjectDetail proj) {
        // 添加空值校验
        if (proj.getTitle() == null || proj.getTitle().isEmpty()) {
            throw new IllegalArgumentException("项目标题不能为空");
        }
        // 添加类型转换安全处理
        try {
            proj.setBudget(proj.getBudget() != null ? Double.parseDouble(proj.getBudget().toString()) : 0.0);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("预算金额格式错误");
        }
        projMapper.insertProject(proj);
        proj.getMembers().forEach(member -> {
            if (userMapper.findByUserId(member.getUser_id()) == null) {
                projMapper.deleteProject(proj.getProject_id());
                throw new IllegalArgumentException("用户ID不存在: " + member.getUser_id());
            }
            member.setProject_id(proj.getProject_id());
            membersMapper.insertMember(member);
        });
        ProjectMembers leader=new ProjectMembers();
        leader.setProject_id(proj.getProject_id());
        leader.setUser_id(proj.getLeader_id());
        leader.setRole("leader");
        membersMapper.insertMember(leader);
        return proj;
    }
    
    @Override
    public ProjectDetail updateProject(Long projectId, ProjectDetail updateProj) {
        ProjectDetail existing = projMapper.selectProjectById(projectId);
        if (existing == null) {
            throw new RuntimeException("Project not found");
        }
        
        // 只更新非空字段
        if (updateProj.getTitle() != null) existing.setTitle(updateProj.getTitle());
        if (updateProj.getDescription() != null) existing.setDescription(updateProj.getDescription());
        if (updateProj.getLeader_id() != null) existing.setLeader_id(updateProj.getLeader_id());
        if (updateProj.getStatus() != null) existing.setStatus(updateProj.getStatus());
        if (updateProj.getLab_id() != null) existing.setLab_id(updateProj.getLab_id());
        if (updateProj.getBudget() != null) existing.setBudget(updateProj.getBudget());
        if (updateProj.getEnd_date() != null) existing.setEnd_date(updateProj.getEnd_date());
        if (updateProj.getStart_date() != null) existing.setStart_date(updateProj.getStart_date());
        if (updateProj.getDepartment_id() != null) existing.setDepartment_id(updateProj.getDepartment_id());
        
        projMapper.updateProject(existing);
        return projMapper.selectProjectById(projectId);
    }

    @Override
    public void deleteProject(Long projectId) {
        // 先删除成员
        membersMapper.deleteByProject(projectId);
        // 再删除项目
        projMapper.deleteProject(projectId);
    }
}