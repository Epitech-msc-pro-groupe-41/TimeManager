package com.timemanager.core.src.service;

import com.timemanager.core.src.dto.WorkingTimeResponseDto;

import java.text.SimpleDateFormat;
import java.util.*;

import com.timemanager.core.common.Utils;
import com.timemanager.core.src.dto.StatisticResponseDto;
import com.timemanager.core.src.dto.StatsDailyResponseDto;
import com.timemanager.core.src.dto.StatsElementResponseDto;
import com.timemanager.core.src.dto.UserResponseDto;
import com.timemanager.core.src.repository.TeamMemberRepository;
import com.timemanager.core.src.service.TeamMemberService;
import com.timemanager.core.src.service.WorkingTimeService;
import com.timemanager.core.src.repository.WorkingTimeRepository;
import com.timemanager.core.src.service.ClockService;
import com.timemanager.core.src.repository.ClockRepository;

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

    public StatisticResponseDto getTeamStatisticByPeriod(String teamID, long start, long end) {
        StatisticResponseDto statDto = new StatisticResponseDto();
        int nbrdate = 0;
        long total = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        List<UserResponseDto> teamMember = teamMemberService.getAllMember(teamID);
        for (UserResponseDto user : teamMember) {
            List<WorkingTimeResponseDto> resultat = workingTimeService.getWorkingTimes(user.getUserID(), start, end);
            for (WorkingTimeResponseDto w : resultat) {
                Date d1 = null;
                Date d2 = null;
                try {
                    d1 = format.parse(Long.toString(w.getStart()));
                    d2 = format.parse(Long.toString(w.getEnd()));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                nbrdate++;
                long difference = getDifference(d1, d2);
                total += difference;
            }
            total = total / nbrdate;
        }
        statDto.setDuration(total / nbrdate);
        return statDto;
    }

    private static long getDifference(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        diff = diff / 1000;

        long hours = diff / (60 * 60) % 24;
        long minutes = diff / 60 % 60;
        if (minutes >= 30) {
            hours++;
        }
        return hours;
    }

    public StatisticResponseDto getTeamStatisticByDay(String teamID, long start) {
        StatisticResponseDto statDto = new StatisticResponseDto();
        int nbrdate = 0;
        long total = 0;
        long end = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        List<UserResponseDto> teamMember = teamMemberService.getAllMember(teamID);
        for (UserResponseDto user : teamMember) {
            List<WorkingTimeResponseDto> resultat = workingTimeService.getWorkingTimes(user.getUserID(), start, end);
            for (WorkingTimeResponseDto w : resultat) {
                Date d1 = null;
                Date d2 = null;
                try {
                    d1 = format.parse(Long.toString(w.getStart()));
                    try {
                        c.setTime(d1);
                    } catch (Exception e) {

                    }
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    d2 = c.getTime();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                nbrdate++;
                long difference = getDifference(d1, d2);
                total += difference;
            }
        }
        statDto.setDuration(total / nbrdate);
        return statDto;
    }

    public StatsDailyResponseDto getUserStatisticByDay(String userID) {
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
        } else {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "No working times are available");
        }
        response.setUserDailyHours(userDailyHours);
        return response;
    }

    public StatisticResponseDto getUserStatisticByPeriod(String userID, long start, long end) {
        StatisticResponseDto statDto = new StatisticResponseDto();
        int nbrdate = 0;
        long total = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        List<WorkingTimeResponseDto> resultat = workingTimeService.getWorkingTimes(userID, start, end);
        for (WorkingTimeResponseDto w : resultat) {
            Date d1 = null;
            Date d2 = null;
            try {
                d1 = format.parse(Long.toString(w.getStart()));
                d2 = format.parse(Long.toString(w.getEnd()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            nbrdate++;
            long difference = getDifference(d1, d2);
            total += difference;
        }
        statDto.setDuration(total / nbrdate);
        return statDto;
    }

}