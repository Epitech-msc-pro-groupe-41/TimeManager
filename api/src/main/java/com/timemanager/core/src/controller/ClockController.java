package com.timemanager.core.src.controller;

import com.timemanager.core.src.dto.ClockResponseDto;
import com.timemanager.core.src.model.Clock;
import com.timemanager.core.src.service.ClockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Clocks", consumes = MediaType.APPLICATION_JSON_VALUE, tags = "Clocks")
@RestController
@RequestMapping("/clocks")
public class ClockController {

    @Autowired
    ClockService clockService;

    @ApiOperation(value = "Get clocks by userID")
    @RequestMapping(method = RequestMethod.GET, value = "/{userID}")
    public ClockResponseDto getClock(
        @PathVariable(name = "userID", required = true) String userID) {

        return clockService.getClock(userID, true);
    }

    @ApiOperation(value = "Create a new clock")
    @RequestMapping(method = RequestMethod.POST, value = "/{userID}")
    public Clock createClock(
        @PathVariable(name = "userID", required = true) String userID) {
            return clockService.createClock(userID);
    }
}