package com.timemanager.core.src.dto;

public class WorkingTimeResponseDto {

    String workingTimeID;

    String start;

    String end;

    String userId;

    public String getWorkingTimeID() {
        return workingTimeID;
    }

    public void setWorkingTimeID(String workingTimeID) {
        this.workingTimeID = workingTimeID;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}