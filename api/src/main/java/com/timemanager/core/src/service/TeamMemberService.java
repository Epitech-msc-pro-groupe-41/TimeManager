package com.timemanager.core.src.service;

import java.util.ArrayList;
import java.util.List;

import com.timemanager.core.src.dto.TeamMemberRequestDto;
import com.timemanager.core.src.dto.UserResponseDto;
import com.timemanager.core.src.repository.TeamMemberRepository;
import com.timemanager.core.src.repository.UserRepository;
import com.timemanager.core.src.model.Team;
import com.timemanager.core.src.model.TeamMember;
import com.timemanager.core.src.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamMemberService {

    @Autowired
	TeamMemberRepository teamMemberRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;

	@Autowired
	TeamService teamService;

	public void addMember(TeamMemberRequestDto in) {
		if (in.getUserID() != null && in.getTeamID() !=null) {
			TeamMember teamMember = new TeamMember();
			teamMember.setUserID(in.getUserID());
			teamMember.setTeamID(in.getTeamID());
			teamMemberRepository.create(teamMember);
		}
	}


	public void removeMember(String teamID, String userID) {
		User user = userService.getUserById(userID, true);
		List<TeamMember> member = teamMemberRepository.find(userID, userID);
		Team team = teamService.getUniqueTeam(teamID, true);
		if (team!= null && user!= null) {
			for (TeamMember m : member) {
				if(m.getUserID() == userID && m.getTeamID()== teamID){
					teamMemberRepository.delete(m);
				}	
			}
		}
	}
	public void removeMember(String userID) {
		User user = userService.getUserById(userID, true);
		List<TeamMember> member = teamMemberRepository.find(userID, userID);
		if (user!= null) {
			for (TeamMember m : member) {
				if(m.getUserID() == userID){
					teamMemberRepository.delete(m);
				}	
			}
		}
	}
	public List<UserResponseDto> getAllMember(String teamID) {
		Team team = teamService.getUniqueTeam(teamID, true);
		List<TeamMember> tm = teamMemberRepository.find("teamID", teamID);
		List<UserResponseDto> liste = new ArrayList<>();
		if (team!=null){
			for(TeamMember t: tm){
				User user = userRepository.findById(t.getUserID());
				liste.add(userService.convertToResponseDto(user));
			}	
		}
		return liste;
	}


}