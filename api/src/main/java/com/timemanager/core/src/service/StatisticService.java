package com.timemanager.core.src.service;

import com.timemanager.core.src.model.WorkingTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.timemanager.core.src.dto.WorkingTimeResponseDto;

import java.text.SimpleDateFormat;
import java.util.*;

import com.timemanager.core.src.dto.ClockResponseDto;
import com.timemanager.core.src.dto.TeamMemberRequestDto;
import com.timemanager.core.src.dto.UserResponseDto;
import com.timemanager.core.src.repository.TeamMemberRepository;
import com.timemanager.core.src.repository.UserRepository;
import com.timemanager.core.src.service.TeamMemberService;
import com.timemanager.core.src.service.WorkingTimeService;
import com.timemanager.core.src.repository.WorkingTimeRepository;
import com.timemanager.core.src.service.ClockService;
import com.timemanager.core.src.repository.ClockRepository;
import com.timemanager.core.src.model.Clock;
import com.timemanager.core.src.model.Team;
import com.timemanager.core.src.model.TeamMember;
import com.timemanager.core.src.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StatisticService {
    @Autowired
    WorkingTimeRepository workingTimeRepository;

    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Autowired
    ClockRepository clockRepository;

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

    public long getTeamStatisticByPeriod(String teamID, long start, long end) {

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
        return total;
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

    public long getTeamStatisticByDay(String teamID, long start) {
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
        return total / nbrdate;
    }

    public long getUserStatisticByDay(String userID, long start) {
        int nbrdate = 0;
        long total = 0;
        long end = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        List<WorkingTimeResponseDto> resultat = workingTimeService.getWorkingTimes(userID, start, end);
        Calendar c = Calendar.getInstance();
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
        return total / nbrdate;
    }

    public long getUserStatisticByPeriod(String userID, long start, long end) {
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
        return total / nbrdate;
    }

}
