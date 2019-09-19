package com.timemanager.core.src.controller;

import com.timemanager.core.src.dto.ChartResponseDto;
import com.timemanager.core.src.service.ChartManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value = "ChartManager", consumes = MediaType.APPLICATION_JSON_VALUE, tags = "ChartManager")
@RestController
@RequestMapping("/chartManager")
public class ChartManagerController {

    @Autowired
    ChartManagerService chartManagerService;
    
    @ApiOperation(value = "Get/Refresh data for charts (chart 1 : working hours by day")
    @RequestMapping(method = RequestMethod.GET, value = "/{userID}")
    public ChartResponseDto getChartData(
        @PathVariable(name = "userID", required = true) String userID) {

        return chartManagerService.getChartData(userID);
    }
}