package com.timemanager.core.src.controller;

import java.util.List;

import com.timemanager.core.src.dto.CreateWorkingTimeRequestDto;
import com.timemanager.core.src.dto.UpdateWorkingTimeRequestDto;
import com.timemanager.core.src.dto.WorkingTimeResponseDto;
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
    
    @ApiOperation(value = "Get all working time of a user filter by start and end time" )
    @RequestMapping(method = RequestMethod.GET, value = "filter/{userID}")
    public List<WorkingTimeResponseDto> getWorkingTimes(
        @PathVariable(name = "userID", required = true) String userID,
        @RequestParam(name = "start", required = true) String start,
        @RequestParam(name = "end", required = true) String end) {
        
        return workingTimeService.getWorkingTimes(userID, start, end);
    }

    @ApiOperation(value = "Get all working time by userID" )
    @RequestMapping(method = RequestMethod.GET, value = "/{userID}")
    public List<WorkingTimeResponseDto> getAllWorkingTimes(
        @PathVariable(name = "userID", required = true) String userID) {        
        return workingTimeService.getAllWorkingTimes(userID);
    }


    @ApiOperation(value = "Get working time by userID and workingtimeID")
    @RequestMapping(method = RequestMethod.GET, value = "/{userID}/{workingtimeID}")
    public WorkingTimeResponseDto getWorkingTime(
        @PathVariable(name = "userID", required = true) String userID,
        @PathVariable(name = "workingtimeID", required = true) String workingtimeID) {
        return workingTimeService.getWorkingTime(userID, workingtimeID);
    }

    @ApiOperation(value = "Create a new working time")
    @RequestMapping(method = RequestMethod.POST, value = "/{userID}")
    public void createWorkingTime(
        @PathVariable(name = "userID", required = true) String userID,
        @RequestBody CreateWorkingTimeRequestDto in ) {
            workingTimeService.createWorkingTime(userID, in);
    }

    @ApiOperation(value = "Update working time")
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public void updateWorkingTime(
        @PathVariable(name = "id", required = true) String id,
        @RequestBody UpdateWorkingTimeRequestDto workingTime ) {
            workingTimeService.updateWorkingTime(id, workingTime);
    }

    @ApiOperation(value = "Delete working time")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deleteWorkingTime(
        @PathVariable(name = "id", required = true) String id) {
            workingTimeService.deleteWorkingTime(id);
    }
}