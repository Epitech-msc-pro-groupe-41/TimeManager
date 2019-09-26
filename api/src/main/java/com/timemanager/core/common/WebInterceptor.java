package com.timemanager.core.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.timemanager.core.annotation.Role;
import com.timemanager.core.src.service.TokenService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
/**
 * Check JWT token data : if user exist and match with the userID and token
 */
public class WebInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebInterceptor.class);

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {


        if (req.getRequestURI().contains("auth/signUp") || req.getRequestURI().contains("auth/signIn")
                || req.getRequestURI().contains("swagger") || req.getRequestURI().contains("/rest/error") || req.getRequestURI().contains("api-docs")) {
            return true;
        }

        String userType = req.getAttribute("role").toString().toUpperCase();
        boolean matchRole = false;

        // cast the annotated spring handler method
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// get method using reflection
		Method method = handlerMethod.getMethod();
		// check if the method is annotated
		if (handlerMethod.getMethod().isAnnotationPresent(Role.class)) {
			Annotation annotation = method.getAnnotation(Role.class);
			Role methodRole = (Role) annotation;
			
			//Get authorizes roles
            String[] roles = methodRole.access();

            for (String role : roles) {
                if (userType.equals(role.toUpperCase())) {
                    matchRole = true;
                }
            }
        }

        if (!matchRole) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You have no enough permissions");
            return false;
        }

        if (!tokenService.isTokenValid(req.getAttribute("userID").toString(), req.getAttribute("token").toString(),
                req.getAttribute("role").toString())) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You have no enough permissions");
            return false;
        }
        return true;
    }
}