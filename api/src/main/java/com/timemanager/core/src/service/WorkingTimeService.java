package com.timemanager.core.src.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.timemanager.core.src.dto.CreateWorkingTimeRequestDto;
import com.timemanager.core.src.dto.UpdateWorkingTimeRequestDto;
import com.timemanager.core.src.dto.WorkingTimeResponseDto;
import com.timemanager.core.src.model.WorkingTime;
import com.timemanager.core.src.repository.WorkingTimeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WorkingTimeService {

    @Autowired
    WorkingTimeRepository workingTimeRepository;

    @Autowired
    UserService userService;

    public String dateLongToString(long dateTime) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(dateTime);
        return formatter.format(date);
    }

    public long stringToLongDate(String date) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long dateLong = 0;
        try {
            dateLong = formatter.parse(date).getTime();
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");  
        }
        return dateLong;
    }

    public void createWorkingTime(String userID, CreateWorkingTimeRequestDto in) {
        WorkingTime workingTime = new WorkingTime();
        workingTime.setStart(stringToLongDate(in.getStart()));
        workingTime.setEnd(stringToLongDate(in.getEnd()));
        workingTime.setUserID(userID);
        workingTime.setWorkingTimeID("WT" + UUID.randomUUID().toString());
        workingTimeRepository.create(workingTime);
    }

    public List<WorkingTimeResponseDto> getAllWorkingTimes(String userID) {
        List<WorkingTime> workingTimes = null;
        List<WorkingTimeResponseDto> response = new ArrayList<>();

        if (userID.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");  
        } else {

            Query query = new Query();
            query.addCriteria(new Criteria().andOperator(Criteria.where("userId").is(userID)));
            workingTimes = workingTimeRepository.find(query);
            if (workingTimes != null) {
                for (WorkingTime workingTime : workingTimes) {
                    WorkingTimeResponseDto data = new WorkingTimeResponseDto();
                    data.setEnd(dateLongToString(workingTime.getEnd()));
                    data.setStart(dateLongToString(workingTime.getStart()));
                    data.setUserId(workingTime.getUserID());
                    data.setWorkingTimeID(workingTime.getWorkingTimeID());
                    response.add(data);
                }
            } else {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No working time found"); 
            }
        }

        return response;
    }


    public List<WorkingTimeResponseDto> getWorkingTimes(String userID, String start, String end){
        List<WorkingTime> workingTimes = null;
        List<WorkingTimeResponseDto> response = new ArrayList<>();

        if (userID.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");  
        } else {
            long startDate = stringToLongDate(start);
            long endDate = stringToLongDate(end);

            Query query = new Query();
            query.addCriteria(new Criteria().andOperator(Criteria.where("userId").is(userID)).orOperator(Criteria.where("start").is(startDate),
            Criteria.where("end").is(endDate)));
             workingTimes = workingTimeRepository.find(query);
             if (workingTimes != null) {
                for (WorkingTime workingTime : workingTimes) {
                    WorkingTimeResponseDto data = new WorkingTimeResponseDto();
                    data.setEnd(dateLongToString(workingTime.getEnd()));
                    data.setStart(dateLongToString(workingTime.getStart()));
                    data.setUserId(workingTime.getUserID());
                    data.setWorkingTimeID(workingTime.getWorkingTimeID());
                    response.add(data);
                }
            } else {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No working time found"); 
            }
        }

        return response;
    }

    public WorkingTimeResponseDto getWorkingTime(String userID, String workingTimeID) {
        List<WorkingTime> workingTime = null;
        WorkingTimeResponseDto response = new WorkingTimeResponseDto();

        if (userID.isEmpty() || workingTimeID.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");  
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(userID).and("workingTimeID").is(workingTimeID));
            workingTime = workingTimeRepository.find(query);
            if (workingTime != null) {
                response.setEnd(dateLongToString(workingTime.get(0).getEnd()));
                response.setStart(dateLongToString(workingTime.get(0).getStart()));
                response.setUserId(workingTime.get(0).getUserID());
                response.setWorkingTimeID(workingTime.get(0).getWorkingTimeID());
            } else {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No working time found"); 
            }
        }
        return response;
    }

    public void updateWorkingTime(String id, UpdateWorkingTimeRequestDto in) {
        if (id.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");  
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(in.getUserID()).and("workingTimeID").is(id));
            List<WorkingTime> workingTimes = workingTimeRepository.find(query);
            if (workingTimes != null) {
                WorkingTime workingTime = workingTimes.get(0);
                workingTime.setEnd(stringToLongDate(in.getEnd()));
                workingTime.setStart(stringToLongDate(in.getStart()));
                workingTimeRepository.update(workingTime);
            }  else {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No working time found"); 
            }          
        }
    }

    public void deleteWorkingTime(String id) {
        if (id.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");  
        } else {
            workingTimeRepository.delete("workingTimeID", id);
        }
    }
}