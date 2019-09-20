package com.timemanager.core.src.service;

import java.util.List;

import com.timemanager.core.src.dto.TeamDto;
import com.timemanager.core.src.dto.UpdateTeamRequestDto;
import com.timemanager.core.src.repository.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;

	public TeamDto getTeam(String teamID, boolean b) {
		return null;
	}

	public void createTeam(TeamDto in) {
	}

	public void updateTeam(String teamID, UpdateTeamRequestDto in) {
	}

	public void deleteTeam(String teamID) {
	}

	public List<TeamDto> getTeamsByManager(String managerID) {
		return null;
	}

}