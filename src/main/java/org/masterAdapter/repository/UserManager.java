package org.masterAdapter.repository;

import lombok.NonNull;
import org.masterAdapter.exception.DuplicateUserException;
import org.masterAdapter.exception.NotFoundException;
import org.masterAdapter.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserManager {

    private HashMap<String, User> users;

    public UserManager() {
        this.users = new HashMap<>();
    }

    public String createUser(@NonNull final String userName, @NonNull final String userId){
        final User newUser = new User(userName, userId);
        if(users.containsKey(newUser.getUserId())){
            throw new DuplicateUserException("User already exists");
        }
        users.put(newUser.getUserId(), newUser);
        return newUser.getUserId();
    }

    public List<User> getAllUsers(){
        List<User> allUsers = new ArrayList<>();
        users.forEach((k,v) ->{
            allUsers.add(v);
        });
        return allUsers;
    }

    public User getUser(@NonNull final  String userId){
        if(!users.containsKey(userId)){
            throw new NotFoundException("User Not present");
        }
        return users.get(userId);
    }



}
