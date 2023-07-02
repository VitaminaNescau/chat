package com.chat.model;


import com.chat.dto.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "mensagem")
public class MessagerModel  {
    
    public MessagerModel(String msg, UserDTO user, UserDTO friend){ 
        Usermodel usermodel = new Usermodel() ; 
        new String();
        usermodel.setId_username(Long.parseLong(String.valueOf(user.getId())));
        Usermodel usermodel2 = new Usermodel() ; 
        usermodel2.setId_username(Long.parseLong(String.valueOf(friend.getId())));
        messager = msg;
        send_id = usermodel;
        receiver_id = usermodel2; 
   
    }

public MessagerModel(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messager_id;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id_username",name = "send_id")
    private Usermodel send_id;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id_username",name = "receiver_id")
    private Usermodel receiver_id;
    @Column
    private String messager;
    public int getMessager_id() {
        return messager_id;
    }
    public void setMessager_id(int messager_id) {
        this.messager_id = messager_id;
    }
    public Usermodel getSend_id() {
        return send_id;
    }
    public void setSend_id(Usermodel send_id) {
        this.send_id = send_id;
    }
    public Usermodel getReceiver_id() {
        return receiver_id;
    }
    public void setReceiver_id(Usermodel receiver_id) {
        this.receiver_id = receiver_id;
    }
    public String getMessager() {
        return messager;
    }
    public void setMessager(String messager) {
        this.messager = messager;
    }

    




}
