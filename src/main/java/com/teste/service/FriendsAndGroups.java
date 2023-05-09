package com.teste.service;



import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.teste.dao.User_dao;
import com.teste.dto.FriendDTO;
import com.teste.dto.Userdto;

public class FriendsAndGroups extends Thread{
    FriendDTO friendDTO;
    
    public FriendsAndGroups() {}

    // public Userdto addFriend(String name){
    //     Usermodel model = User_dao.getInstance().findUser(name);
    //     Userdto friend = new Userdto();
    //     if (model == null) {
    //         return null;
    //     }
    //     friend.setUsername(model.getUsername());
    //     friend.setStatus(model.getStatus());
    //     friend.setHost(model.getHost());
    //     return friend;
    // };
    public void joinGroup(){}
    public List<FriendDTO> allFriends(Userdto user){
        List<FriendDTO> friendList = new ArrayList<>();
        User_dao.getInstance().findFriend(user).forEach(e->{
            friendDTO = new FriendDTO();
            friendDTO.setUsername(e.getId_friend().getUsername());
            friendDTO.setId(e.getId_friend().getId_username().intValue());
            friendDTO.setHost(e.getId_friend().getHost());
            friendDTO.setStatus(e.getId_friend().getStatus());
            friendList.add(friendDTO);
         });
       return friendList;
    }
    public Hashtable friend(){
        
        return null;}

    @Override
    public void run() {
        while (true) {
            
        }
    }
}
