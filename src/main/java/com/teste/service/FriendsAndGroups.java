package com.teste.service;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.teste.configuration.ManagerUser;
import com.teste.configuration.ServerDK;
import com.teste.dao.User_dao;
import com.teste.dto.FriendDTO;
import com.teste.dto.Userdto;
import com.teste.model.Usermodel.Status;

public class FriendsAndGroups extends Thread{
    private FriendDTO friendDTO;
    private Userdto user;
    private ManagerUser accounts;
    public  HashMap<String,FriendDTO> users = new HashMap<>();
    
    public FriendsAndGroups(Userdto user,ManagerUser accounts){
        this.user = user;
        this.accounts = accounts;
    }
    public FriendsAndGroups(){

    }
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
    public void joinGroup(){
       
    }
    public List<FriendDTO> allFriends(){
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

    @Override
    public void run() {
        while (true) {
       
        allFriends().forEach(e->{
                users.put(e.getUsername(),e);
                try {
                    if(accounts.findUser(e.getUsername()) != null){
                        users.get(e.getUsername()).setSocket(accounts.findUser(e.getUsername()).getSocket().socket());
                    }
                } catch (NullPointerException erro) {
                   users.get(e.getUsername()).setStatus(Status.OFF);
                }

            });
            //System.out.println(users);
              try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
        }
       
    }
}
