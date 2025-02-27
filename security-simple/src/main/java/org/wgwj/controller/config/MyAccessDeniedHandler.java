package org.wgwj.controller.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: 徐钢
 * @Date: 2025/2/26:21:44
 * @Description:
 **/
public class MyAccessDeniedHandler  implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String errMsg = accessDeniedException.getLocalizedMessage();
        HashMap result = new HashMap();
        result.put("code",-1);
        result.put("message",errMsg);

        String json = JSON.toJSONString(result);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().println(json);
    }
}
