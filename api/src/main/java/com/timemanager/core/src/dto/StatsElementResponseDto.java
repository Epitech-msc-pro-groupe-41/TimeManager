package com.timemanager.core.src.dto;

public class StatsElementResponseDto {

    String date;

    Integer hours;

    public StatsElementResponseDto() {}

    public StatsElementResponseDto(String date, Integer hours) {
        this.date = date;
        this.hours = hours;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

}