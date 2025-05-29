package com.chht.srms.mapper;

import com.chht.srms.domain.achievements.AchievementsAuthors;
import com.chht.srms.domain.achievements.AchievementsOutputs;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

@Mapper
public interface ResMapper {
    public class ResSqlProvider {
        public String updateResDynamic(AchievementsOutputs res) {
            return new SQL() {{
                UPDATE("research_outputs");
                if (res.getTitle() != null) SET("title = #{title}");
                if (res.getDescription() != null) SET("description = #{description}");
                if (res.getType() != null) SET("type = #{type}");
                if (res.getStatus() != null) SET("status = #{status}");
                if (res.getFile_path() != null) SET("file_path = #{file_path}");
                if (res.getPublish_date() != null) SET("publish_date = #{publish_date}");
                if (res.getJournal_name() != null) SET("journal_name = #{journal_name}");
                if (res.getPaper_type() != null) SET("paper_type = #{paper_type}");
                if (res.getImpact_factor() != null) SET("impact_factor = #{impact_factor}");
                if (res.getDoi() != null) SET("doi = #{doi}");
                if (res.getPatent_number() != null) SET("patent_number = #{patent_number}");
                WHERE("research_id = #{research_id}");
            }}.toString();
        }
    }

    @Insert("<script>" +
            "INSERT INTO research_outputs " +
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">" +
            "  title, type, status, description, file_path, publish_date," +
            "  <if test=\"type == 'paper'\">journal_name, paper_type, impact_factor, doi,</if>" +
            "  <if test=\"type == 'patent'\">patent_number,</if>" +
            "</trim>" +
            "VALUES " +
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">" +
            "  #{title}, #{type}, #{status}, #{description}, #{file_path}, #{publish_date}," +
            "  <if test=\"type == 'paper'\">#{journal_name}, #{paper_type}, #{impact_factor}, #{doi},</if>" +
            "  <if test=\"type == 'patent'\">#{patent_number},</if>" +
            "</trim>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "research_id")
    void insertRes(AchievementsOutputs res);

    @Select({"<script>",
        "SELECT ro.*, GROUP_CONCAT(ra.author_id) as author_ids " +
        "FROM research_outputs ro " +
        "LEFT JOIN research_authors ra ON ro.research_id = ra.research_id " +
        "<where>",
        "<if test='params.type != null'> ro.type = #{params.type} </if>",
        "<if test='params.status != null'> AND ro.status = #{params.status} </if>",
        "<if test='params.title != null'> AND ro.title LIKE CONCAT('%', #{params.title}, '%') </if>",
        "<if test='params.researchId != null'> AND ro.research_id = #{params.researchId} </if>",
        "</where>",
        "GROUP BY ro.research_id",
        "</script>"})
    List<AchievementsOutputs> select(@Param("params") Map<String, String> params);


    @Select("SELECT research_id, r.author_id, u.username,r.role FROM research_authors r LEFT JOIN user u ON r.author_id = u.user_id WHERE r.research_id = #{resId}")
    List<AchievementsAuthors> selectAuthorByResearchId(@Param("resId") Long resId);


    @Select("SELECT DISTINCT r.*  FROM research_outputs r " +
            "LEFT JOIN research_authors ra ON r.research_id = ra.research_id " +
            "LEFT JOIN user u ON ra.author_id = u.user_id " +
            "WHERE r.title LIKE CONCAT('%',#{keyword},'%') " +
            "OR r.description LIKE CONCAT('%',#{keyword},'%') " +
            "OR u.username LIKE CONCAT('%',#{keyword},'%')" +
            "OR r.journal_name LIKE CONCAT('%',#{keyword},'%')")
    List<AchievementsOutputs> searchResearch(String keyword);

    @UpdateProvider(type = ResSqlProvider.class, method = "updateResDynamic")
    void updateRes(AchievementsOutputs update);

    @Delete("DELETE FROM research_outputs WHERE research_id = #{researchId}")
    int deleteResearchById(Long researchId);

    @Insert("INSERT INTO research_authors(research_id, author_id , role) VALUES(#{research_id},#{author_id},#{role})")
    void insertAuthor(AchievementsAuthors author);

    @Delete("DELETE FROM research_authors WHERE research_id=#{research_id}")
    void deleteAuthor(Long research_id);
}