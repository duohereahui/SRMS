package com.chht.srms.mapper;

import com.chht.srms.domain.person.Person;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public interface PersonMapper {
    public class PersonSqlProvider {
    //插入
    public String insertPersonSQL(Person person) {
        return new SQL() {{
            INSERT_INTO("person");
            if (person.getPer_id() != null) VALUES("per_id", "#{per_id}");
            if (person.getName() != null) VALUES("name", "#{name}");
            if (person.getPhone() != null) VALUES("phone", "#{phone}");
            if (person.getEmail() != null) VALUES("email", "#{email}");
            if (person.getRole() != null) VALUES("role", "#{role}");
            if (person.getTeacher_id() != null) VALUES("teacher_id", "#{teacher_id}");
            if (person.getLab_id() != null) VALUES("lab_id", "#{lab_id}");
            if (person.getDescription() != null) VALUES("description", "#{description}");
        }}.toString();
    }

    //删除
    public String deletePersonSQL() {
        return new SQL() {{
            DELETE_FROM("person");
            WHERE("per_id = #{per_id}");
        }}.toString();
    }

    //更新
    public String updatePersonSQL(Person person) {
        return new SQL() {{
            UPDATE("person");
            if (person.getName() != null) SET("name = #{name}");
            if (person.getPhone() != null) SET("phone = #{phone}");
            if (person.getEmail() != null) SET("email = #{email}");
            if (person.getRole() != null) SET("role = #{role}");
            if (person.getTeacher_id() != null) SET("teacher_id = #{teacher_id}");
            if (person.getLab_id() != null) SET("lab_id = #{lab_id}");
            if (person.getDescription() != null) SET("description = #{description}");
            WHERE("per_id = #{per_id}");
        }}.toString();
    }

    //根据ID查询
    public String selectPersonByIdSQL() {
        return new SQL() {{
            SELECT("p.*, l.name as lab_name");
            FROM("person p");
            LEFT_OUTER_JOIN("lab l ON p.lab_id = l.lab_id");
            WHERE("p.per_id = #{per_id}");
        }}.toString();
    }

    //条件分页查询
    public String selectPersonsByKeywordSQL(
        @Param("keyword") String keyword,
        @Param("page")Integer page,
        @Param("displayNum") Integer displayNum
    ) {
        return new SQL() {{
            SELECT("p.*, l.name as lab_name");
            FROM("person p");
            LEFT_OUTER_JOIN("lab l ON p.lab_id = l.lab_id");
            if(keyword != null) {
                WHERE("p.name LIKE CONCAT('%', #{keyword}, '%') OR " +
                    "p.phone LIKE CONCAT('%', #{keyword}, '%') OR " +
                    "p.email LIKE CONCAT('%', #{keyword}, '%') OR " +
                    "p.description LIKE CONCAT('%', #{keyword}, '%') OR " +
                    "l.name LIKE CONCAT('%', #{keyword}, '%')");
            }
            ORDER_BY("p.per_id");
            if (page != null && displayNum != null) {
                String limitOffset = String.format("LIMIT #{displayNum} OFFSET %d", (page - 1) * displayNum);
                OFFSET_ROWS(limitOffset);
            }
        }}.toString();
    }
    //根据实验室ID和角色查询
    public String selectByLabAndRoleSQL(
        @Param("lab_id") Integer lab_id,
        @Param("role") String role,
        @Param("page") Integer page,
        @Param("display_num") Integer display_num
    ) {
        return new SQL() {{
            SELECT("p.*, l.name as lab_name");
            FROM("person p");
            LEFT_OUTER_JOIN("lab l ON p.lab_id = l.lab_id");
            if (lab_id != null) {
                WHERE("p.lab_id = #{lab_id}");
            }
            if (role != null && !role.isEmpty()) {
                WHERE("p.role LIKE CONCAT('%', #{role}, '%')");
            }
            ORDER_BY("p.per_id");
            if (page != null && display_num != null) {
                String limitOffset = String.format("LIMIT %d OFFSET %d", display_num, (page - 1) * display_num);
                OFFSET_ROWS(limitOffset);
            }
        }}.toString();
    }
}
    
    @InsertProvider(type = PersonSqlProvider.class, method = "insertPersonSQL")
    @Options(useGeneratedKeys = true, keyProperty = "per_id")
    int insertPerson(Person person);

    @DeleteProvider(type = PersonSqlProvider.class, method = "deletePersonSQL")
    int deletePersonById(@Param("per_id") Integer per_id);

    @UpdateProvider(type = PersonSqlProvider.class, method = "updatePersonSQL")
    int updatePerson(Person person);

    @SelectProvider(type = PersonSqlProvider.class, method = "selectPersonByIdSQL")
    Person selectPersonById(@Param("per_id") Integer per_id);

    @SelectProvider(type = PersonSqlProvider.class, method = "selectPersonsByKeywordSQL")
    List<Person> selectPersonsByKeyword(
        @Param("keyword") String keyword,
        @Param("page") Integer page,
        @Param("display_num") Integer display_num
    );

    @SelectProvider(type = PersonSqlProvider.class, method = "selectByLabAndRoleSQL")
    List<Person> selectByLabAndRole(
        @Param("lab_id") Integer lab_id,
        @Param("role") String role,
        @Param("page") Integer page,
        @Param("display_num") Integer display_num
    );
}
