package com.chht.srms_dao.mapper;

import com.chht.srms_dao.domain.dept.dept;
import com.chht.srms_dao.domain.user.User;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE user_id = #{user_id}")
    User findByUserId(Long id);

    @Select("SELECT * FROM user where email=#{email}")
    User findByEmail(String email);

    @Select("SELECT * FROM user where phone=#{phone}")
    User findByUserPhone(String phone);
    
    @Select("SELECT  * FROM user where lab_id=#{lab_id}")
    List<User> findByLabId(Long lab_id);


    @Select("SELECT  * FROM user where department_id=#{deptId}")
    List<User> findByDeptId(Long deptId);

    @Select("SELECT * FROM user WHERE username = #{credential} OR email = #{credential} OR phone = #{credential}")
    User selectByAnyCredential(@Param("credential") String credential);



    @Insert("INSERT INTO user (username, password, phone,email, department_id, role,lab_id,teacher_id) " +
            "VALUES (#{username}, #{password}, #{phone} ,#{email}, #{department_id}, #{role}, #{lab_id} ,#{teacher_id})")
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
"</set>",
"WHERE user_id=#{user_id}",
"</script>"})
    void updateUser(@Param("user_id") Long user_id, @Param("user") User user);


    @Delete("DELETE FROM user WHERE user_id = #{user_id}")
    void deleteByUsername(Long user_id);

    @Select("SELECT * FROM user WHERE department_id=#{deptId} and user.role = #{role}")
    List<User> selectByDepartmentAndRole(@Param("deptId") Long deptId, @Param("role") String role);

}