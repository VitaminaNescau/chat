package com.teste.configuration;

import java.util.concurrent.ConcurrentHashMap;

import com.teste.dto.Userdto;

public class ManagerUser {
   
    private static ConcurrentHashMap<String,Userdto> accounts = new ConcurrentHashMap<>();


    public void addUser(Userdto user){
        accounts.put(user.getUsername(), user);
    }
    public void removerUSer(Userdto user){
        accounts.remove(user.getUsername());
    }
    public Userdto findUser(String user){
        return accounts.get(user);
    }
}

