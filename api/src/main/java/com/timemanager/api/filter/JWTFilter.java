package com.timemanager.api.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.timemanager.core.src.element.TokenData;
import com.timemanager.core.src.service.TokenService;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
/**
 * Config for JWT which check if the JWT token given by the Front app is not
 * empty and has the valid type.
 */
public class JWTFilter extends GenericFilterBean {

    private TokenService tokenService;

    JWTFilter() {
        this.tokenService = new TokenService();
    }

    @Override
    /**
     * Check the validity of JWT token else return a 401 Unauthorized error.
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String token = request.getHeader("Authorization");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.sendError(HttpServletResponse.SC_OK, "Success");
            return;
        }

        if (allowRequestWithoutToken(request)) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(req, res);
        } else {
            if (token == null || !token.startsWith("Bearer ") || !tokenService.tokenExist(token.substring(7))) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
            } else {
                TokenData tokenData = tokenService.getTokenData(token.substring(7));
                request.setAttribute("userID", tokenData.getUserID());
                request.setAttribute("token", tokenData.getXsrfToken());
                request.setAttribute("role", tokenData.getRole());
                filterChain.doFilter(req, res);
            }
        }
    }

    /**
     * Ignore Register, Login and Swagger route from JWT token checking
     * 
     * @param request Request received from the client
     * @return boolean true if the request not need to be checkup for JWT Token else
     *         false.
     */
    public boolean allowRequestWithoutToken(HttpServletRequest request) {
        if (request.getRequestURI().contains("auth/signUp") || request.getRequestURI().contains("auth/signIn")
                || request.getRequestURI().contains("swagger") || request.getRequestURI().contains("api-docs")) {
            return true;
        }
        return false;
    }

}