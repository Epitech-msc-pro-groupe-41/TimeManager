package com.timemanager.core.src.dto;

import java.util.ArrayList;

public class StatsDailyResponseDto {

    ArrayList<StatsElementResponseDto> userDailyHours;

    public ArrayList<StatsElementResponseDto> getUserDailyHours() {
        return userDailyHours;
    }

    public void setUserDailyHours(ArrayList<StatsElementResponseDto> userDailyHours) {
        this.userDailyHours = userDailyHours;
    }

}