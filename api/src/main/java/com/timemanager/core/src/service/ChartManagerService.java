package com.timemanager.core.src.service;

import java.util.ArrayList;
import java.util.Random;

import com.timemanager.core.src.dto.ChartElementResponseDto;
import com.timemanager.core.src.dto.ChartResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ChartManagerService {

    @Autowired
    UserService userService;

    private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

    public ChartResponseDto getChartData(String userID) {
        ChartResponseDto chartResponse = new ChartResponseDto();
        if (userID.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid parameters");
        } else {
            ArrayList<ChartElementResponseDto> char1 = new ArrayList<>();
            char1.add(new ChartElementResponseDto("2019-09-03 00:00:00", getRandomNumberInRange(0, 10)));
            char1.add(new ChartElementResponseDto("2019-09-04 00:00:00", getRandomNumberInRange(0, 10)));
            char1.add(new ChartElementResponseDto("2019-09-05 00:00:00", getRandomNumberInRange(0, 10)));
            char1.add(new ChartElementResponseDto("2019-09-06 00:00:00", getRandomNumberInRange(0, 10)));
            char1.add(new ChartElementResponseDto("2019-09-07 00:00:00", getRandomNumberInRange(0, 10)));
            char1.add(new ChartElementResponseDto("2019-09-08 00:00:00", getRandomNumberInRange(0, 10)));
            char1.add(new ChartElementResponseDto("2019-09-09 00:00:00", getRandomNumberInRange(0, 10)));
            chartResponse.setCharData(char1);
        }
        return chartResponse;
    }
}