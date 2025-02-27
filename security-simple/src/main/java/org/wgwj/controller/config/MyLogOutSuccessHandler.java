package org.wgwj.controller.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: 徐钢
 * @Date: 2025/2/26:20:18
 * @Description:
 **/
public class MyLogOutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HashMap result = new HashMap();
        result.put("code",0);
        result.put("message","登出成功");

        String json = JSON.toJSONString(result);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().println(json);
    }
}
