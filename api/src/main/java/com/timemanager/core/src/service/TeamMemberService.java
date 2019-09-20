package com.timemanager.core.src.service;

import java.util.List;

import com.timemanager.core.src.dto.TeamMemberRequestDto;
import com.timemanager.core.src.dto.UserResponseDto;
import com.timemanager.core.src.repository.TeamMemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamMemberService {

    @Autowired
    TeamMemberRepository teamMemberRepository;

	public void addMember(TeamMemberRequestDto in) {
	}

	public void removeMember(String teamID, String userID) {
	}

	public List<UserResponseDto> getAllMember(String teamID) {
		return null;
	}

}