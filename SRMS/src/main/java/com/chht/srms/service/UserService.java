package com.chht.srms.service;


import com.chht.srms.domain.user.User;

import java.util.List;

public interface UserService {

    User findByUserId(Long user_id);
    User findByEmail(String email);
    User findByUserPhone(String phone);

    List<User> findByLabId(Long lab_id);

    void register(User user);
    
    boolean checkPassword(String rawPassword, String encodedPassword);

    void updateUser(Long user_id, User user);

    void deleteByUserId(Long user_id);

    //匹配id、邮箱或电话
    User selectByAnyCredential(String credential);
}
