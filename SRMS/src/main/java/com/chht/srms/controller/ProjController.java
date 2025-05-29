package com.chht.srms.controller;

import com.chht.srms.domain.project.ProjectOutputs;
import com.chht.srms.domain.shared.Result;
import com.chht.srms.service.ProjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/system/project")
public class ProjController {
    @Autowired
    private ProjService projService;

    @GetMapping   //返回目前已有的全部项目
    public Result getAll() {
        return Result.success(projService.getAllProjects());
    }

    @GetMapping("/{part_id}")
    public Result getById(@PathVariable("part_id") Long partId) {
        return Result.success(projService.getProjectsByPart(partId));
    }

    @GetMapping("/search")   //根据keyword模糊匹配项目，匹配范围（tittle、description、leader_name、part_name）
    public Result searchProjects(@RequestParam String keyword) {
        return Result.success(projService.searchProjects(keyword));
    }

    @GetMapping("/department/{deptId}")   //显示某个学院下的所有项目
    public Result searchProjectsByDept(@PathVariable Long deptId) {
        return Result.success(projService.searchProjectsByDept(deptId));
    }

    
    @GetMapping("/status/{status}") //根据项目状态返回所有项目
    public Result byStatus(@PathVariable String status) {
        return Result.success(projService.getByStatus(status));
    }

    @PostMapping//需json格式
    public Result createProject(
        @RequestBody ProjectOutputs proj) {
        proj.setStatus("draft");
        return Result.success(projService.createProject(proj));
    }

    //需json格式
    @PutMapping("/{projectId}")
    public Result updateProject(
        @PathVariable Long projectId,
        @RequestBody ProjectOutputs updateProj) {
        return Result.success(projService.updateProject(projectId, updateProj));
    }

    @DeleteMapping("/{projectId}")
    public Result deleteProject(@PathVariable Long projectId) {
        projService.deleteProject(projectId);
        return Result.success();
    }
}