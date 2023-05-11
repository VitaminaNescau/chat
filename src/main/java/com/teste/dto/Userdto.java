package com.teste.dto;

import java.net.Socket;
import java.nio.channels.SocketChannel;

import com.teste.model.Usermodel.Status;

public class Userdto{
    private int id;
    private String username;
    private String host;
    private Status status;
    private SocketChannel socket;
    //private Socket socket;
  
    // private List friendsList;

    // public List getFriendsList() {
    //     return friendsList;
    // }
    // public void setFriendsList(List friendsList) {
    //     this.friendsList = friendsList;
    // }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public SocketChannel getSocket() {
        return socket;
    }
    public void setSocket(SocketChannel socket) {
        this.socket = socket;
    }
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
    @Override
    public String toString() {
        return String.format("Username: %s, Host: %s, Status: %s, Socket: %s", 
                              username, host, status, socket.socket().getLocalAddress().getHostAddress());
    }
}
