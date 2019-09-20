package com.timemanager.core.src.dto;

public class UpdateTeamRequestDto {

    private String name;

    public UpdateTeamRequestDto(String name) {
        this.setName(name);
    }

    public UpdateTeamRequestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}