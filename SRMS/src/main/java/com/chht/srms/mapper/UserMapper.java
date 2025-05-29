package com.chht.srms.mapper;

import com.chht.srms.domain.user.User;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface UserMapper {

    public class UserSqlProvider {
        public String insertUser(User user) {
            return new SQL() {{
                INSERT_INTO("user");
                if (user.getPhone() != null) VALUES("phone", "#{phone}");
                if (user.getEmail() != null) VALUES("email", "#{email}");
                if (user.getUsername() != null) VALUES("username", "#{username}");
                if (user.getPassword() != null) VALUES("password", "#{password}");
                if (user.getRole() != null) VALUES("role", "#{role}");
                if (user.getDepartment_id() != null) VALUES("department_id", "#{department_id}");
                if (user.getLab_id() != null) VALUES("lab_id", "#{lab_id}");
                if (user.getTeacher_id() != null) VALUES("teacher_id", "#{teacher_id}");
                if (user.getDescription() != null) VALUES("description", "#{description}");
            }}.toString();
        }
    }

    @Select("SELECT * FROM user WHERE user_id = #{user_id}")
    User findByUserId(Long id);

    @Select("SELECT * FROM user where email=#{email}")
    User findByEmail(String email);

    @Select("SELECT * FROM user where phone=#{phone}")
    User findByUserPhone(String phone);
    
    @Select("SELECT  * FROM user where lab_id=#{lab_id}")
    List<User> findByLabId(Long lab_id);



    @Select("SELECT * FROM user WHERE username = #{credential} OR email = #{credential} OR phone = #{credential}")
    User selectByAnyCredential(@Param("credential") String credential);

    @InsertProvider(type = UserMapper.UserSqlProvider.class, method = "insertUser")
    @Options(useGeneratedKeys = true, keyProperty = "user_id")
    void insert(User user);
   
    @Update({"<script>",
"UPDATE user",
"<set>",
"<if test='user.username != null'>username=#{user.username},</if>",
"<if test='user.password != null'>password=#{user.password},</if>",
"<if test='user.lab_id != null'>real_name=#{user.real_name},</if>",
"<if test='user.phone != null'>phone=#{user.phone},</if>",
"<if test='user.email != null'>email=#{user.email},</if>",
"<if test='user.department_id != null'>department_id=#{user.department_id},</if>",
"<if test='user.role != null'>role=#{user.role},</if>",
"<if test='user.lab_id != null'>lab_id=#{user.lab_id},</if>",
"<if test='user.teacher_id != null'>teacher_id=#{user.teacher_id},</if>",
"<if test='user.description != null'>description=#{user.description},</if>",
"</set>",
"WHERE user_id=#{user_id}",
"</script>"})
    void updateUser(@Param("user_id") Long user_id, @Param("user") User user);


    @Delete("DELETE FROM user WHERE user_id = #{user_id}")
    void deleteByUsername(Long user_id);

    @Select("SELECT * FROM user WHERE department_id=#{deptId} and user.role = #{role}")
    List<User> selectByDepartmentAndRole(@Param("deptId") Long deptId, @Param("role") String role);

}