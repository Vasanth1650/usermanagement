
package com.example.demo.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.service.UserService;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public User addUser(User user, Set<UserRole> userRoles) throws Exception {
        //we are creating a method findyUserName to check is the user already present in db
        User local = this.userRepository.findByUsername(user.getUsername());
        if(local !=null){
            System.out.println("User is already there!!");
            throw new Exception("User exits ");
        }
        else {
            //here we are taking role we recieved and saving in db if it not present
            for(UserRole ur :userRoles){
                roleRepository.save(ur.getRole());
            }
            //adding all role that is associated with user
            user.getUserRoles().addAll(userRoles);
            local = this.userRepository.save(user);
        }

        return local;
    }
}
