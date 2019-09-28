package com.timemanager.core.src.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.timemanager.core.src.dto.TeamMemberRequestDto;
import com.timemanager.core.src.dto.UserResponseDto;
import com.timemanager.core.src.repository.TeamMemberRepository;
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
public class TeamMemberService {

	@Autowired
	TeamMemberRepository teamMemberRepository;

	@Autowired
	UserService userService;

	@Autowired
	TeamService teamService;

	public void addMember(TeamMemberRequestDto in) {
		if (in.getUserID() != null && in.getTeamID() != null) {
			if (getUniqueTeamMember(in.getUserID(), in.getTeamID(), false) == null) {
				TeamMember teamMember = new TeamMember();
				teamMember.setUserID(in.getUserID());
				teamMember.setTeamID(in.getTeamID());
				teamMember.setTeamMemberID("MBR" + UUID.randomUUID().toString());
				teamMember.setJoinDate(System.currentTimeMillis());
				teamMemberRepository.create(teamMember);
			} else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is already member in this team");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid parameters");
		}
	}

	public TeamMember getUniqueTeamMember(String userID, String teamID, boolean trigger) {
		TeamMember teamMember = null;
		if (userID == null || teamID == null) {
			if (trigger) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid parameters value");
			}
		} else {
			Query query = new Query();
			query.addCriteria(Criteria.where("userID").is(userID).and("teamID").is(teamID));

			List<TeamMember> teamMembers = teamMemberRepository.find(query);
			if (teamMembers == null || teamMembers.size() == 0) {
				if (trigger) {
					throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No team for this teamID");
				}
			} else {
				teamMember = teamMembers.get(0);
			}
		}
		return teamMember;
	}

	public void removeMember(String teamID, String userID) {
		TeamMember teamMember = getUniqueTeamMember(userID, teamID, true);
		teamMemberRepository.delete(teamMember);
	}

	public void removeMember(String userID) {
		List<TeamMember> member = teamMemberRepository.find("userID", userID);
		if (member != null && member.size() > 0) {
			for (TeamMember m : member) {
				if (m.getUserID() == userID) {
					teamMemberRepository.delete(m);
				}
			}
		}
	}

	public List<UserResponseDto> getAllMember(String teamID) {
		Team team = teamService.getUniqueTeam(teamID, true);
		List<TeamMember> tm = teamMemberRepository.find("teamID", teamID);
		List<UserResponseDto> list = new ArrayList<>();
		if (team != null) {
			for (TeamMember t : tm) {
				User user = userService.getUserById(t.getUserID(), false);
				if (user != null)
					list.add(userService.convertToResponseDto(user));
			}
		}
		return list;
	}
}