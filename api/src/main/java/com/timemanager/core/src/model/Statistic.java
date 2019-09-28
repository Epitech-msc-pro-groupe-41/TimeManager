package com.timemanager.core.src.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "timemanager_statistic")
/**
 * Clock model, fields of table timemanager_clocks in mongoDB
 */
public class Statistic{

    @Id
    private String id;
    private String teamID;
    private String userID;
    private long stat;

    public String getTeamID() {
        return teamID;
    }
    public String getUserID() {
        return userID;
    }
    public long getStat(){
        return stat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setStat(long stat) {
        this.stat = stat;
    }




}