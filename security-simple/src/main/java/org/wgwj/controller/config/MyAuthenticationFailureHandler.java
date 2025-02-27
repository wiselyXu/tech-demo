package org.wgwj.controller.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: 徐钢
 * @Date: 2025/2/26:19:30
 * @Description:
 **/
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errMsg = exception.getLocalizedMessage();
        HashMap result = new HashMap();
        result.put("code",-1);
        result.put("message",errMsg);

        String json = JSON.toJSONString(result);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().println(json);
    }
}
