package com.timemanager.core.src.service;

import com.timemanager.core.src.dto.WorkingTimeResponseDto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import com.timemanager.core.common.Utils;
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

    public StatsDailyResponseDto getTeamStatisticByDay(String teamID, long start, long end) {
        StatsDailyResponseDto response = new StatsDailyResponseDto();
        ArrayList<StatsElementResponseDto> userDailyHours = new ArrayList<>();
        Map<String, String> data = new LinkedHashMap<>();

        if (start > end) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid date values");
        }

        List<UserResponseDto> teamMember = teamMemberService.getAllMember(teamID);
        for (UserResponseDto user : teamMember) {
            List<WorkingTimeResponseDto> resultat = workingTimeService.getWorkingTimes(user.getUserID(), start, end);
            for (WorkingTimeResponseDto w : resultat) {
                try {
                    String date = Utils.dateLongToString(w.getStart());
                    int hours = (int) ((w.getEnd() - w.getStart()) / (60 * 60 * 1000));
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
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid format for date");
                }
            }
        }

        if (!data.isEmpty()) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                int hours = Integer.valueOf(entry.getValue().split(";")[0]);
                int countMember = Integer.valueOf(entry.getValue().split(";")[1]);

                userDailyHours.add(new StatsElementResponseDto(entry.getKey(), Math.round(hours / countMember)));
            }
        }
        response.setData(userDailyHours);
        return response;
    }

    public StatsDailyResponseDto getUserStatisticByDay(String userID, long start, long end) {
        StatsDailyResponseDto response = new StatsDailyResponseDto();
        ArrayList<StatsElementResponseDto> userDailyHours = new ArrayList<>();
        Map<String, Integer> data = new LinkedHashMap<>();
        List<WorkingTimeResponseDto> workingTimes = workingTimeService.getAllWorkingTimes(userID);

        if (start > end) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid date values");
        }

        for (WorkingTimeResponseDto w : workingTimes) {
            try {
                String date = Utils.dateLongToString(w.getStart());
                int hours = (int) ((w.getEnd() - w.getStart()) / (60 * 60 * 1000));
                if (!data.containsKey(date)) {
                    data.put(date, hours);
                } else {
                    data.replace(date, data.get(date) + hours);
                }
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid format for date");
            }
        }

        if (!data.isEmpty()) {
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                userDailyHours.add(new StatsElementResponseDto(entry.getKey(), entry.getValue()));
            }
        }
        response.setData(userDailyHours);
        return response;
    }

    public StatsDailyResponseDto getTeamStatisticByPeriod(String teamID, long start, long end) {
        StatsDailyResponseDto response = new StatsDailyResponseDto();
        ArrayList<StatsElementResponseDto> userDailyHours = new ArrayList<>();
        Map<String, String> data = new LinkedHashMap<>();

        if (start > end) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid date values");
        }

        List<LocalDate> mondayList = getmondays(start, end);
        for (LocalDate date : mondayList) {
            data.put(date.toString() + ";" + date.plusDays(6).toString(), "0;0");
        }

        List<UserResponseDto> teamMember = teamMemberService.getAllMember(teamID);
        for (UserResponseDto user : teamMember) {
            List<WorkingTimeResponseDto> resultat = workingTimeService.getWorkingTimes(user.getUserID(), start, end);
            for (WorkingTimeResponseDto w : resultat) {
                try {
                    LocalDate date = LocalDate.parse(Utils.dateLongToString(w.getStart()));
                    int hours = (int) ((w.getEnd() - w.getStart()) / (60 * 60 * 1000));
                    if (hours > 0) {
                        for (Map.Entry<String, String> entry : data.entrySet()) {
                            LocalDate startWeek = LocalDate.parse(entry.getKey().split(";")[0]);
                            LocalDate endWeek = LocalDate.parse(entry.getKey().split(";")[1]);

                            if (date.compareTo(startWeek) >= 0 && date.compareTo(endWeek) <= 0) {
                                int oldTime = Integer.valueOf(entry.getValue().split(";")[0]);
                                int oldCount = Integer.valueOf(entry.getValue().split(";")[1]);
                                oldCount++;
                                entry.setValue(String.valueOf(oldTime + hours) + ";" + oldCount);
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid format for date");
                }
            }
        }

        if (!data.isEmpty()) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                int hours = Integer.valueOf(entry.getValue().split(";")[0]);
                int countMember = Integer.valueOf(entry.getValue().split(";")[1]);
                if (countMember == 0) 
                    countMember = 1;
                if (countMember > teamMember.size())
                    countMember = teamMember.size();
                userDailyHours.add(new StatsElementResponseDto(entry.getKey(), Math.round(hours / countMember)));
            }
        }
        response.setData(userDailyHours);
        return response;
    }

    public StatsDailyResponseDto getUserStatisticByPeriod(String userID, long start, long end) {
        StatsDailyResponseDto response = new StatsDailyResponseDto();
        ArrayList<StatsElementResponseDto> userDailyHours = new ArrayList<>();
        Map<String, Integer> data = new LinkedHashMap<>();

        if (start > end) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid date values");
        }

        List<LocalDate> mondayList = getmondays(start, end);
        for (LocalDate date : mondayList) {
            data.put(date.toString() + ";" + date.plusDays(6).toString(), 0);
        }

        List<WorkingTimeResponseDto> workingTimes = workingTimeService.getAllWorkingTimes(userID);
        for (WorkingTimeResponseDto w : workingTimes) {
            try {
                LocalDate date = LocalDate.parse(Utils.dateLongToString(w.getStart()));
                int hours = (int) ((w.getEnd() - w.getStart()) / (60 * 60 * 1000));
                if (hours > 0) {
                    for (Map.Entry<String, Integer> entry : data.entrySet()) {
                        LocalDate startWeek = LocalDate.parse(entry.getKey().split(";")[0]);
                        LocalDate endWeek = LocalDate.parse(entry.getKey().split(";")[1]);
                        if (date.compareTo(startWeek) >= 0 && date.compareTo(endWeek) <= 0) {
                            entry.setValue(entry.getValue() + hours);
                        }
                    }
                }
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid format for date");
            }
        }

        if (!data.isEmpty()) {
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                userDailyHours.add(new StatsElementResponseDto(entry.getKey(), entry.getValue()));
            }
        }
        response.setData(userDailyHours);
        return response;
    }

    public ArrayList<LocalDate> getmondays(long start, long end) {
        LocalDate startDate = Instant.ofEpochMilli(start).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = Instant.ofEpochMilli(end).atZone(ZoneId.systemDefault()).toLocalDate();
        List<LocalDate> totalDates_Mondays = new ArrayList<>();

        while (!startDate.isAfter(endDate)) {
            totalDates_Mondays.add(startDate);
            startDate = startDate.plusWeeks(1);
        }
        return (ArrayList<LocalDate>) totalDates_Mondays;
    }

}
