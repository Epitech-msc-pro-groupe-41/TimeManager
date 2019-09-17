package com.timemanager.core.src.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "timemanager_teammembers")
public class TeamMember {

    @Id
    private String id;

    @Indexed(unique = true)
    private String teamMemberID;

    private String userID;

    private String teamID;

    private long joinDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamMemberID() {
        return teamMemberID;
    }

    public void setTeamMemberID(String teamMemberID) {
        this.teamMemberID = teamMemberID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public long getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(long joinDate) {
        this.joinDate = joinDate;
    }

}