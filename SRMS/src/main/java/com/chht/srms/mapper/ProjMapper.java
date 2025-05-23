package com.chht.srms.mapper;

import com.chht.srms.domain.project.ProjectDetail;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface ProjMapper {
    public class ProjectSqlProvider {
        public String insertProjectDynamic(ProjectDetail project) {
            return new SQL() {{
                INSERT_INTO("projects");
                if (project.getTitle() != null) VALUES("title", "#{title}");
                if (project.getDescription() != null) VALUES("description", "#{description}");
                if (project.getLeader_name() != null) VALUES("leader_name", "#{leader_name}");
                if(project.getLeader_id() != null) VALUES("leader_id", "#{leader_id}");
//                if (project.getMembers() != null) VALUES("members", "#{members}");
                if (project.getStatus() != null) VALUES("status", "#{status}");
                if (project.getLab_id() != null) VALUES("lab_id", "#{lab_id}");
                if (project.getBudget() != null ) VALUES("budget", "#{budget}");
                if(project.getEnd_date() != null) VALUES("end_date", "#{end_date}");
                if (project.getStart_date() != null) VALUES("start_date", "#{start_date}");
                if(project.getDepartment_id() != null) VALUES("department_id", "#{department_id}");
            }}.toString();
        }
        public String updateProjectDynamic(ProjectDetail project) {
            return new SQL() {{
                UPDATE("projects");
                if (project.getTitle() != null) SET("title = #{title}");
                if (project.getDescription() != null) SET("description = #{description}");
                if (project.getLeader_id() != null) SET("leader_id = #{leader_id}");
                if (project.getStatus() != null) SET("status = #{status}");
                if (project.getLab_id() != null) SET("lab_id = #{lab_id}");
                if (project.getBudget() != null) SET("budget = #{budget}");
                if (project.getEnd_date() != null) SET("end_date = #{end_date}");
                if (project.getStart_date() != null) SET("start_date = #{start_date}");
                if (project.getDepartment_id() != null) SET("department_id = #{department_id}");
                WHERE("project_id = #{project_id}");
            }}.toString();
        }
    }



    @Select("SELECT p.*, u.username as leader_name, p.lab_id FROM projects p LEFT JOIN user u ON p.leader_id = u.user_id order by project_id DESC")
    List<ProjectDetail> selectAll();
    
    @Select("SELECT p.*, u.username as leader_name FROM projects p LEFT JOIN user u ON p.leader_id = u.user_id WHERE status = #{status} order by project_id DESC")
    List<ProjectDetail> selectByStatus(String status);
    
    @Select("SELECT p.*, u.username as leader_name FROM projects p LEFT JOIN user u ON p.leader_id = u.user_id WHERE p.department_id = #{departmentId} order by project_id DESC")
    List<ProjectDetail> selectByDepartment(Long departmentId);
    
    @Select("SELECT DISTINCT p.* , u.username as leader_name FROM projects p " +
            "LEFT JOIN project_members pm ON p.project_id = pm.project_id " +
            "LEFT JOIN user u ON pm.user_id = u.user_id " +
            "WHERE p.title LIKE CONCAT('%',#{keyword},'%') " +
            "OR p.description LIKE CONCAT('%',#{keyword},'%') " +
            "OR u.username LIKE CONCAT('%',#{keyword},'%')")
    List<ProjectDetail> searchProjects(String keyword);


    @Select("SELECT DISTINCT p.* , u.username as leader_name FROM projects p " +
            "LEFT JOIN project_members pm ON p.project_id = pm.project_id " +
            "LEFT JOIN user u ON pm.user_id = u.user_id " +
            "where pm.user_id=#{userId} order by project_id DESC")
    List<ProjectDetail> selectByParticipant(Long userId);




    @InsertProvider(type = ProjectSqlProvider.class, method = "insertProjectDynamic")
    @Options(useGeneratedKeys = true, keyProperty = "project_id")
    void insertProject(ProjectDetail project);



    @Select("SELECT * FROM projects WHERE project_id = #{projectId} order by project_id DESC" )
    ProjectDetail selectProjectById(Long projectId);
    
    @UpdateProvider(type = ProjectSqlProvider.class, method = "updateProjectDynamic")
    void updateProject(ProjectDetail project);

    @Delete("DELETE FROM projects WHERE project_id = #{projectId}")
    void deleteProject(Long projectId);
}