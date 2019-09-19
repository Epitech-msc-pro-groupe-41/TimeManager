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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    public UserResponseDto signUp(RegisterRequestDto in) {

        if (in.getPassword() != null && !in.getPassword().isEmpty()) {
            return userService.createUser(new UserRequestDto(in.getEmail(), in.getFirstName(), in.getLastName(),
            passwordEncoder.encode(in.getPassword()), UserType.Employee));    
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Password cannot be empty");
        }
    }

    public AuthResponseDto signIn(LoginRequestDto in) {
        User user = userService.getUserByEmail(in.getEmail(), true);
        if (passwordEncoder.matches(in.getPassword(), user.getPassword())) {
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