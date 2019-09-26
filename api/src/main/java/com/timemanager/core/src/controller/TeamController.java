package com.timemanager.core.src.controller;

import java.util.List;

import com.timemanager.core.common.Utils;
import com.timemanager.core.src.dto.TeamDto;
import com.timemanager.core.src.dto.TeamRequestDto;
import com.timemanager.core.src.service.TeamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Teams", consumes = MediaType.APPLICATION_JSON_VALUE, tags = "Teams")
@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    TeamService teamService;

    @ApiOperation(value = "Get team by teamID")
    @RequestMapping(method = RequestMethod.GET, value = "/{teamID}")
    public TeamDto getTeam(@PathVariable(name = "teamID", required = true) String teamID) {
        Utils.preventInjection(teamID);

        return teamService.getTeam(teamID);
    }

    @ApiOperation(value = "Get all teams by managerID")
    @RequestMapping(method = RequestMethod.GET)
    public List<TeamDto> getTeamsByManager(@RequestParam(name = "managerID", required = true) String managerID) {
        Utils.preventInjection(managerID);

        return teamService.getTeamsByManager(managerID);
    }

    @ApiOperation(value = "Create a new team")
    @RequestMapping(method = RequestMethod.POST, value = "/{managerID}")
    public void createTeam(@RequestBody TeamRequestDto in,
            @PathVariable(name = "managerID", required = true) String managerID) {
        Utils.preventInjection(in.getName());
        Utils.preventInjection(managerID);

        teamService.createTeam(in, managerID);
    }

    @ApiOperation(value = "Update a team")
    @RequestMapping(method = RequestMethod.PUT, value = "/{teamID}")
    public void updateTeam(@PathVariable(name = "teamID", required = true) String teamID,
            @RequestBody TeamRequestDto in) {
        Utils.preventInjection(in.getName());
        Utils.preventInjection(teamID);

        teamService.updateTeam(teamID, in);
    }

    @ApiOperation(value = "delete a team")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{teamID}")
    public void deleteTeam(@PathVariable(name = "teamID", required = true) String teamID) {
        Utils.preventInjection(teamID);

        teamService.deleteTeam(teamID);
    }
}