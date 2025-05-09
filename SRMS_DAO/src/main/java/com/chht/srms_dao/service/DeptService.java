package com.chht.srms_dao.service;

import com.chht.srms_dao.domain.dept.dept;
import com.chht.srms_dao.domain.shared.Result;

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