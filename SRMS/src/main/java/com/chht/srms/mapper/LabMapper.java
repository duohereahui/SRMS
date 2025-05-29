package com.chht.srms.mapper;

import com.chht.srms.domain.lab.Lab;
import com.chht.srms.domain.person.Person;
import com.chht.srms.domain.resourse.Resource;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface LabMapper {
    public class SqlProvider {
        //插入
        public String insertLabSQL(Lab lab) {
            return new SQL() {{
                INSERT_INTO("lab");
                if(lab.getLab_id() != null) VALUES("lab_id", "#{lab_id}" );
                if(lab.getName() != null) VALUES("name", "#{name}");
                if(lab.getDescription() != null) VALUES("description", "#{description}");
                if (lab.getLocation() != null) VALUES("location", "#{location}");
                if(lab.getLeader_id() != null) VALUES("leader_id", "#{leader_id}");
            }}.toString();
        }

        //更新
        public String updateLabSQL(Lab lab) {
            return new SQL() {{
                UPDATE("lab");
                if (lab.getName() != null) SET("name = #{name}");
                if (lab.getDescription() != null) SET("description = #{description}");
                if (lab.getLocation() != null) SET("location = #{location}");
                if (lab.getLeader_id() != null) SET("leader_id = #{leader_id}");
                WHERE("lab_id = #{lab_id}");
            }}.toString();
        }

        //删除
        public String deleteLabSQL() {
            return new SQL() {{
                DELETE_FROM("lab");
                WHERE("lab_id = #{lab_id}");
            }}.toString();
        }

        //根据ID查询
        public String selectLabByIdSQL() {
            return new SQL() {{
                SELECT("l.*, p.name as leader_name");
                FROM("lab l");
                LEFT_OUTER_JOIN("person p ON l.leader_id = p.per_id");
                WHERE("l.lab_id = #{lab_id}");
            }}.toString();
        }

        //条件分页查询
        public String selectLabsByKeywordSQL(
            @Param("keyword") String keyword,
            @Param("page") Integer page,
            @Param("displayNum") Integer displayNum
        ) {
            return new SQL() {{
                SELECT("l.*, p.name as leader_name");
                FROM("lab l");
                LEFT_OUTER_JOIN("person p ON l.leader_id = p.per_id");
                if(keyword != null) {
                    WHERE("l.name LIKE CONCAT('%', #{keyword}, '%') OR " +
                          "l.location LIKE CONCAT('%', #{keyword}, '%') OR " +
                          "l.description LIKE CONCAT('%', #{keyword}, '%') OR " +
                          "p.name LIKE CONCAT('%', #{keyword}, '%')");
                }
                ORDER_BY("l.lab_id");
                if (page != null && displayNum != null) {
                    String limitOffset = String.format("LIMIT #{displayNum} OFFSET %d", (page - 1) * displayNum);
                    OFFSET_ROWS(limitOffset);
                }
            }}.toString();
        }

        //资源分页查询
        public String getResourcesByLabIdSQL(
            @Param("labId") Long labId,
            @Param("page") Integer page,
            @Param("displayNum") Integer displayNum
        ) {
            return new SQL() {{
                SELECT("*");
                FROM("resource");
                WHERE("lab_id = #{labId}");
                ORDER_BY("resource_id");
                if (page != null && displayNum != null) {
                    String limitOffset = String.format("LIMIT #{displayNum} OFFSET %d", (page - 1) * displayNum);
                    OFFSET_ROWS(limitOffset);
                }
            }}.toString();
        }

        //更新资源
        public String updateResourceByIdSQL(Resource resource) {
            return new SQL() {{
                UPDATE("resource");
                if (resource.getName() != null) SET("name = #{name}");
                if (resource.getLocation() != null) SET("location = #{location}");
                if (resource.getType() != null) SET("type = #{type}");
                if (resource.getDescription() != null) SET("description = #{description}");
                if (resource.getLab_id() != null) SET("lab_id = #{lab_id}");
                WHERE("resource_id = #{resource_id}");
            }}.toString();
        }
    }

    @InsertProvider(type = SqlProvider.class,method = "insertLabSQL" )
    @Options(useGeneratedKeys = true, keyProperty = "lab_id")
    void insert(Lab lab);

    @UpdateProvider(type = SqlProvider.class, method = "updateLabSQL")
    void updateById(Lab lab);

    @DeleteProvider(type = SqlProvider.class, method = "deleteLabSQL")
    void deleteById(Long labId);

    @SelectProvider(type = SqlProvider.class, method = "selectLabByIdSQL")
    Lab selectById(Long labId);

    @SelectProvider(type = SqlProvider.class, method = "selectLabsByKeywordSQL")
    List<Lab> selectLabsByKeyword(
        @Param("keyword") String keyword,
        @Param("page") Integer page,
        @Param("displayNum") Integer displayNum
    );

    @Select("SELECT * FROM resource WHERE lab_id= #{labId}")
    List<Resource> getLabResources(Long labId);

    //resource部分
    @Insert("INSERT INTO resource(name, location,type, description, lab_id) " +
            "VALUES(#{name},#{location},#{type} , #{description}, #{lab_id})")
    @Options(useGeneratedKeys = true, keyProperty = "resource_id")
    void insertResource(Resource resource);

    @SelectProvider(type = SqlProvider.class, method = "getResourcesByLabIdSQL")
    List<Resource> getResourcesByLabId(
        @Param("labId") Long labId,
        @Param("page") Integer page,
        @Param("displayNum") Integer displayNum
    );

    @Select("SELECT * FROM resource WHERE resource_id= #{resourceId}")
    Resource getResourceById(Long resourceId);

    @UpdateProvider(type = SqlProvider.class, method = "updateResourceByIdSQL")
    void updateResourceById(Resource resource);

    @Delete("DELETE FROM resource WHERE resource_id = #{resource_id}")
    void deleteResourceById(Long resourceId);
}