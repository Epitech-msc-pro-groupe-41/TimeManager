package com.timemanager.core.src.dto;


public class TeamDto {
    
    private String teamID;

    private String name;

    private long createDate;

    private String managerID;

    public TeamDto(String teamID, String name, long createDate, String managerID) {
        this.teamID = teamID;
        this.name = name;
        this.createDate = createDate;
        this.managerID = managerID;
    }

    public TeamDto() {
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }


}