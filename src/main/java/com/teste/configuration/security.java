package com.teste.configuration;

import java.util.ArrayList;

import com.teste.dao.User_dao;
import com.teste.dto.UserDTO;
import com.teste.model.Usermodel;

public class security {
    
    private Usermodel usermodel;
    public ArrayList<UserDTO> user = new ArrayList<>();
    
    public String[] verifyString(String msg){
        return msg.split(";");
    }
    private boolean loginVerify(String login[]){
           if (login.length < 2) {
             return false;
           }
            usermodel = new Usermodel();
            usermodel.setUsername(login[0]);
            usermodel.setPassword(login[1]);
            usermodel = User_dao.getInstance().loginUser(usermodel);
            
            if (usermodel != null) {
                return true;
            }
            return false;
    } 
    public UserDTO login(String login[],UserDTO userdto){
        if (loginVerify(login)) {
            //userdto.setId(usermodel.getId_username().intValue());
            userdto.setUsername(usermodel.getUsername());
            userdto.setStatus(usermodel.getStatus());
            userdto.setHost(usermodel.getHost());  
            userdto.setId(usermodel.getId_username().intValue());
            user.add(userdto);
            return userdto;
        }
        return userdto;
    }
    public void removeUser(UserDTO user){
        this.user.remove(user);
    }
}
