package org.wgwj.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: 徐钢
 * @Date: 2025/2/25:21:19
 * @Description:
 **/
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){


        return "index";
    }


    @GetMapping("/hi")
    public String hi(){
        return "from security";
    }
}
