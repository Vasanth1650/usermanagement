package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.demo.config.JwtUtils;
import com.example.demo.model.JwtResponse;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserDetailsServiceImpl;

import java.security.Principal;

@RestController
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private JwtUtils jwtUtils;
    

    
    
    //genetare token
    @PostMapping(value = {"/user/login"})
    public ResponseEntity<?> isUserPresent(@RequestBody LoginRequest loginRequest) throws Exception {
        try{
            authenticate(loginRequest.getUsername(),loginRequest.getPassword());
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("user not present");
        }
        UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(loginRequest.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    private void authenticate(String username,String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (DisabledException e){
            throw  new Exception("User Disabled"+e.getMessage());
        }
        catch (BadCredentialsException e){
            throw new Exception("Invalid Credentials"+e.getMessage());
        }
    }
    //return details of current user
    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal){
        return (User)this.userDetailsServiceImpl.loadUserByUsername(principal.getName());
    }
}
