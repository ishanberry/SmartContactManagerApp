package com.Scm.Services;
import java.util.*;


import com.Scm.Entity_Models.User;

public interface UserService {

User saveUser(User user);       //  use optional  User getUserById(String id); /

Optional<User> getUserById(String id); // we Optional<User> getUserById(String id) Optional is datatype which return user is exist or return ture or fasle
Optional<User> updateUser(User user);
void deleteUser(String id);
boolean isUserExist(String userId);
boolean isUserExistByEmail(String email);
List<User>getAllUser();

User getUserByEmail(String email);

// add more method realted to user logic 


}
