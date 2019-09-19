package com.timemanager.core.src.element;

/**
 * TokenData is an element which is used to store decoded data from JWT token
 */
public class TokenData {

    String userID;

    String xsrfToken;

    String role;

    long expireDate;

    public TokenData() {}

    public TokenData(String userID, String xsrfToken, long expireDate, String role) {
        this.userID = userID;
        this.xsrfToken = xsrfToken;
        this.expireDate = expireDate;
        this.role = role;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getXsrfToken() {
        return xsrfToken;
    }

    public void setXsrfToken(String xsrfToken) {
        this.xsrfToken = xsrfToken;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}