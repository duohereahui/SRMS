package com.chht.srms_dao.service;

import com.chht.srms_dao.domain.lab.lab;
import com.chht.srms_dao.domain.resourse.Resource;
import com.chht.srms_dao.domain.user.User;

import java.util.List;

public interface LabService {
    void insert(lab lab);
    void updateById(lab lab);
    void deleteById(Long labId);

    List<lab> findByNameLike(String name);

    
    List<User> getStudentsByLab(Long labId);
    List<User> getTeachersByLab(Long labId);

    lab selectById(Long labId);

    List<lab> listAllLab();

    //resource部分
    List<Resource> getLabResources(Long labId);
    Resource getResourceById(Long resourceId);
    void insertResource(Resource resource);
    void updateResourceById(Resource resource);
    void deleteResourceById(Long resourceId);


}