package com.teste.service;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.teste.configuration.ManagerUser;
import com.teste.dao.User_dao;
import com.teste.dto.FriendDTO;
import com.teste.dto.MessagerDTO;
import com.teste.dto.Userdto;
import com.teste.model.MessagerModel;

public class FriendsAndGroups extends Thread{
    private FriendDTO friendDTO;
    private  Userdto user;
    private   ManagerUser accounts;
    public  ConcurrentHashMap<String,FriendDTO> users = new ConcurrentHashMap<>();
    List<FriendDTO> friendList = new ArrayList<>();
    public FriendsAndGroups(Userdto user,ManagerUser accounts){
        this.accounts = accounts;
        this.user = user;
        updateListFriend();
    }
    public FriendsAndGroups(){

    }
   
    public List<FriendDTO> allFriends(int id){
        User_dao.getInstance().findFriend(id).forEach(e->{
            friendDTO = new FriendDTO();
            friendDTO.setUsername(e.getId_friend().getUsername());
            friendDTO.setId(e.getId_friend().getId_username().intValue());
            friendDTO.setHost(e.getId_friend().getHost());
            friendDTO.setStatus(e.getId_friend().getStatus());
            friendList.add(friendDTO);
         });
        //updateListFriend();
        return friendList;
    }
    public void updateListFriend(){
            friendList = allFriends(user.getId());
            friendList.forEach((list)->{
                try {    
                Userdto friendUser = accounts.findUser(list.getUsername());
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
