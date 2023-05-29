package com.teste.dto;

import java.net.Socket;

import com.teste.model.Usermodel.Status;

/**
 * FriendDTO
 */

public class FriendDTO {
    private int id;
    private String username;
    private String host;
    private Status status;
    private Socket socket;
  
    // public FriendDTO(String username,String host, Status status){
    //     this.username = username;
    //     this.status = status;
    //     this.host = host;
    // }  
    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public FriendDTO(){}
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    
}