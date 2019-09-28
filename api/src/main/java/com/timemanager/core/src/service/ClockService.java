package com.timemanager.core.src.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.timemanager.core.src.dto.ClockResponseDto;
import com.timemanager.core.src.dto.CreateWorkingTimeRequestDto;
import com.timemanager.core.src.model.Clock;
import com.timemanager.core.src.model.WorkingTime;
import com.timemanager.core.src.repository.ClockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClockService {

    @Autowired
    ClockRepository clockRepository;

    @Autowired
    WorkingTimeService workingTimeService;

    @Autowired
    UserService userService;

    public ClockResponseDto getClock(String userID, boolean trigger) {
        List<Clock> clocks= null;
        ClockResponseDto res = null;
        if (userID.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");     
        } else {
            if (userService.getUserById(userID, true) != null) {
                Query query = new Query();
                query.addCriteria(Criteria.where("userID").is(userID).and("status").is(true));            
                clocks = clockRepository.find(query);
            }
            if (clocks == null) {
                return null;    
            } 
            Clock clock = null;
            long compareDate = 0;
            for (Clock c : clocks) {
                if (c.getTime() > compareDate) {
                    clock = c;
                }
            }

            if (clock != null) {
                res = new ClockResponseDto();
                res.setClockID(clock.getClockID());
                res.setStatus(clock.isStatus());
                res.setTime(clock.getTime());
                res.setUserID(clock.getUserID());
    
            }
        }

        if (res == null && trigger) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "No work period is in progress");     
        }
        return res;
    }

    public Clock createClock(String userID) {
        Clock response = null;

        if (userID.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");     
        } else {
            if (userService.getUserById(userID, true) != null) {
                long time = System.currentTimeMillis();

                ClockResponseDto clock = getClock(userID, false);
                if (clock == null) {
                    /** Create new clock if not exist so it's a clock in */
                    response = new Clock();
                    response.setClockID("CLK" + UUID.randomUUID().toString());
                    response.setStatus(true);
                    response.setTime(time);
                    response.setUserID(userID);    
                    String workingTimeID = workingTimeService.createWorkingTime(userID, new CreateWorkingTimeRequestDto(time, -1));
                    response.setWorkingTimeID(workingTimeID);
                    clockRepository.create(response);
                } else {
                    /** Clock already exist so it's a clock out */
                    Query query = new Query();
                    query.addCriteria(Criteria.where("clockID").is(clock.getClockID()).and("userID").is(clock.getUserID()));            
                    List<Clock> existClock = clockRepository.find(query);   
                    if (existClock != null && existClock.size() > 0)
                        response = existClock.get(0);
                    response.setStatus(false);
                    WorkingTime workingTime = workingTimeService.getUniqueWorkingTime(userID, response.getWorkingTimeID());
                    workingTime.setEnd(time);
                    workingTimeService.updateWorkingTime(workingTime);
                    clockRepository.update(response);
                }
            }
        }
        return response;
    }
    public Clock convertToClock(ClockResponseDto c) {
        Clock clock = new Clock();
        if (c != null) {
                clock = convertToclock(c);
            }    
        

        return clock;
    }
    public Clock convertToclock(ClockResponseDto c) {
        Clock response = new Clock();
        if (response != null) {
            response.setClockID(c.getClockID());
            response.setStatus(c.getStatus());
            response.setTime(c.getTime());
            response.setUserID(c.getUserID());
        }

        return response;    
    }
    
    public List<Clock> getAllClocks(String userID) {
        List<Clock> clocks = null;

        if (userID.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");  
        } else {
            Query query = new Query();
            query.addCriteria(new Criteria().andOperator(Criteria.where("userID").is(userID)));
            clocks = clockRepository.find(query);
        }

        return clocks;
    }
}