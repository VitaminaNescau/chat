package com.chat.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.chat.dao.User_dao;
import com.chat.dto.UserDTO;
import com.chat.model.MessagerModel;

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
    public void forwardMessage(ConcurrentHashMap<String, UserDTO> users,UserDTO user,String msg){
        String MSG[]  = verifySend(msg);
         try {
            if (MSG[0].equals(group)) {
               sendAll(users, user, MSG);   
            }else{
               sendFor(users,user,MSG);   
            } 
        } catch (IOException e) {
            e.printStackTrace();
        } catch(NullPointerException e){
            e.printStackTrace();
        } catch(ArrayIndexOutOfBoundsException e){
           Logger.getGlobal().info("Erro no formato da mensagem");
        }
           
       
    }
    /*metodo para as mensagens em grupo*/
    public void sendAll(ConcurrentHashMap<String, UserDTO> users,UserDTO userdto,String msg[]) {
        users.forEach((c,v)->{
                try {
                output = new PrintWriter(v.getSocket().getOutputStream(), true);
                output.println(userdto.getUsername()+": "+msg[1]);
                
            } catch (IOException e) {
                    e.printStackTrace();
                }
        });
    }
    /*metodo para as mensagens privadas*/
    public void sendFor(ConcurrentHashMap<String, UserDTO> users,UserDTO user,String msg[]) throws IOException, NullPointerException{
            
        MessagerModel savemessager = new MessagerModel(msg[1],user,users.get(msg[0]));
        User_dao.getInstance().saveMessager(savemessager);
            try {
                output = new PrintWriter(users.get(msg[0]).getSocket().getOutputStream(), true);
                output.println(user.getUsername()+";"+msg[1]);
          
            }catch (NullPointerException e) {
                Logger.getLogger("ERRO").info("Usuario OFF");
            }
           
       
    }


    public static String[] verifySend(String msg){
        return msg.split(";");
    }
        
    public void MessagerSave(){

    }

}