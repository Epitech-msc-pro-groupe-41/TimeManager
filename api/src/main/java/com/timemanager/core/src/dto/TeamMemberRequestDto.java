package com.timemanager.core.src.dto;


public class TeamMemberRequestDto {
    
    private String userID;

    private String teamID;

    public TeamMemberRequestDto() {
    }

    public TeamMemberRequestDto(String userID, String teamID) {
        this.userID = userID;
        this.teamID = teamID;
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

}