package org.wgwj.controller.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;
import java.util.HashMap;

/** session 过期了要如何响应
 * @Author: 徐钢
 * @Date: 2025/2/26:20:59
 * @Description:
 **/
public class MySessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HashMap result = new HashMap();
        result.put("code",-1);
        result.put("message","该账号已从其他设备登录");

        String json = JSON.toJSONString(result);
        HttpServletResponse response = event.getResponse();
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().println(json);
    }
}
