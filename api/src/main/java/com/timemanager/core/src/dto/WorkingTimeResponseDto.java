package com.timemanager.core.src.dto;

public class WorkingTimeResponseDto {

    String workingTimeID;

    long start;

    long end;

    String userID;

    public String getWorkingTimeID() {
        return workingTimeID;
    }

    public void setWorkingTimeID(String workingTimeID) {
        this.workingTimeID = workingTimeID;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}