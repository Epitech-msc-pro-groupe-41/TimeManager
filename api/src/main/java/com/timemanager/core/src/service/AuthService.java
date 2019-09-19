package com.timemanager.core.src.service;

import com.timemanager.core.src.constant.UserType;
import com.timemanager.core.src.dto.AuthResponseDto;
import com.timemanager.core.src.dto.LoginRequestDto;
import com.timemanager.core.src.dto.RegisterRequestDto;
import com.timemanager.core.src.dto.UserRequestDto;
import com.timemanager.core.src.dto.UserResponseDto;
import com.timemanager.core.src.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    public UserResponseDto signUp(RegisterRequestDto in) {

        return userService.createUser(new UserRequestDto(in.getEmail(), in.getFirstName(), in.getLastName(),
                in.getPassword(), UserType.Employee));
    }

    public AuthResponseDto signIn(LoginRequestDto in) {
        User user = userService.getUserByEmail(in.getEmail(), true);
        if (user.getPassword().equals(in.getPassword())) {
            String token = tokenService.createToken(user.getUserID());
            user.setToken(token);
            userService.updateUser(user);
            return new AuthResponseDto(token, user.getUserID(), UserType.valueOf(user.getType()));
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid email or password");
        }
    }

    public void signOut(String userID) {
        User user = userService.getUserById(userID, true);
        user.setToken(null);
        userService.updateUser(user);
    }
}