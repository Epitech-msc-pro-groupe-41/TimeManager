package com.timemanager.core.src.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.timemanager.core.common.Utils;
import com.timemanager.core.src.dto.AuthResponseDto;
import com.timemanager.core.src.dto.LoginRequestDto;
import com.timemanager.core.src.dto.RegisterRequestDto;
import com.timemanager.core.src.service.AuthService;
import com.timemanager.core.src.service.TokenService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Authentication", consumes = MediaType.APPLICATION_JSON_VALUE, tags = "Authentication")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    TokenService tokenService;

    @ApiOperation(value = "Register a new user")
    @RequestMapping(method = RequestMethod.POST, value = "/signUp")
    public AuthResponseDto signUp(@RequestBody RegisterRequestDto in) {
        
        Utils.preventInjection(in.getEmail());
        Utils.preventInjection(in.getFirstName());
        Utils.preventInjection(in.getLastName());
        Utils.preventInjection(in.getPassword());

        return authService.signUp(in);
    }

    @ApiOperation(value = "Login user with email and password")
    @RequestMapping(method = RequestMethod.POST, value = "/signIn")
    public AuthResponseDto signIn(@RequestBody LoginRequestDto in) {

        Utils.preventInjection(in.getEmail());
        Utils.preventInjection(in.getPassword());

        return authService.signIn(in);
    }

    @ApiOperation(value = "Logout user")
    @RequestMapping(method = RequestMethod.GET, value = "/signOut")
    public void signOut(HttpServletRequest req) throws IOException {

        authService.signOut(req.getAttribute("userID").toString());
    }

}