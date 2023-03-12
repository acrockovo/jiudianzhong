package com.itlyc.service;

import com.itlyc.sys.entity.UserDomain;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    public UserDomain saveUser(UserDomain user){

        Random random = new Random(100);
        user.setId(random.nextLong());
        return user;
    }
}
