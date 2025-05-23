package com.chht.srms.mapper;

import com.chht.srms.domain.dept.dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {

    @Select("SELECT * FROM department ")
    List<dept> selectAllDepts();

    @Select("SELECT * from department where department_id=#{deptId}")
    dept selectByDeptId(Long deptId);

    @Update("UPDATE department SET name=#{name}, description=#{description} WHERE department_id=#{department_id}")
    void updateDept(dept dept);

    @Insert("INSERT INTO department(name, description)" +"\n" +
            "VALUES(#{name}, #{description})")
    void addDept(dept dept);

    @Delete("delete from department where department_id=#{deptId}")
    void deleteDeptById(Long deptId);
}
