package com.teste.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
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
    public void forwardMessage(ConcurrentHashMap<String, FriendDTO> users,Userdto user,String msg){
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
            e.printStackTrace();
        }
           
      
    }
    /*metodo para as mensagens em grupo*/
    public void sendAll(ConcurrentHashMap<String, FriendDTO> users,Userdto userdto,String msg[]) {
        users.forEach((c,v)->{
                try {
                output = new PrintWriter(v.getSocket().getOutputStream(), true);
                output.println(userdto.getUsername()+": "+msg[1]);
                System.out.println("envio mensagem pra grupo"); 
            } catch (IOException e) {
                    e.printStackTrace();
                }
        });
    }
    /*metodo para as mensagens privadas*/
    public void sendFor(ConcurrentHashMap<String, FriendDTO> users,Userdto user,String msg[]) throws IOException, NullPointerException{
        
            System.out.println(msg[0]+" "+msg[1]);
            output = new PrintWriter(users.get(msg[0]).getSocket().getOutputStream(), true);
            output.println(user.getUsername()+";"+msg[1]);
            System.out.println("enviou mensagem para " + users.get(msg[0]).getUsername());
       
    }


    public static String[] verifySend(String msg){
        return msg.split(";");
    }
        
}