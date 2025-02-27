package org.wgwj.controller.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: 徐钢
 * @Date: 2025/2/26:19:29
 * @Description:
 **/
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Object principal = authentication.getPrincipal(); //获取用户信息
//        Object credentials = authentication.getCredentials(); // 获取凭证信息， 如凭证是密码， 那这个值 就是密码
//        authentication.getAuthorities(); // 获取用户权限信息

        HashMap result = new HashMap();
        result.put("code",0);
        result.put("message","登录成功");
        result.put("data",principal);

        String json = JSON.toJSONString(result);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().println(json);
    }
}
