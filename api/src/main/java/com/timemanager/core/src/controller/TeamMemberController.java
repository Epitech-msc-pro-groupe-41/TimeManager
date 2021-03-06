package com.timemanager.core.src.controller;

import java.util.List;

import com.timemanager.core.annotation.Role;
import com.timemanager.core.common.Utils;
import com.timemanager.core.src.dto.TeamMemberRequestDto;
import com.timemanager.core.src.dto.UserResponseDto;
import com.timemanager.core.src.service.TeamMemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Team members", consumes = MediaType.APPLICATION_JSON_VALUE, tags = "Team members")
@RestController
@RequestMapping("/teamMembers")
public class TeamMemberController {

    @Autowired
    TeamMemberService teamMemberService;

    @ApiOperation(value = "Add user to a team")
    @Role(access = { "Admin", "Manager" })
    @RequestMapping(method = RequestMethod.POST)
    public void addMember(@RequestBody TeamMemberRequestDto in) {
        Utils.preventInjection(in.getTeamID());
        Utils.preventInjection(in.getUserID());

        teamMemberService.addMember(in);
    }

    @ApiOperation(value = "Remove user from a team")
    @Role(access = { "Admin", "Manager" })
    @RequestMapping(method = RequestMethod.DELETE, value = "/{teamID}/{userID}")
    public void removeMember(@PathVariable(name = "teamID", required = true) String teamID,
            @PathVariable(name = "userID", required = true) String userID) {
        Utils.preventInjection(teamID);
        Utils.preventInjection(userID);

        teamMemberService.removeMember(teamID, userID);
    }

    @ApiOperation(value = "Get all members of a team")
    @Role(access = { "Admin", "Manager" })
    @RequestMapping(method = RequestMethod.GET, value = "/{teamID}")
    public List<UserResponseDto> getAllMember(@PathVariable(name = "teamID", required = true) String teamID) {
        Utils.preventInjection(teamID);

        return teamMemberService.getAllMember(teamID);
    }

}