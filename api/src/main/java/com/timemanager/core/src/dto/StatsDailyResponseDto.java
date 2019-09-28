package com.timemanager.core.src.dto;

import java.util.ArrayList;

public class StatsDailyResponseDto {

    ArrayList<StatsElementResponseDto> dailyHours;

    public ArrayList<StatsElementResponseDto> getDailyHours() {
        return dailyHours;
    }

    public void setDailyHours(ArrayList<StatsElementResponseDto> dailyHours) {
        this.dailyHours = dailyHours;
    }
}