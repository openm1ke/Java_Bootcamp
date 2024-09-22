package org.example.service.services;

import org.example.service.models.User;
import org.example.service.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("UserService")
public class UserServiceImpl implements UserService {
//    @Autowired
    private UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String signUp(String email) {
        String tempPassword = null;
        if(usersRepository.findByEmail(email).isEmpty()) {
            tempPassword = UUID.randomUUID().toString();
            usersRepository.save(new User(email, tempPassword));
        }
        return tempPassword;
    }
}
