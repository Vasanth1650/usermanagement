package com.example.demo.model;

import org.springframework.security.core.GrantedAuthority;

//implementing userDetails grandAuthority
public class Authority implements GrantedAuthority {
    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
