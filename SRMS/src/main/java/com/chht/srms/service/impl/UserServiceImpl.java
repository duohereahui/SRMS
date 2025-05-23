package com.chht.srms.service.impl;

import com.chht.srms.domain.user.User;
import com.chht.srms.mapper.UserMapper;
import com.chht.srms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserId(Long user_id) {
        return userMapper.findByUserId(user_id);
    }
    @Override
    public User findByEmail(String email){
        return userMapper.findByEmail(email);
    }

    @Override
    public User findByUserPhone(String phone){return userMapper.findByUserPhone(phone);}

    @Override
    public List<User> findByLabId(Long lab_id){
        return  userMapper.findByLabId(lab_id);
    }

    @Override
    public User selectByAnyCredential(String credential){
        return userMapper.selectByAnyCredential(credential);
    }


    @Override
    public boolean checkPassword(String loginPassword, String userPassword) {
        return loginPassword.equals(userPassword);
    }

    @Override
    public void register(User user) {
        user.setCreated_at(new Date());
        userMapper.insert(user);
    }


    @Override
    public void updateUser(Long user_id, User user) {
        userMapper.updateUser(user_id, user);
    }

    @Override
    public void deleteByUserId(Long user_id) {
        userMapper.deleteByUsername(user_id);
    }
}
