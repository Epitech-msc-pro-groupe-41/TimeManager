package com.timemanager.core.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.timemanager.core.src.service.TokenService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class WebInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebInterceptor.class);

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        if (req.getRequestURI().contains("auth/signUp") || req.getRequestURI().contains("auth/signIn")
                || req.getRequestURI().contains("swagger") || req.getRequestURI().contains("api-docs")) {
            return true;
        }

        if (!tokenService.isTokenValid(req.getAttribute("userID").toString(), req.getAttribute("token").toString())) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized Error");
            return false;
        }
        return true;
    }
}