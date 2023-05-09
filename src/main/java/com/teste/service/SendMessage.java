package com.teste.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import com.teste.dto.FriendDTO;
import com.teste.dto.Userdto;

/**
 * SendMessage
 */
public class SendMessage {
    private PrintWriter output;
    final String group = "GROUP";

    public SendMessage(){
        
    }
    public void forwardMessage(ConcurrentHashMap<String,FriendDTO> users,Userdto user,String msg){
        String MSG[]  = verifySend(msg);
        
        System.out.println(users.isEmpty()+" "+user.getUsername()+" "+msg);
            if (MSG[0].equals(group)) {
                System.out.println(1);
               sendAll(users, user, MSG);   
            }else{
                 System.out.println(2);
                sendFor(users,MSG);
               
            }
           
      
    }
    /*metodo para as mensagens em grupo*/
    public void sendAll(ConcurrentHashMap<String,FriendDTO> users,Userdto userdto,String msg[]){
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
    public void sendFor(ConcurrentHashMap<String,FriendDTO> users,String msg[]){
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