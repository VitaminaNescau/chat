package com.teste.service;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.teste.configuration.ManagerUser;
import com.teste.dao.User_dao;
import com.teste.dto.FriendDTO;
import com.teste.dto.Userdto;

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
    // public static FriendsAndGroups getInstance(){
    //     if (instance == null) {
    //         instance = new FriendsAndGroups(user,accounts);
    //     }
    //     return instance;
    // }
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

    // public List<FriendDTO> allFriends(Userdto user){
    //     User_dao.getInstance().findFriend(user.getId()).forEach(e->{
    //         friendDTO = new FriendDTO();
    //         friendDTO.setUsername(e.getId_friend().getUsername());
    //         friendDTO.setId(e.getId_friend().getId_username().intValue());
    //         friendDTO.setHost(e.getId_friend().getHost());
    //         friendDTO.setStatus(e.getId_friend().getStatus());
    //         friendList.add(friendDTO);
    //      });
    //     //updateListFriend();
    //     return friendList;
    // }
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
}
