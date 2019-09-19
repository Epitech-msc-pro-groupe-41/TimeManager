package com.timemanager.core.src.dto;

public class CreateWorkingTimeRequestDto {
    
    long start;

    long end;

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public CreateWorkingTimeRequestDto() {
    }

    public CreateWorkingTimeRequestDto(long start, long end) {
        this.start = start;
        this.end = end;
    }


}