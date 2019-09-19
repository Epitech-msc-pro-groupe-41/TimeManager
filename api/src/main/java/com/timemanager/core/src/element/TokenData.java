package com.timemanager.core.src.element;

/**
 * TokenData is an element which is used to store decoded data from JWT token
 */
public class TokenData {

    String userID;

    String xsrfToken;

    long expireDate;

    public TokenData() {}

    public TokenData(String userID, String xsrfToken, long expireDate) {
        this.userID = userID;
        this.xsrfToken = xsrfToken;
        this.expireDate = expireDate;
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
}