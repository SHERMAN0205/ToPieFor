package com.topiefor.dao;

import com.topiefor.models.User;
import java.util.List;

public interface UserDao {

   public boolean addUser(User user);
   public boolean userAvailability(User user); 
   public  boolean editUser(User user);
   List<User> getAllUsers();
}