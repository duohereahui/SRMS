package com.chht.srms.controller;

import com.chht.srms.domain.lab.lab;
import com.chht.srms.domain.resourse.Resource;
import com.chht.srms.domain.shared.Result;
import com.chht.srms.service.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

@RestController
@RequestMapping("/system/lab")
public class LabController {
    @Autowired
    private LabService labService;

    //lab部分
    @PostMapping //插入新的实验室
    public Result insert(@RequestParam Map<String,Object> params) {
        lab lab=new lab();
        lab.setName(params.get("name").toString());
        lab.setDepartment_id(Long.valueOf(params.get("department_id").toString()));
        lab.setLocation(params.get("location").toString());
        lab.setDescription(params.get("description").toString());
        labService.insert(lab);
        return Result.success();
    }

    @PutMapping("/{labId}")  //根据id修改实验室相关信息
    public Result updateById(@PathVariable Long labId,
                             @RequestParam Map<String,Object> params ) {
        lab updatelab=labService.selectById(labId);
        if(params.get("description")!=null) {
            updatelab.setDescription(params.get("description").toString());
        }
        if(params.get("location")!=null) {
            updatelab.setLocation(params.get("location").toString());
        }
        if(params.get("department_id")!=null) {
            updatelab.setDepartment_id(Long.valueOf(params.get("department_id").toString()));
        }
        labService.updateById(updatelab);
        return Result.success();
    }

    @DeleteMapping("/{labId}")  //根据id删除实验室
    public Result deleteById(@PathVariable Long labId) {
        labService.deleteById(labId);
        return Result.success();
    }


    @GetMapping("/search") //根据名字模糊搜索实验室
    public Result findByNameLike(@RequestParam String name) {
        return Result.success(labService.findByNameLike(name));
    }
    @GetMapping("/{labId}")
    public Result findById(@PathVariable Long labId) {
        lab lab=labService.selectById(labId);
        return Result.success(lab);
    }
    @GetMapping
    public Result findAll() {
        return Result.success(labService.listAllLab());
    }

    @GetMapping("/{labId}/students")  //返回实验室下所有学生
    public Result getStudentsByLab(@PathVariable Long labId) {
        return Result.success(labService.getStudentsByLab(labId));
    }
    @GetMapping("/{labId}/teachers")  //返回实验室下所有老师
    public Result getTeachersByLab(@PathVariable Long labId) {
        return Result.success(labService.getTeachersByLab(labId));
    }

    //resources部分
    @GetMapping("/{labId}/resources")  //返回实验室拥有的资源
    public Result getLabResources(@PathVariable Long labId) {
        return Result.success(labService.getLabResources(labId));
    }

    @GetMapping("resources/{resourceId}")
    public Result getResourcesById(@PathVariable Long resourceId) {
        return Result.success(labService.getResourceById(resourceId));
    }

    @PostMapping("/{labId}/resources")
    public Result insertResource(@RequestParam Map<String,Object> params, @PathVariable Long labId) {
        Resource resource=new Resource();
        resource.setName(params.get("name").toString());
        resource.setLocation(params.get("location").toString());
        resource.setType(params.get("type").toString());  //equipment,consumables,software
        try {
            resource.setPurchase_date( new SimpleDateFormat("yyyy-MM-dd").parse( params.get("purchase_date").toString()));
        } catch (ParseException e) {
            return Result.fail("日期格式错误");
        }

        resource.setLab_id(labId);
        resource.setStatus("available");

        labService.insertResource(resource);
        return Result.success();
    }

    @PutMapping("/resources/{resourceId}")
    public Result updateResourceById(@PathVariable Long resourceId,
                             @RequestParam Map<String,Object> params) {

        Resource updateResource=new Resource();
        updateResource=labService.getResourceById(resourceId);
        if(params.get("description")!=null)
        {updateResource.setName(params.get("name").toString());}
        if(params.get("location")!=null)
        {updateResource.setLocation(params.get("location").toString());}
        if(params.get("type")!=null)
        {updateResource.setType(params.get("type").toString());}

        labService.updateResourceById(updateResource);
        return Result.success();
    }

    @DeleteMapping("/resources/{resourceId}")
    public Result deleteResourceById(@PathVariable Long resourceId) {
        labService.deleteResourceById(resourceId);
        return Result.success();
    }




}