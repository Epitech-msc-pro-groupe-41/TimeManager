package com.timemanager.core.src.dto;

public class StatisticResponseDto {

    String statisticID;

    long duration;

    String userID;

    String teamID;

    public String getStatisticID() {
        return statisticID;
    }

    public void setStatisticID(String statisticID) {
        this.statisticID = statisticID;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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