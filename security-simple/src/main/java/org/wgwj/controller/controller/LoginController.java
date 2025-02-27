package org.wgwj.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: 徐钢
 * @Date: 2025/2/26:16:40
 * @Description:
 **/

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
