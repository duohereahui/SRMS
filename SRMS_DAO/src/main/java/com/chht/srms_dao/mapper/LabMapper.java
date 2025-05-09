package com.chht.srms_dao.mapper;

import com.chht.srms_dao.domain.lab.lab;
import com.chht.srms_dao.domain.resourse.Resource;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LabMapper {
    @Insert("INSERT INTO lab(name, description, location, department_id) " +
            "VALUES(#{name}, #{description}, #{location}, #{department_id})")
    void insert(lab lab);

    @Update("UPDATE lab SET name=#{name}, description=#{description}, " +
            "location=#{location}, department_id=#{department_id} WHERE lab_id=#{lab_id}")
    void updateById(lab lab);

    @Delete("DELETE FROM lab WHERE lab_id = #{labId}")
    void deleteById(Long labId);

    @Select("SELECT * FROM lab WHERE lab_id = #{labId}")
    lab selectById(Long labId);

    @Select("SELECT * FROM lab WHERE department_id = #{departmentId}")
    List<lab> findByDepartmentId(Long departmentId);

    @Select("SELECT * FROM lab WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<lab> findByNameLike(String name);

    @Select("SELECT * FROM resource WHERE lab_id= #{labId}")
    List<Resource> getLabResources(Long labId);

    @Select("SELECT * FROM lab")
    List<lab> ListAllLab();


    @Insert("INSERT INTO resource(name, location,type,status, purchase_date, lab_id) " +
            "VALUES(#{name},#{location},#{type} , #{status}, #{purchase_date}, #{lab_id})")
    void insertResource(Resource resource);

    @Select("SELECT * FROM resource WHERE resource_id= #{resourceId}")
    Resource getResourceById(Long resourceId);

    @Update("UPDATE resource SET name=#{name}, location=#{location}, " +
            "status=#{status}, purchase_date=#{purchase_date}, lab_id=#{lab_id} " +
            "WHERE resource_id=#{resource_id}")
    void updateResourceById(Resource resource);

    @Delete("DELETE FROM resource WHERE resource_id = #{resource_id}")
    void deleteResourceById(Long resourceId);

}