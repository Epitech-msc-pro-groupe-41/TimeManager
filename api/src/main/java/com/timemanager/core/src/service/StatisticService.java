package com.timemanager.core.src.service;

import com.timemanager.core.src.dto.WorkingTimeResponseDto;

import java.text.SimpleDateFormat;
import java.util.*;

import com.timemanager.core.common.Utils;
import com.timemanager.core.src.dto.StatisticResponseDto;
import com.timemanager.core.src.dto.StatsDailyResponseDto;
import com.timemanager.core.src.dto.StatsElementResponseDto;
import com.timemanager.core.src.dto.UserResponseDto;
import com.timemanager.core.src.service.TeamMemberService;
import com.timemanager.core.src.service.WorkingTimeService;
import com.timemanager.core.src.service.ClockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StatisticService {

    @Autowired
    UserService userService;

    @Autowired
    TeamService teamService;

    @Autowired
    TeamMemberService teamMemberService;

    @Autowired
    WorkingTimeService workingTimeService;

    @Autowired
    ClockService clockService;

    public StatsDailyResponseDto getTeamStatisticByPeriod(String teamID, long start, long end) {
        StatsDailyResponseDto response = new StatsDailyResponseDto();
        ArrayList<StatsElementResponseDto> userDailyHours = new ArrayList<>();
        Map<String, String> data = new LinkedHashMap<>();
        

        List<UserResponseDto> teamMember = teamMemberService.getAllMember(teamID);
        for (UserResponseDto user : teamMember) {
            List<WorkingTimeResponseDto> resultat = workingTimeService.getWorkingTimes(user.getUserID(), start, end);
            for (WorkingTimeResponseDto w : resultat) {
                try {
                    String date = Utils.dateLongToString(w.getStart());
                    int hours = (int)((w.getEnd() - w.getStart()) / (60 * 60 * 1000));
                    if (hours > 0) {
                        if (!data.containsKey(date)) {
                            data.put(date, String.valueOf(hours) + ';' + 1);
                        } else {
                            int oldTime = Integer.valueOf(data.get(date).split(";")[0]);
                            int oldCount = Integer.valueOf(data.get(date).split(";")[1]);
                            oldCount++;
                            data.replace(date, String.valueOf(oldTime + hours) + ";" + oldCount);
                        }
                    }
                } catch (Exception e) {
                    throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Invalid format for date");
                }
            }
        }

        if (!data.isEmpty()) {
            for(Map.Entry<String, String> entry : data.entrySet()) {
                int hours = Integer.valueOf(entry.getValue().split(";")[0]);
                int countMember = Integer.valueOf(entry.getValue().split(";")[1]);

                userDailyHours.add(new StatsElementResponseDto(entry.getKey(), Math.round(hours/countMember)));
            }
        }
        response.setDailyHours(userDailyHours);
        return response;
    }

    public StatsDailyResponseDto getTeamStatisticByDay(String teamID, long start, long end) {
        StatsDailyResponseDto response = new StatsDailyResponseDto();
        ArrayList<StatsElementResponseDto> userDailyHours = new ArrayList<>();
        Map<String, String> data = new LinkedHashMap<>();
        

        List<UserResponseDto> teamMember = teamMemberService.getAllMember(teamID);
        for (UserResponseDto user : teamMember) {
            List<WorkingTimeResponseDto> resultat = workingTimeService.getWorkingTimes(user.getUserID(), start, end);
            for (WorkingTimeResponseDto w : resultat) {
                try {
                    String date = Utils.dateLongToString(w.getStart());
                    int hours = (int)((w.getEnd() - w.getStart()) / (60 * 60 * 1000));
                    if (hours > 0) {
                        if (!data.containsKey(date)) {
                            data.put(date, String.valueOf(hours) + ';' + 1);
                        } else {
                            int oldTime = Integer.valueOf(data.get(date).split(";")[0]);
                            int oldCount = Integer.valueOf(data.get(date).split(";")[1]);
                            oldCount++;
                            data.replace(date, String.valueOf(oldTime + hours) + ";" + oldCount);
                        }
                    }
                } catch (Exception e) {
                    throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Invalid format for date");
                }
            }
        }

        if (!data.isEmpty()) {
            for(Map.Entry<String, String> entry : data.entrySet()) {
                int hours = Integer.valueOf(entry.getValue().split(";")[0]);
                int countMember = Integer.valueOf(entry.getValue().split(";")[1]);

                userDailyHours.add(new StatsElementResponseDto(entry.getKey(), Math.round(hours/countMember)));
            }
        }
        response.setDailyHours(userDailyHours);
        return response;
    }

    public StatsDailyResponseDto getUserStatisticByDay(String userID, long start, long end) {
        StatsDailyResponseDto response = new StatsDailyResponseDto();
        ArrayList<StatsElementResponseDto> userDailyHours = new ArrayList<>();
        Map<String, Integer> data = new LinkedHashMap<>();
        List<WorkingTimeResponseDto> workingTimes = workingTimeService.getAllWorkingTimes(userID);
        for (WorkingTimeResponseDto w : workingTimes) {
            try {
                String date = Utils.dateLongToString(w.getStart());
                int hours = (int)((w.getEnd() - w.getStart()) / (60 * 60 * 1000));
                if (!data.containsKey(date)) {
                    data.put(date, hours);
                } else {
                    data.replace(date, data.get(date) + hours);
                }
            } catch (Exception e) {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Invalid format for date");
            }
        }

        if (!data.isEmpty()) {
            for(Map.Entry<String, Integer> entry : data.entrySet()) {
                userDailyHours.add(new StatsElementResponseDto(entry.getKey(), entry.getValue()));
            }
        }
        response.setDailyHours(userDailyHours);
        return response;
    }

    public StatsDailyResponseDto getUserStatisticByPeriod(String userID, long start, long end) {
        StatsDailyResponseDto response = new StatsDailyResponseDto();
        ArrayList<StatsElementResponseDto> userDailyHours = new ArrayList<>();
        Map<String, Integer> data = new LinkedHashMap<>();
        List<WorkingTimeResponseDto> workingTimes = workingTimeService.getAllWorkingTimes(userID);
        for (WorkingTimeResponseDto w : workingTimes) {
            try {
                String date = Utils.dateLongToString(w.getStart());
                int hours = (int)((w.getEnd() - w.getStart()) / (60 * 60 * 1000));
                if (!data.containsKey(date)) {
                    data.put(date, hours);
                } else {
                    data.replace(date, data.get(date) + hours);
                }
            } catch (Exception e) {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Invalid format for date");
            }
        }

        if (!data.isEmpty()) {
            for(Map.Entry<String, Integer> entry : data.entrySet()) {
                userDailyHours.add(new StatsElementResponseDto(entry.getKey(), entry.getValue()));
            }
        }
        response.setDailyHours(userDailyHours);
        return response;
    }

}
