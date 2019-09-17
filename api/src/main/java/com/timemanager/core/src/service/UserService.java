package com.timemanager.core.src.service;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.timemanager.core.src.constant.UserType;
import com.timemanager.core.src.dto.UserRequestDto;
import com.timemanager.core.src.dto.UserResponseDto;
import com.timemanager.core.src.model.User;
import com.timemanager.core.src.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public static final Pattern pattern = 
    Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$",
        Pattern.CASE_INSENSITIVE);

    public UserResponseDto createUser(UserRequestDto in) {
        User user = null;
        String userID = "";
        Matcher matcher = pattern.matcher(in.getEmail());

        if (!matcher.matches() || in.getFirstName().isEmpty() || in.getLastName().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Incorrect value for mail");     
        } else {
            user = new User();
            userID = "USR" + UUID.randomUUID().toString();
            user.setUserID(userID);
            user.setEmail(in.getEmail().toLowerCase());
            user.setFirstName(in.getFirstName());
            user.setLastName(in.getLastName());
            user.setPassword(in.getPassword());

            userRepository.create(user);
        }
        return new UserResponseDto(user.getUserID(), user.getEmail(), user.getFirstName(),
            user.getLastName(), UserType.valueOf(user.getType()));
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(String userID, User in) {
        Matcher matcher = pattern.matcher(in.getEmail());
        User user = getUserById(userID);

        if (user != null && !matcher.matches() && in.getFirstName().isEmpty() && in.getLastName().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");
        } else {
            user.setEmail(in.getEmail());
            user.setFirstName(in.getFirstName());
            user.setLastName(in.getLastName());
            user.setType(in.getType());
            //user.setPassword(in.getPassword());
            userRepository.update(user);
        }
    }

    public User getUserById(String userID) {
        List<User> users = null;

        if (userID.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid parameters");     
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where("userID").is(userID));            

            users = userRepository.find(query);
        }
        
        if (users == null || users.size() == 0) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "No user found");
        }

        return users.get(0);
    }

    public void deleteUser(String userID) {
        User user = getUserById(userID);
        if (user != null) {
            userRepository.delete(user);
        }
    }
}