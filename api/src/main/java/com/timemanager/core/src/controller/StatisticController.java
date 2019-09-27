package com.timemanager.core.src.controller;

import com.timemanager.core.annotation.Role;
import com.timemanager.core.common.Utils;
import com.timemanager.core.src.dto.StatisticResponseDto;
import com.timemanager.core.src.dto.StatsDailyResponseDto;
import com.timemanager.core.src.service.StatisticService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Stats", consumes = MediaType.APPLICATION_JSON_VALUE, tags = "Stats")
@RestController
@RequestMapping("/stats")
public class StatisticController {

    @Autowired
    StatisticService statisticService;

    @ApiOperation(value = "Get daily stats by userID")
    @Role(access = { "Admin", "Employee", "Manager" })
    @RequestMapping(method = RequestMethod.GET, value = "/user/daily/{userID}")
    public StatsDailyResponseDto getDayStatsUser(@PathVariable(name = "userID", required = true) String userID) {

        Utils.preventInjection(userID);

        return statisticService.getUserStatisticByDay(userID);
    }

    @ApiOperation(value = "Get month stats by userID")
    @Role(access = { "Admin", "Employee", "Manager" })
    @RequestMapping(method = RequestMethod.GET, value = "/user/monthly/{userID}")
    public StatisticResponseDto getMonthStatsUser(@PathVariable(name = "userID", required = true) String userID,
            @RequestParam(name = "start", required = true) long start,
            @RequestParam(name = "end", required = true) long end) {

        Utils.preventInjection(userID);
        Utils.preventInjection(String.valueOf(start));
        Utils.preventInjection(String.valueOf(end));

        return statisticService.getUserStatisticByPeriod(userID, start, end);
    }

    @ApiOperation(value = "Get daily stats by teamID")
    @Role(access = { "Admin", "Manager" })
    @RequestMapping(method = RequestMethod.GET, value = "/team/daily/{teamID}")
    public StatisticResponseDto getDayStatsTeam(@PathVariable(name = "teamID", required = true) String teamID,
            @RequestParam(name = "date", required = true) long date) {

        Utils.preventInjection(teamID);
        Utils.preventInjection(String.valueOf(date));

        return statisticService.getTeamStatisticByDay(teamID, date);
    }

    @ApiOperation(value = "Get month stats by teamID")
    @Role(access = { "Admin", "Manager" })
    @RequestMapping(method = RequestMethod.GET, value = "/team/monthly/{teamID}")
    public StatisticResponseDto getStats(@PathVariable(name = "teamID", required = true) String teamID,
            @RequestParam(name = "start", required = true) long start,
            @RequestParam(name = "end", required = true) long end) {

        Utils.preventInjection(teamID);
        Utils.preventInjection(String.valueOf(start));
        Utils.preventInjection(String.valueOf(end));

        return statisticService.getTeamStatisticByPeriod(teamID, start, end);
    }

}