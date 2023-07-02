package com.chat;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.logging.Logger;

import com.chat.configuration.ManagerUser;
import com.chat.configuration.ServerNetty;
import com.chat.configuration.security;
import com.chat.dao.User_dao;
import com.chat.dto.UserDTO;
import com.chat.model.Usermodel.Status;
import com.chat.service.FriendsAndGroups;
import com.chat.service.SendMessage;
public class ServerDK implements Runnable  {
    private BufferedReader input;
    private PrintStream output;
    private static ManagerUser accounts = new ManagerUser();
    private  ServerSocket server;
    private FriendsAndGroups friend;
    Logger log;
    private  UserDTO userdto;

    public ServerDK(UserDTO user){
        this.userdto = user; 
    }
    ServerDK(){
        try { 
            
            server = new ServerSocket();
           //server.setPerformancePreferences(1, 0, 0);
            server.bind(new InetSocketAddress("localhost",3030), 3030);  
            new ServerNetty().start();
            User_dao.getInstance();
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }
    public static void main(String[] args) {
        ServerDK serverMain = new ServerDK();
        serverMain.conectionServe();
        
    }
    public void conectionServe(){
        while (true) {
            userdto = new UserDTO();
        try {
            userdto.setSocket(server.accept());
            new Thread(new ServerDK(userdto)).start();
        } catch (IOException e) {
            log.info(e.getMessage());
        }
     }
    }
    @Override
    public void run() {
       
        try {
            security sc = new security();
            input = new BufferedReader(new InputStreamReader(userdto.getSocket().getInputStream()));
            String info[] = sc.verifyString(input.readLine());
            userdto = sc.login(info,userdto);
            output = new PrintStream(userdto.getSocket().getOutputStream(), true);
            if (userdto.getStatus() == Status.ON) {
                output.println("OK");
                output.println(userdto.getId());
                output.println(userdto.getUsername());
                accounts.addUser(userdto);
                friend = new FriendsAndGroups(userdto,accounts);
                Thread messager = new Thread(()->{
                Thread.currentThread().setName("MESSAGER");
                    while (true) {
                        try {
                            SendMessage.getInstance().forwardMessage(friend.users,userdto,input.readLine());
                        } catch (SocketException e) {
                            try {
                                userdto.getSocket().close();
                                System.out.println("desconectado");
                                break;
                            } catch (IOException e1) {
                                log.info(e1.getMessage());
                            }
                            log.info(e.getMessage());
                        } catch (NullPointerException | IOException e) {
                          log.info(e.getMessage());
                        }
                    }
                });
                messager.start();        
           }else{
            output.println("ERRO");
           }
           while (true) {
            if (userdto == null || userdto.getSocket().isClosed() ) {
                sc.removeUser(userdto);
                accounts.removerUSer(userdto);
                System.out.println("Usuario desconectado ");
                break;
           }
           try {
            friend.updateListFriend();
            Thread.sleep(10000);
            } catch (InterruptedException e) {
               log.info(e.getMessage());
            }
           }
        } catch (SocketException  e) {
            log.info(e.getMessage());
        } catch(IOException  e){
            log.info(e.getMessage());
         } 
         catch(NullPointerException  e){
            log.info(e.getMessage());
        }
        System.out.println("Thread desligada ");  
    }





}
