package com.chht.srms.mapper;

import com.chht.srms.domain.project.ProjectOutputs;

import io.micrometer.common.util.StringUtils;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface ProjMapper {
    public class ProjectSqlProvider {
        public String selectByPageSql(Integer page, Integer displayNum) {
            return new SQL() {{
                SELECT("p.*, per.name as leader_name, l.name as lab_name");
                FROM("projects p");
                LEFT_OUTER_JOIN("person per ON p.leader_id = per.per_id");
                LEFT_OUTER_JOIN("lab l ON p.lab_id = l.lab_id");
                if (page!= null && displayNum!= null) {
                    String limitOffset = String.format("LIMIT %d OFFSET %d", displayNum, (page - 1) * displayNum);
                    OFFSET_ROWS(limitOffset);
                }
            }}.toString();            
        }
        public String insertProject(ProjectOutputs project) {
            return new SQL() {{
                INSERT_INTO("projects");
                if (project.getType() != null) VALUES("type", "#{type}");

                if (project.getProject_id() != null) VALUES("project_id", project.getProject_id());
                if (project.getTitle() != null) VALUES("title", "#{title}");
                if (project.getProj_type() != null) VALUES("proj_type", "#{proj_type}");
                if(project.getLeader_id() != null) VALUES("leader_id", "#{leader_id}");


                if (project.getFunds() != null) VALUES("funds", "#{funds}");
                if (project.getStart_date() != null) VALUES("start_date", "#{start_date}");
                if (project.getEnd_date() != null) VALUES("end_date", "#{end_date}");
                if (project.getSource() != null) VALUES("source", "#{source}"); // 新增
                if (project.getDescription() != null) VALUES("description", "#{description}");

                if (project.getLab_id() != null) VALUES("lab_id", "#{lab_id}");

            }}.toString();
        }

        public String updateProjectDynamic(ProjectOutputs project) {
            return new SQL() {{
                UPDATE("projects");
                if (project.getType() != null) SET("type", "#{type}");
                if (project.getTitle() != null) SET("title = #{title}");
                if (project.getProj_type() != null) SET("proj_type = #{proj_type}");
                if (project.getLeader_id() != null) SET("leader_id = #{leader_id}");

                if (project.getFunds() != null) SET("funds = #{funds}");
                if (project.getEnd_date() != null) SET("end_date = #{end_date}");
                if (project.getStart_date() != null) SET("start_date = #{start_date}");
                if (project.getSource() != null) SET("source = #{source}");

                if (project.getDescription() != null) SET("description = #{description}");

                if (project.getLab_id() != null) SET("lab_id = #{lab_id}");

                WHERE("project_id = #{project_id}");
            }}.toString();
        }

        public String searchProjectsSQL(
    @Param("type") String type,
    @Param("keyword") String keyword,
    @Param("page") Integer page,
    @Param("displayNum") Integer displayNum) {
    
    return new SQL() {{
        SELECT("p.*, per.name as leader_name, l.name as lab_name");
        FROM("projects p");
        LEFT_OUTER_JOIN("person per ON p.leader_id = per.per_id");
        LEFT_OUTER_JOIN("lab l ON p.lab_id = l.lab_id");
        LEFT_OUTER_JOIN("project_members pm ON p.project_id = pm.project_id");
        LEFT_OUTER_JOIN("person per ON pm.per_id = per.per_id");
        
        if (StringUtils.isNotBlank(type)) {
            WHERE("p.type = #{type}");
        }
        
        if (StringUtils.isNotBlank(keyword)) {
            WHERE("p.type LIKE CONCAT('%', #{keyword}, '%') OR "
                + "p.title LIKE CONCAT('%', #{keyword}, '%') OR "
                + "p.proj_type LIKE CONCAT('%', #{keyword}, '%') OR "
                + "leader_name LIKE CONCAT('%', #{keyword}, '%') OR "
                + "p.source LIKE CONCAT('%', #{keyword}, '%') OR "
                + "p.description LIKE CONCAT('%', #{keyword}, '%') OR "
                + "l.name LIKE CONCAT('%', #{keyword}, '%') " );
        }
        
        GROUP_BY("p.project_id");
        ORDER_BY("p.project_id DESC");
        
        if (page != null && displayNum != null) {
            String limitOffset = String.format("LIMIT %d OFFSET %d", 
                displayNum, (page - 1) * displayNum);
            OFFSET_ROWS(limitOffset);
        }
    }}.toString();
}
    }



    @SelectProvider(type = ProjectSqlProvider.class, method = "selectByPageSql")
    List<ProjectOutputs> selectAllByPage(Integer page, Integer displayNum);

    @SelectProvider(type = ProjectSqlProvider.class , method = "searchProjectsSQL")
    List<ProjectOutputs> searchProjects(String type,String keyword,Integer page, Integer displayNum);


    //感觉不如直接用上面这个模糊匹配。。。。。。
    @Select("SELECT DISTINCT p.* , per.name as leader_name FROM projects p " +
            "LEFT JOIN project_members pm ON p.project_id = pm.project_id " +
            "LEFT JOIN person per ON pm.per_id = per.per_id " +
            "where pm.per_id=#{perId} order by project_id DESC")
    List<ProjectOutputs> selectByParticipant(Long perId);

    @InsertProvider(type = ProjectSqlProvider.class, method = "insertProjectDynamic")
    @Options(useGeneratedKeys = true, keyProperty = "project_id")
    void insertProject(ProjectOutputs project);



    @Select("SELECT * FROM projects WHERE project_id = #{projectId} order by project_id DESC" )
    ProjectOutputs selectProjectById(Long projectId);
    
    @UpdateProvider(type = ProjectSqlProvider.class, method = "updateProjectDynamic")
    void updateProject(ProjectOutputs project);

    @Delete("DELETE FROM projects WHERE project_id = #{projectId}")
    void deleteProject(Long projectId);

}
