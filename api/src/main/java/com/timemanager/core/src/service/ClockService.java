package com.timemanager.core.src.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.timemanager.core.src.dto.ClockResponseDto;
import com.timemanager.core.src.model.Clock;
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
    UserService userService;

    public String dateLongToString(long dateTime) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(dateTime);
        return formatter.format(date);
    }

    public ClockResponseDto getClock(String userID, boolean trigger) {
        List<Clock> clocks= null;
        ClockResponseDto res = null;
        if (userID.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");     
        } else {
            if (userService.getUserById(userID) != null) {
                Query query = new Query();
                query.addCriteria(Criteria.where("userId").is(userID).and("status").is(true));            
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
                res.setTime(dateLongToString(clock.getTime()));
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
            if (userService.getUserById(userID) != null) {
                ClockResponseDto clock = getClock(userID, false);
                if (clock == null) {
                    response =  new Clock();
                    response.setClockID("CLK" + UUID.randomUUID().toString());
                    response.setStatus(true);
                    response.setTime(System.currentTimeMillis());
                    response.setUserID(userID);    
                } else {
                    Query query = new Query();
                    query.addCriteria(Criteria.where("clockID").is(clock.getClockID()).and("userId").is(clock.getUserID()));            
                    List<Clock> existClock = clockRepository.find(query);   
                    if (existClock != null && existClock.size() > 0) 
                        response = existClock.get(0);
                    response.setStatus(false);
                }
                clockRepository.create(response);
            }
        }
        return response;
    }
}