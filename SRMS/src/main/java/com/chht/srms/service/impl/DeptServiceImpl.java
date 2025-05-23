package com.chht.srms.service.impl;

import com.chht.srms.domain.dept.dept;
import com.chht.srms.mapper.DeptMapper;
import com.chht.srms.mapper.LabMapper;
import com.chht.srms.mapper.UserMapper;
import com.chht.srms.service.DeptService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private UserMapper userMapper;  // 复用已有的mapper
    
    @Autowired
    private LabMapper labMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<dept> getAllDept(){
        return deptMapper.selectAllDepts();
    }

    @Override
    public dept selectByDeptId(Long deptId) {
        return deptMapper.selectByDeptId(deptId);
    }

    @Override
    public void updateByDeptId(dept updateDept) {
        deptMapper.updateDept(updateDept);
    }

    @Override
    public void addDept(dept dept){
        deptMapper.addDept(dept);
    }


    @Override
    public java.util.List<?> getTeachersByDept(Long departmentId) {
        return userMapper.selectByDepartmentAndRole(departmentId, "teacher");
    }

    @Override
    public java.util.List<?> getStudentsByDept(Long departmentId) {
        return userMapper.selectByDepartmentAndRole(departmentId, "student");
    }

    @Override
    public java.util.List<?> getLabsByDept(Long departmentId) {
        return labMapper.findByDepartmentId(departmentId);
    }

    @Override
    public  void deleteDeptById(Long detpId){
        deptMapper.deleteDeptById(detpId);
    }
}