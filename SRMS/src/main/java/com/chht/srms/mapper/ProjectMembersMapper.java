package com.chht.srms.mapper;

import com.chht.srms.domain.project.ProjectMembers;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProjectMembersMapper {
    @Select("SELECT project_id, p.user_id, username,p.role FROM project_members p LEFT JOIN user u ON p.user_id = u.user_id WHERE p.project_id = #{projectId}")
    List<ProjectMembers> selectByProject(@Param("projectId") Long projectId);

    @Insert("INSERT INTO project_members(project_id, user_id, role) VALUES(#{project_id}, #{user_id}, #{role})")
    void insertMember(ProjectMembers member);

    @Delete("DELETE FROM project_members WHERE project_id = #{project_id} AND user_id = #{user_id}")
    void deleteMember(@Param("projectId") Long projectId, @Param("userId") Long userId);

    @Delete("DELETE FROM project_members WHERE project_id = #{projectId}")
    void deleteByProject(Long projectId);
}