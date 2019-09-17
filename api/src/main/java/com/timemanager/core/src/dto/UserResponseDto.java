package com.timemanager.core.src.dto;

import com.timemanager.core.src.constant.UserType;

public class UserResponseDto {
    
    String userID;

    String email;

    String firstName;

    String lastName;

    UserType type;

    public UserResponseDto() {}

    public UserResponseDto(String userID, String email, 
        String firstName, String lastName, UserType type) {
            this.userID = userID;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
            this.type = type;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

}