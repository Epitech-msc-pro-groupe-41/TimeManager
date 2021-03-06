package com.timemanager.core.src.dto;

import com.timemanager.core.src.constant.UserType;

public class UserRequestDto {
    
    String email;

    String firstName;

    String lastName;

    String password;

    UserType type;

    public UserRequestDto() {}

    public UserRequestDto(String email, String firstName, String lastName, String password, UserType type) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

}