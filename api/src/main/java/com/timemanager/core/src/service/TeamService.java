package com.timemanager.core.src.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.timemanager.core.src.constant.UserType;
import com.timemanager.core.src.dto.TeamDto;
import com.timemanager.core.src.dto.TeamRequestDto;
import com.timemanager.core.src.model.Team;
import com.timemanager.core.src.model.User;
import com.timemanager.core.src.repository.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TeamService {

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	UserService userService;

	public TeamDto getTeam(String teamID) {
		TeamDto response = new TeamDto();
		Team team = getUniqueTeam(teamID, true);
		response.setCreateDate(team.getCreateDate());
		response.setManagerID(team.getManagerID());
		response.setName(team.getName());
		response.setTeamID(team.getTeamID());
		return response;
	}

	public Team getUniqueTeam(String teamID, boolean trigger) {
		Team team = null;
		if (teamID == null || teamID.isEmpty()) {
			if (trigger) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid value for teamID");
			}
		} else {
			Query query = new Query();
			query.addCriteria(Criteria.where("teamID").is(teamID));

			List<Team> teams = teamRepository.find(query);
			if (teams == null || teams.size() == 0) {
				if (trigger) {
					throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No team for this teamID");
				}
			} else {
				team = teams.get(0);
			}
		}
		return team;
	}

	public Team getTeamByName(String teamName, String managerID, boolean trigger) {
		Team team = null;
		if (teamName == null || teamName.isEmpty() || managerID.isEmpty()) {
			if (trigger) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid value for arguments");
			}
		} else {
			Query query = new Query();
			query.addCriteria(Criteria.where("name").is(teamName).and("managerID").is(managerID));

			List<Team> teams = teamRepository.find(query);
			if (teams == null || teams.size() == 0) {
				if (trigger) {
					throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No team for this teamID");
				}
			} else {
				team = teams.get(0);
			}
		}
		return team;
	}

	public void createTeam(TeamRequestDto in, String managerID) {
		if (in.getName() != null && !in.getName().isEmpty()) {
			User user = userService.getUserById(managerID, true);
			if (user.getType().toUpperCase().equals(UserType.Manager.name().toUpperCase())
					|| user.getType().toUpperCase().equals(UserType.Admin.name().toUpperCase())) {
				if (getTeamByName(in.getName(), managerID, false) == null) {
					Team team = new Team();
					team.setCreateDate(System.currentTimeMillis());
					team.setManagerID(managerID);
					team.setTeamID("TM" + UUID.randomUUID().toString());
					team.setName(in.getName());
					teamRepository.create(team);
				} else {
					throw new ResponseStatusException(HttpStatus.FORBIDDEN,
							"A team with this name already exist for this user");
				}
			} else {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
						"User ID given in parameter have not enough permissions to manage a team");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid value for team name");
		}
	}

	public void updateTeam(String teamID, TeamRequestDto in) {
		if (in.getName() != null && !in.getName().isEmpty()) {
			Team team = getUniqueTeam(teamID, true);
			team.setName(in.getName());
			teamRepository.update(team);
		}
	}

	public void deleteTeam(String teamID) {
		Team team = getUniqueTeam(teamID, true);
		teamRepository.delete(team);
	}

	public List<TeamDto> getTeamsByManager(String managerID) {
		List<TeamDto> response = new ArrayList<>();
		if (!managerID.isEmpty()) {
			Query query = new Query();
			query.addCriteria(Criteria.where("managerID").is(managerID));

			List<Team> teams = teamRepository.find(query);
			if (teams != null && teams.size() > 0) {
				for (Team team : teams) {
					response.add(
							new TeamDto(team.getTeamID(), team.getName(), team.getCreateDate(), team.getManagerID()));
				}
			}
		}
		return response;
	}

}