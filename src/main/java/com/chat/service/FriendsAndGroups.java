package com.chat.service;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.chat.configuration.ManagerUser;
import com.chat.dao.User_dao;
import com.chat.dto.MessagerDTO;
import com.chat.dto.UserDTO;
import com.chat.model.MessagerModel;

public class FriendsAndGroups extends Thread{
    private  UserDTO user;
    private   ManagerUser accounts;
    public  ConcurrentHashMap<String,UserDTO> users = new ConcurrentHashMap<>();
    List<UserDTO> friendList = new ArrayList<>();
    public FriendsAndGroups(UserDTO user,ManagerUser accounts){
        this.accounts = accounts;
        this.user = user;
        updateListFriend();
    }
    public FriendsAndGroups(){

    }
   
    public List<UserDTO> allFriends(int id){
        User_dao.getInstance().findFriend(id).forEach(e->{
            UserDTO UserDTO = new UserDTO();
            UserDTO.setUsername(e.getId_friend().getUsername());
            UserDTO.setId(e.getId_friend().getId_username().intValue());
            UserDTO.setHost(e.getId_friend().getHost());
            UserDTO.setStatus(e.getId_friend().getStatus());
            friendList.add(UserDTO);
         });
        //updateListFriend();
        return friendList;
    }
    public void updateListFriend(){
            friendList = allFriends(user.getId());
            friendList.forEach((list)->{
                try {    
                UserDTO friendUser = accounts.findUser(list.getUsername());
                users.put(list.getUsername(), list);
                if (friendUser != null) {
                    users.get(list.getUsername()).setSocket(friendUser.getSocket());
                }  
                  } catch (NullPointerException e) {
            }
         });   
    }
    public List<MessagerDTO> MessagerHistory(int id,String name) {
        List<MessagerModel> result =  User_dao.getInstance().MessagerHistory(id);
        List<MessagerDTO> messagerDTOs = new ArrayList<>(); 
        for (MessagerModel messager : result) {
            if ((messager.getReceiver_id().getUsername().equals(name)
             || messager.getSend_id().getUsername().equals(name)) ) {
                MessagerDTO dto = new MessagerDTO();
                dto.setMessager(messager.getMessager());
                dto.setSend(messager.getSend_id().getUsername());
                dto.setReceive(messager.getReceiver_id().getUsername());
                messagerDTOs.add(dto); 
            }   
        }
        return  messagerDTOs;
    }
 
}
