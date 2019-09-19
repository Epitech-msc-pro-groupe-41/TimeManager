package com.timemanager.core.src.dto;

import java.util.ArrayList;

public class ChartResponseDto {

    ArrayList<ChartElementResponseDto> charData;

    public ArrayList<ChartElementResponseDto> getCharData() {
        return charData;
    }

    public void setCharData(ArrayList<ChartElementResponseDto> charData) {
        this.charData = charData;
    }


}