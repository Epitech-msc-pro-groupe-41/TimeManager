package com.timemanager.core.src.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.timemanager.core.annotation.Role;
import com.timemanager.core.common.Utils;
import com.timemanager.core.src.dto.CreateWorkingTimeRequestDto;
import com.timemanager.core.src.dto.UpdateWorkingTimeRequestDto;
import com.timemanager.core.src.dto.WorkingTimeResponseDto;
import com.timemanager.core.src.service.TokenService;
import com.timemanager.core.src.service.WorkingTimeService;

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

@Api(value = "Working Times", consumes = MediaType.APPLICATION_JSON_VALUE, tags = "Working Times")
@RestController
@RequestMapping("/workingtimes")
public class WorkingTimeController {

    @Autowired
    WorkingTimeService workingTimeService;

    @Autowired
    TokenService tokenService;

    @ApiOperation(value = "Get all working time of a user filter by start and end time")
    @Role(access = { "Employee", "Admin", "Manager" })
    @RequestMapping(method = RequestMethod.GET, value = "filter/{userID}")
    public List<WorkingTimeResponseDto> getWorkingTimes(@PathVariable(name = "userID", required = true) String userID,
            @RequestParam(name = "start", required = true) long start,
            @RequestParam(name = "end", required = true) long end) {
        Utils.preventInjection(userID);
        Utils.preventInjection(String.valueOf(end));
        Utils.preventInjection(String.valueOf(start));

        return workingTimeService.getWorkingTimes(userID, start, end);
    }

    @ApiOperation(value = "Get all working time by userID")
    @Role(access = { "Employee", "Admin", "Manager" })
    @RequestMapping(method = RequestMethod.GET, value = "/{userID}")
    public List<WorkingTimeResponseDto> getAllWorkingTimes(
            @PathVariable(name = "userID", required = true) String userID) throws IOException {
        Utils.preventInjection(userID);

        return workingTimeService.getAllWorkingTimes(userID);
    }

    @ApiOperation(value = "Get working time by userID and workingtimeID")
    @Role(access = { "Employee", "Admin", "Manager" })
    @RequestMapping(method = RequestMethod.GET, value = "/{userID}/{workingtimeID}")
    public WorkingTimeResponseDto getWorkingTime(@PathVariable(name = "userID", required = true) String userID,
            @PathVariable(name = "workingtimeID", required = true) String workingtimeID) {
        Utils.preventInjection(userID);
        Utils.preventInjection(workingtimeID);

        return workingTimeService.getWorkingTime(userID, workingtimeID);
    }

    @ApiOperation(value = "Create a new working time")
    @Role(access = { "Employee", "Admin", "Manager" })
    @RequestMapping(method = RequestMethod.POST, value = "/{userID}")
    public void createWorkingTime(@PathVariable(name = "userID", required = true) String userID,
            @RequestBody CreateWorkingTimeRequestDto in) {
        Utils.preventInjection(userID);
        Utils.preventInjection(String.valueOf(in.getStart()));
        Utils.preventInjection(String.valueOf(in.getEnd()));

        workingTimeService.createWorkingTime(userID, in);
    }

    @ApiOperation(value = "Update working time")
    @Role(access = { "Employee", "Admin", "Manager" })
    @RequestMapping(method = RequestMethod.PUT, value = "/{workingTimeID}")
    public void updateWorkingTime(@PathVariable(name = "workingTimeID", required = true) String workingTimeID,
            @RequestBody UpdateWorkingTimeRequestDto workingTime) {
        Utils.preventInjection(workingTimeID);
        Utils.preventInjection(String.valueOf(workingTime.getEnd()));
        Utils.preventInjection(String.valueOf(workingTime.getStart()));
        Utils.preventInjection(workingTime.getUserID());

        workingTimeService.updateWorkingTime(workingTimeID, workingTime);
    }

    @ApiOperation(value = "Delete working time")
    @Role(access = { "Employee", "Admin", "Manager" })
    @RequestMapping(method = RequestMethod.DELETE, value = "/{workingTimeID}")
    public void deleteWorkingTime(@PathVariable(name = "workingTimeID", required = true) String workingTimeID) {
        Utils.preventInjection(workingTimeID);

        workingTimeService.deleteWorkingTime(workingTimeID);
    }
}