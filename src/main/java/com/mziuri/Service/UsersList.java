package com.mziuri.Service;

import com.mziuri.Classes.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UsersList {
    static private List<User> userList;
    public UsersList(){
    }
    public void addUser(User user){
        userList.add(user);
    }
    public void removeUser(User user){
        userList.remove(user);
    }
}
