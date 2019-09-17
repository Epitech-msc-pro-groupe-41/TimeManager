package com.timemanager.core.src.dto;

public class ClockResponseDto {

    String clockID;

    String time;

    Boolean status;

    String userID;

    public String getClockID() {
        return clockID;
    }

    public void setClockID(String clockID) {
        this.clockID = clockID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    
}