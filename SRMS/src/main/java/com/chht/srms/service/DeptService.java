package com.chht.srms.service;

import com.chht.srms.domain.dept.dept;

import java.util.List;

public interface DeptService {
    List<?> getTeachersByDept(Long departmentId);
    List<?> getStudentsByDept(Long departmentId);
    List<?> getLabsByDept(Long departmentId);

    List<dept> getAllDept();

    dept selectByDeptId(Long deptId);

    void updateByDeptId(dept updateDept);

    void addDept(dept dept);

    void deleteDeptById(Long detpId);
}