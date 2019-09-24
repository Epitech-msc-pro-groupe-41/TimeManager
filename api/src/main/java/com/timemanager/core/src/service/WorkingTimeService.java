package com.timemanager.core.src.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.timemanager.core.common.Utils;
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

    public String createWorkingTime(String userID, CreateWorkingTimeRequestDto in) {
        WorkingTime workingTime = new WorkingTime();
        workingTime.setStart(in.getStart());
        workingTime.setEnd(in.getEnd());
        workingTime.setUserID(userID);
        workingTime.setWorkingTimeID("WT" + UUID.randomUUID().toString());
        workingTimeRepository.create(workingTime);
        return workingTime.getWorkingTimeID();
    }

    public List<WorkingTimeResponseDto> getAllWorkingTimes(String userID) {
        List<WorkingTime> workingTimes = null;
        List<WorkingTimeResponseDto> response = new ArrayList<>();

        if (userID.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");  
        } else {

            Query query = new Query();
            query.addCriteria(new Criteria().andOperator(Criteria.where("userID").is(userID)));
            workingTimes = workingTimeRepository.find(query);
            if (workingTimes != null) {
                for (WorkingTime workingTime : workingTimes) {
                    WorkingTimeResponseDto data = new WorkingTimeResponseDto();
                    data.setEnd(workingTime.getEnd());
                    data.setStart(workingTime.getStart());
                    data.setUserID(workingTime.getUserID());
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
    
    public List<WorkingTimeResponseDto> getWorkingTimes(String userID, long start, long end){
        List<WorkingTime> workingTimes = null;
        List<WorkingTimeResponseDto> response = new ArrayList<>();

        if (userID.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");  
        } else {

            Query query = new Query();
            query.addCriteria(new Criteria().andOperator(Criteria.where("userID").is(userID)).orOperator(Criteria.where("start").is(start),
            Criteria.where("end").is(end)));
             workingTimes = workingTimeRepository.find(query);
             if (workingTimes != null) {
                for (WorkingTime workingTime : workingTimes) {
                    WorkingTimeResponseDto data = new WorkingTimeResponseDto();
                    data.setEnd(workingTime.getEnd());
                    data.setStart(workingTime.getStart());
                    data.setUserID(workingTime.getUserID());
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
        WorkingTime workingTime = getUniqueWorkingTime(userID, workingTimeID);
        WorkingTimeResponseDto response = new WorkingTimeResponseDto();
        if (workingTime != null) {
            response.setEnd(workingTime.getEnd());
            response.setStart(workingTime.getStart());
            response.setUserID(workingTime.getUserID());
            response.setWorkingTimeID(workingTime.getWorkingTimeID());
        }
        return response;
    }

    public WorkingTime getUniqueWorkingTime(String userID, String workingTimeID) {
        List<WorkingTime> workingTimes = null;

        if (userID.isEmpty() || workingTimeID.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");  
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where("userID").is(userID).and("workingTimeID").is(workingTimeID));
            workingTimes = workingTimeRepository.find(query);
            if (workingTimes != null && workingTimes.size() > 0) {
                return workingTimes.get(0);
            } else {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No working time found"); 
            }
        }
    }

    public void updateWorkingTime(String id, UpdateWorkingTimeRequestDto in) {
        if (id.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");  
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where("userID").is(in.getUserID()).and("workingTimeID").is(id));
            List<WorkingTime> workingTimes = workingTimeRepository.find(query);
            if (workingTimes != null) {
                WorkingTime workingTime = workingTimes.get(0);
                workingTime.setEnd(in.getEnd());
                workingTime.setStart(in.getStart());
                workingTimeRepository.update(workingTime);
            }  else {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No working time found"); 
            }          
        }
    }

    public void updateWorkingTime(WorkingTime workingTime) {
        workingTimeRepository.update(workingTime);
    }

    public void deleteWorkingTime(String id) {
        if (id.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");  
        } else {
            workingTimeRepository.delete("workingTimeID", id);
        }
    }

    public List<WorkingTime> convertToListWT(List <WorkingTimeResponseDto> wt) {
        List<WorkingTime> response = new ArrayList<>();

        if (wt != null) {
            for (WorkingTimeResponseDto work: wt) {
                response.add(convertToList(work));
            }    
        }

        return response;
    }

	public WorkingTime convertToList(WorkingTimeResponseDto workingtime) {
        WorkingTime response= new WorkingTime();
        if (response != null) {
            response.setEnd(workingtime.getEnd());
            response.setStart(workingtime.getStart());
            response.setUserID(workingtime.getUserID());
            response.setWorkingTimeID(workingtime.getWorkingTimeID());
        }

        return response;    
	}
}