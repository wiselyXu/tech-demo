package org.wgwj.controller.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.wgwj.controller.config.DBUserDetailsManager;
import org.wgwj.controller.entity.User;
import org.wgwj.controller.mapper.UserMapper;
import org.wgwj.controller.service.UserService;

/**
 * @Author: 徐钢
 * @Date: 2025/2/26:09:54
 * @Description:
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Resource
    private DBUserDetailsManager  dbUserDetailsManager;


    public void saveUserDetails(User user){
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder().
                        username(user.getUsername()).
                        password(user.getPassword())
                        //.roles("USER")
                        .build();
        dbUserDetailsManager.createUser(userDetails);
    }
}
