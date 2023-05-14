package com.teste.configuration;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


import com.teste.dto.Userdto;

public class ManagerUser {

    private static ConcurrentHashMap<String,Userdto> accounts = new ConcurrentHashMap<>();

    public Userdto addUser(Userdto user){
        try {
            accounts.put(user.getUsername(), user);
            Logger.getLogger("SUCCESS").info("ADICIONADO NO SERVIDOR USER: "+user.getUsername());
            return user;
        } catch (NullPointerException e) {
           Logger.getLogger("ERROR").info(e.getMessage());
           return null;
        }
        
    }
    public void removerUSer(Userdto user){
        accounts.remove(user.getUsername());
    }
    public Userdto findUser(String user){
        return accounts.get(user);
    }
    public HashMap<SelectionKey,Userdto> userKeys(){
        return null;
    } 
}


