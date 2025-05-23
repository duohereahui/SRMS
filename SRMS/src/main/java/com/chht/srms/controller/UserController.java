package com.chht.srms.controller;
import com.chht.srms.domain.shared.Result;
import com.chht.srms.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.chht.srms.service.UserService;
import com.chht.srms.util.JwtUtil;

import java.util.Map;

@RestController
@RequestMapping("system/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login") //用户登陆，id、邮箱或电话均可用作登陆账号
    public Result login(@RequestParam String credential,
                        @RequestParam String password) {
                User user = userService.selectByAnyCredential(credential);
                if (user == null || !userService.checkPassword(password, user.getPassword())) {
                    return Result.fail("用户名或密码错误");
                }

        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken(user.getUsername());
        //token作为data返回
        return Result.success(token);
    }
    
    @PostMapping("/register")  //用户注册
    public Result register(@RequestParam Map<String,Object> params) {
        //查询是否已重复
        User checkPhone = userService.findByUserPhone(params.get("phone").toString());
        User checkEmail = userService.findByEmail(params.get("email").toString());

                     if (checkEmail!=null) {
                         return Result.fail(checkEmail.getEmail()+"邮箱已被使用");
                     }
                     if (checkPhone != null ) {
                         return Result.fail(checkPhone.getPhone()+"手机号已被使用");
                     }
                         //注册
                             User user = new User();
                             user.setUsername(params.get("username").toString());
                             user.setPassword(params.get("password").toString());
                             user.setEmail(params.get("email").toString());
                             user.setPhone(params.get("phone").toString());
                             user.setRole(params.get("role").toString());
                             user.setDepartment_id(params.get("department_id").toString());
                             user.setLab_id(params.get("lab_id").toString());
                             user.setTeacher_id(params.get("teacher_id").toString());
                             user.setDescription(params.get("description").toString());
                         userService.register(user);
                         return Result.success();
    }

//    @PostMapping("/register")
//    public Result register(@RequestBody User user) {
//        User checkPhone = userService.findByUserPhone(user.getPhone().toString());
//        User checkEmail = userService.findByEmail(user.getEmail());
//        if (checkEmail!=null) {
//            return Result.fail(checkEmail.getEmail()+"邮箱已被使用");
//        }
//        if (checkPhone != null ) {
//            return Result.fail(checkPhone.getPhone()+"手机号已被使用");
//        }
//        userService.register(user);
//        return Result.success();
//    }

    @PutMapping("/{user_id}")   //根据传入id修改user信息（包括密码）
    public Result updateUser(
            @PathVariable Long user_id,
            @RequestParam(defaultValue = "") String password,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String phone,
            @RequestParam(defaultValue = "") String email,
            @RequestParam(defaultValue = "") String department_id,
            @RequestParam(defaultValue = "") String role,
            @RequestParam(defaultValue = "") String lab_id,
            @RequestParam(defaultValue = "") String teacher_id,
            @RequestParam(defaultValue = "") String description) {
        
        User existingUser = userService.findByUserId(user_id);
        if (existingUser == null) {
            return Result.fail("用户不存在");
        }

        User updateUser = new User();
        if (!password.isEmpty()) updateUser.setPassword(password);
        if (!username.isEmpty()) updateUser.setUsername(username);
        if (!phone.isEmpty()) updateUser.setPhone(phone);
        if (!email.isEmpty()) updateUser.setEmail(email);
        if (!department_id.isEmpty()) updateUser.setDepartment_id(department_id);
        if (!role.isEmpty()) updateUser.setRole(role);
        if (!lab_id.isEmpty()) updateUser.setLab_id(lab_id);
        if (!teacher_id.isEmpty()) updateUser.setTeacher_id(teacher_id);
        if (!description.isEmpty()) updateUser.setDescription(description);

        userService.updateUser(user_id, updateUser);
        return Result.success();
    }

    @GetMapping("/{user_id}")  //根据用户id返回其详细信息
    public Result userDetails(@PathVariable Long user_id) {
        User user = userService.findByUserId(user_id);
        if (user == null) {
            return Result.fail("用户不存在");
        }
        return Result.success(user);
    }

    @DeleteMapping("/delete") //根据用户id删除用户
    public Result deleteUser(@RequestParam Long user_id) {
        User user = userService.findByUserId(user_id);
        if (user == null) {
            return Result.fail("用户不存在");
        }
        userService.deleteByUserId(user_id);
        return Result.success();
    }
}
