package org.wgwj.controller.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;

/**
 * 针对的是  一些接口需要授权才能访问 ， 但没有登录的处理
 * @Author: 徐钢
 * @Date: 2025/2/26:20:23
 * @Description:
 **/
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String errMsg = authException.getLocalizedMessage();
        HashMap result = new HashMap();
        result.put("code",-1);
        result.put("message",errMsg);

        String json = JSON.toJSONString(result);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().println(json);
    }
}
