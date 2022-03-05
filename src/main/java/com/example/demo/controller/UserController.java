//step 5

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value={"/user","/admin"})
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public User createUser(@RequestBody User user) throws Exception {


        Set<UserRole> roles = new HashSet<>();
        Role role = new Role();
        role.setRoleName(user.getUserRole());
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        //set function add
        roles.add(userRole);
        return this.userService.addUser(user,roles);
    }

}
