package com.topiefor.service;

import com.topiefor.models.User;
import java.util.List;


public interface UserService {
    public boolean addUser(User user);
   public boolean userAvailability(User user); 
   public  boolean editUser(User user);
   User checkUser(String email);
   List<User> getAllUsers();
}