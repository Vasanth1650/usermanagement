package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
//here we are implmenting inteface UserDetails from sprngFromwork
public class User  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Column(length = 50)
    private String email;
    @Column(length = 20)
    private String password;
    @Column(length = 50)
    private String username;
    @Column(length=50)
    private String name;
    @Column(length = 10)
    private String mobileNumber;
    @Column(length = 50)
    private String userRole;

    //user can have many userrole like  customer and admins
    //using cascade type make ensure that whatEver the changes we making in user,get refleted in userRoles by default(crud)
    //fetch type eager means when we do fetch user,it's role get fetch
    //jsonIgnore is used to remove circular dependency
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "user")
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();

    //no args constructor
    public User() {
    }
    //custom constructor
    public User(Long id, String email, String password, String username, String name, String mobileNumber, String userRole, Set<UserRole> userRoles) {
        Id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.userRole = userRole;
        this.userRoles = userRoles;
    }

    //getter setter
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    //userDetails Method
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    ////authortiy mean basically we have to return the set of authority like admin,customer etc
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> set = new HashSet<>();

        //we will get authortiy from userRoles as declare above
       this.userRoles.forEach(userRole -> {
           set.add(new Authority(userRole.getRole().getRoleName()));
       });
        return set;
    }
}
