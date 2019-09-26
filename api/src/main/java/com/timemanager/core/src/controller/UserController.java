package com.timemanager.core.src.controller;

import java.util.List;

import com.timemanager.core.src.dto.UserResponseDto;
import com.timemanager.core.src.dto.UserUpdateRequestDto;
import com.timemanager.core.src.dto.UserRequestDto;
import com.timemanager.core.src.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Users", consumes = MediaType.APPLICATION_JSON_VALUE, tags = "Users")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "Get user by userID")
    @RequestMapping(method = RequestMethod.GET, value = "/{userID}")
    public UserResponseDto getUser(@PathVariable("userID") String userID) {

        return userService.convertToResponseDto(userService.getUserById(userID, true));
    }

    @ApiOperation(value = "Get all user with them informations")
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public List<UserResponseDto> getAllUsers() {

        return userService.convertToListResponseDto(userService.getAllUsers());
    }

    @ApiOperation(value = "Create a new user")
    @RequestMapping(method = RequestMethod.POST)
    public UserResponseDto createUser(@RequestBody UserRequestDto user) {
        return userService.createUser(user);
    }

    /*@ApiOperation(value = "Change role")
    @RequestMapping(method = RequestMethod.POST, value = "/ChangeRole/{userID}")
    public void changeRole(@PathVariable(name = "userID", required = true) String userID) {

    }*/

    @ApiOperation(value = "Update user")
    @RequestMapping(method = RequestMethod.PUT, value = "/{userID}")
    public void updateUser(@PathVariable(name = "userID", required = true) String userID,
            @RequestBody UserUpdateRequestDto user) {
        userService.updateUser(userID, user);
    }

    @ApiOperation(value = "Delete user")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{userID}")
    public void deleteUser(@PathVariable(name = "userID", required = true) String userID) {
        userService.deleteUser(userID);
    }
}