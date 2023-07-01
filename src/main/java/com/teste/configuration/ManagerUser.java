package com.teste.configuration;

import java.util.concurrent.ConcurrentHashMap;

import com.teste.dto.UserDTO;

public class ManagerUser {
   
    private static ConcurrentHashMap<String,UserDTO> accounts = new ConcurrentHashMap<>();

    public void addUser(UserDTO user){
        accounts.put(user.getUsername(), user);
    }
    public void removerUSer(UserDTO user){
        accounts.remove(user.getUsername());
    }
    public UserDTO findUser(String user){
        return accounts.get(user);
    }
}

