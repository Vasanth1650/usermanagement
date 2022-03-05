//step 3
package com.example.demo.service;



import java.util.Set;

import com.example.demo.model.User;
import com.example.demo.model.UserRole;

public interface UserService {
    //creating user
    //taking userRole in set mean ,it might possile if a single user is both admin,customer,pass to it
    User addUser(User user, Set<UserRole> userRoles) throws Exception;

}
