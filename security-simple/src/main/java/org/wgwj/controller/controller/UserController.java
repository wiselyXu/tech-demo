package org.wgwj.controller.controller;

import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.wgwj.controller.entity.User;
import org.wgwj.controller.service.UserService;

import java.util.List;
import java.util.Map;

/**
 * @Author: 徐钢
 * @Date: 2025/2/26:10:16
 * @Description:
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    public UserService userService;

    @GetMapping("/lsit")
    public List<User> getList(){
        return userService.list();
    }

    @GetMapping("/health")
    @PreAuthorize("hasRole('ADMIN') and authentication.name='admin'")
    public String health(){

        return "security health";
    }

    //@PreAuthorize("hasRole('USER')")
    @PreAuthorize("hasAuthries('USER_ADD')")
    @PostMapping("/add")
    public void add(@RequestBody  User user){
        userService.saveUserDetails(user);
    }



//    @GetMapping("/getCredention")
//    public Map getCrendential(){
//        SecurityContext  context = SecurityContextHolder.getContext();
//        Authentication authentication = context.getAuthentication();
//        Object principal = authentication.getPrincipal();
//        Object crendential = authentication.getCredentials();
//
//
//    }
}
