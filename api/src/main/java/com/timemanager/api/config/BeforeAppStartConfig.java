package com.timemanager.api.config;

import java.util.List;
import java.util.UUID;

import com.timemanager.core.src.constant.UserType;
import com.timemanager.core.src.model.User;
import com.timemanager.core.src.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BeforeAppStartConfig implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    
    @Override
    public void run(String... args) throws Exception {
        List<User> adminUser = userRepository.find("email", "admin@timemanager.fr");
        if (adminUser == null || adminUser.size() == 0 ) {
            User user = new User();
            user.setEmail("admin@timemanager.fr");
            user.setFirstName("General");
            user.setLastName("Manager");
            user.setPassword("3PQPTRhqPp");
            user.setType(UserType.Admin.name());
            user.setUserID("USR" + UUID.randomUUID().toString());
            userRepository.create(user);
        }
    }

}