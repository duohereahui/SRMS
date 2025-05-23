package com.chht.srms.controller;

import com.chht.srms.domain.dept.dept;
import com.chht.srms.domain.shared.Result;
import com.chht.srms.service.DeptService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/system/department")
public class DeptController {
    private final DeptService deptService;
    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    @GetMapping
    public Object getAll(){
        return deptService.getAllDept();
    }

    @GetMapping("/{id}/teachers") //根据学院id返回学院下所有老师
    public Object getTeachers(@PathVariable Long id) {
        return deptService.getTeachersByDept(id);
    }

    @GetMapping("/{id}/students") //根据学院id返回学院下所有学生
    public Object getStudents(@PathVariable Long id) {
        return deptService.getStudentsByDept(id);
    }

    @GetMapping("/{id}/labs") //根据学院id返回学院下所有实验室
    public Object getLabs(@PathVariable Long id) {
        return deptService.getLabsByDept(id);
    }

    @PutMapping("/{deptId}")  //根据id修改学院相关信息
    public Result updateById(@PathVariable Long deptId,
                             @RequestParam Map<String,Object> params ) {
        dept updateDept=deptService.selectByDeptId(deptId);
        if(params.get("name")!=null) {
            updateDept.setName(params.get("name").toString());
        }
        if(params.get("description")!=null) {
            updateDept.setDescription(params.get("description").toString());
        }
        deptService.updateByDeptId(updateDept);
        return Result.success();
    }

    @PutMapping
    public Result addDept(@RequestParam Map<String,Object> params) {
        dept dept=new dept();
        dept.setName(params.get("name").toString());
        dept.setDescription(params.get("description").toString());
        deptService.addDept(dept);
        return Result.success();
    }

    @DeleteMapping
    public Result deleteById(@RequestParam Long deptId) {
        dept deleteDept=deptService.selectByDeptId(deptId);
        if(deleteDept!=null) {
            deptService.deleteDeptById(deptId);
        }
        else {
            return Result.fail("学院不存在");
        }
        return Result.success();
    }

}