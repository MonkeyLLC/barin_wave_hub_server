package com.llc.search_service.domain.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.llc.search_service.domain.auth.AuthUser;
import com.llc.search_service.domain.auth.AuthUserResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");

        Map<String, Object> res = new HashMap<>();
        res.put("status", 200);
        res.put("msg", "登录成功");

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        AuthUserResponse authUserResponse = new AuthUserResponse();

        BeanUtils.copyProperties(authUser, authUserResponse);

        res.put("data", authUserResponse);

        String resStr = new ObjectMapper().writeValueAsString(res);

        response.getWriter().write(resStr);
    }
}
