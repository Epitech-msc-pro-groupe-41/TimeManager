package com.timemanager.core.src.dto;

import java.util.ArrayList;

public class StatsDailyResponseDto {

    ArrayList<StatsElementResponseDto> data;

    public ArrayList<StatsElementResponseDto> getData() {
        return data;
    }

    public void setData(ArrayList<StatsElementResponseDto> data) {
        this.data = data;
    }

}