package com.timemanager.core.src.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "timemanager_clocks")
/**
 * Clock model, fields of table timemanager_clocks in mongoDB
 */
public class Clock {

    @Id
    private String id;

    @Indexed(unique = true)
    private String clockID;

    @NotNull(message = "Time must not be null")
    private long time;

    @NotNull(message = "Status must not be null")
    private boolean status;

    @NotNull(message = "UserID must not be null")
    private String userID;

    @NotNull(message = "Working time must not be null")
    private String workingTimeID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClockID() {
        return clockID;
    }

    public void setClockID(String clockID) {
        this.clockID = clockID;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getWorkingTimeID() {
        return workingTimeID;
    }

    public void setWorkingTimeID(String workingTimeID) {
        this.workingTimeID = workingTimeID;
    }
}