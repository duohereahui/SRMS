package com.chht.srms.mapper;

import com.chht.srms.domain.project.ProjectMembers;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface ProjectMembersMapper {
    public class ProjectMembersSqlProvider {
        // 插入项目成员
        public String insertMembersSQL() {
            return "INSERT INTO project_members(project_id, per_id, role) VALUES(#{project_id}, #{per_id}, #{role})";
        }

        // 删除项目所有成员
        public String deleteByProjectSQL() {
            return "DELETE FROM project_members WHERE project_id = #{projectId}";
        }

        // 删除指定成员
        public String deleteMemberSQL() {
            return "DELETE FROM project_members WHERE project_id = #{projectId} AND per_id = #{perId}";
        }

        // 更新成员角色
        public String updateMemberRoleSQL() {
            return "UPDATE project_members SET role = #{role} WHERE project_id = #{projectId} AND per_id = #{perId}";
        }

        // 查询项目成员
        public String selectByProjectSQL() {
            return new SQL() {{
                SELECT("pm.project_id, pm.per_id, p.name, pm.role");
                FROM("project_members pm");
                LEFT_OUTER_JOIN("person p ON pm.per_id = p.per_id");
                WHERE("pm.project_id = #{projectId}");
            }}.toString();
        }
    }

    @InsertProvider(type = ProjectMembersSqlProvider.class, method = "insertMembersSQL")
    void insertMember(ProjectMembers member);

    @DeleteProvider(type = ProjectMembersSqlProvider.class, method = "deleteByProjectSQL")
    void deleteByProject(@Param("projectId") String projectId);

    @DeleteProvider(type = ProjectMembersSqlProvider.class, method = "deleteMemberSQL")
    void deleteMember(@Param("projectId") String projectId, @Param("perId") Long perId);

    @UpdateProvider(type = ProjectMembersSqlProvider.class, method = "updateMemberRoleSQL")
    void updateMemberRole(@Param("projectId") String projectId, @Param("perId") Long perId, @Param("role") String role);

    @SelectProvider(type = ProjectMembersSqlProvider.class, method = "selectByProjectSQL")
    List<ProjectMembers> selectByProjectId(@Param("projectId") String projectId);
}