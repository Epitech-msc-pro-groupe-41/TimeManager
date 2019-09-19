package com.timemanager.core.src.dto;

import com.timemanager.core.src.constant.UserType;

public class AuthResponseDto {
    
    String  token;

    String userID;

    UserType type;

    public AuthResponseDto() {}

    public AuthResponseDto(String token, String userID, UserType type) {
            this.userID = userID;
            this.token = token;
            this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}