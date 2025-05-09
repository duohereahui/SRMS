package com.chht.srms_dao.service.impl;

import com.chht.srms_dao.domain.lab.lab;
import com.chht.srms_dao.domain.resourse.Resource;
import com.chht.srms_dao.domain.user.User;
import com.chht.srms_dao.mapper.LabMapper;
import com.chht.srms_dao.service.LabService;
import com.chht.srms_dao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabServiceImpl implements LabService {
    @Autowired
    private UserService userService;

    @Autowired
    private LabMapper labMapper;

    @Override
    public void insert(lab lab) {
        labMapper.insert(lab);
    }

    @Override
    public void updateById(lab lab) {
        labMapper.updateById(lab);
    }

    @Override
    public void deleteById(Long labId) {
        labMapper.deleteById(labId);
    }

    @Override
    public List<lab> findByNameLike(String name) {
        return labMapper.findByNameLike(name);
    }

    @Override
    public lab selectById(Long labId){
        return labMapper.selectById(labId);
    }


    @Override
    public List<lab> listAllLab(){
        return labMapper.ListAllLab();
    }



    @Override
    public List<User> getStudentsByLab(Long labId) {
        return userService.findByLabId(labId).stream()
                .filter(user -> "student".equals(user.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getTeachersByLab(Long labId) {
        return userService.findByLabId(labId).stream()
                .filter(user -> "teacher".equals(user.getRole()))
                .collect(Collectors.toList());
    }

    //resource部分
    @Override
    public List<Resource> getLabResources(Long labId) {
        return labMapper.getLabResources(labId);
    }

    @Override
    public Resource getResourceById(Long resourceId){
        return labMapper.getResourceById(resourceId);
    }

    @Override
    public void insertResource(Resource resource){
        labMapper.insertResource(resource);
    }
    public void updateResourceById(Resource resource){
        labMapper.updateResourceById(resource);
    }
    public void deleteResourceById(Long resourceId){
        labMapper.deleteResourceById(resourceId);
    }
}