package com.timemanager.core.src.dto;

public class TeamRequestDto {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeamRequestDto() {
    }

    public TeamRequestDto(String name) {
        this.name = name;
    }

}