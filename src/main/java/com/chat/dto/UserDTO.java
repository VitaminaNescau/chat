package com.chat.dto;

import java.io.Serializable;
import java.net.Socket;

import com.chat.model.Usermodel.Status;

public class UserDTO implements Serializable{
    private int id;
    private String username;
    private String host;
    private transient Status status;
    private transient Socket socket;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
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
                              username, host, status, socket.getLocalAddress().getHostAddress());
    }
}
