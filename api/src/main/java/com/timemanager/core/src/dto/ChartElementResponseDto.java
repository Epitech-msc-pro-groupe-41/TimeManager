package com.timemanager.core.src.dto;

public class ChartElementResponseDto {

    String date;

    Integer hours;

    public ChartElementResponseDto() {}

    public ChartElementResponseDto(String date, Integer hours) {
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