package com.teste.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.teste.configuration.ManagerUser;
import com.teste.dto.FriendDTO;
import com.teste.dto.Userdto;

/**
 * SendMessage
 */
public class SendMessage {
    private PrintWriter output;
    final String group = "GROUP";


    private static  SendMessage instance;
    public SendMessage(){
        
    }
    public static SendMessage getInstance(){
        if (instance == null) {
            instance = new SendMessage();        
        }
        return instance;
    }
    public void forwardMessage(HashMap<String, FriendDTO> users,Userdto user,String msg){
        String MSG[]  = verifySend(msg);
        
            if (MSG[0].equals(group)) {
               sendAll(users, user, MSG);   
            }else{
                sendFor(users,MSG);
               
            }
           
      
    }
    /*metodo para as mensagens em grupo*/
    public void sendAll(HashMap<String, FriendDTO> users,Userdto userdto,String msg[]){
        users.forEach((c,v)->{
            System.out.println(v.getUsername());
            try {
                output = new PrintWriter(v.getSocket().getOutputStream(), true);
                 
                    output.println(userdto.getUsername()+": "+msg[1]);
                    System.out.println("envio mensagem pra grupo");
              } catch (IOException e) {
                Logger.getLogger("ERROR").info(e.getMessage());
              }
        });
    }
    /*metodo para as mensagens privadas*/
    public void sendFor(HashMap<String, FriendDTO> users,String msg[]){
        try {
            output = new PrintWriter(users.get(msg[0]).getSocket().getOutputStream(), true);
            output.println(msg[1]);
            System.out.println("enviou mensagem para " + users.get(msg[0]));
        } catch (IOException e) {
            Logger.getLogger("ERROR").info(e.getMessage());
        }
        
    }


    public static String[] verifySend(String msg){
        return msg.split(";");
    }
        
}