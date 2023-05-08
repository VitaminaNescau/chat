package com.teste.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.teste.dao.User_dao;
import com.teste.dto.FriendDTO;
import com.teste.dto.Userdto;
import com.teste.model.Usermodel.Status;
import com.teste.service.FriendsAndGroups;
import com.teste.service.SendMessage;

public class ServerDK implements Runnable {
    private BufferedReader input;
    private PrintWriter output;
    private  ConcurrentHashMap<String,FriendDTO> user = new ConcurrentHashMap<>();
    private static ArrayList<Userdto> accounts = new ArrayList<>();
    private  ServerSocket server;
    Logger log;
    private  Userdto userdto;
    // private  Usermodel  usermodel;
    // private  User_dao userDao;
    
    public ServerDK(Userdto user){
        this.userdto = user;
    }
    ServerDK(){
        try { 
            server = new ServerSocket();
            server.setPerformancePreferences(1, 0, 0);
            server.bind(new InetSocketAddress("localhost", 3030));  
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
            userdto = new Userdto();
        try {
            userdto.setSocket(server.accept());
            accounts.add(userdto);
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
            System.out.println(userdto.toString());
            SendMessage sendMessage = new SendMessage();
            FriendsAndGroups FG = new FriendsAndGroups();
            FG.allFriends(userdto)
            .forEach(e->{
                user.put(e.getUsername(),e);
            });
            // accounts.forEach(e->{
            //     if (e.getUsername().equals(user.get(e).getUsername())) {
            //         user.get(e).setSocket(e.getSocket());
            //     }
            // });

            /*Sessão do usuario 100% iniciada */
            if (userdto.getStatus() == Status.ON) {
                //user.put(userdto.getUsername(), userdto);
                while (true) {
                    sendMessage.forwardMessage(user,userdto,input.readLine());    
                }           
           }
            System.out.println(userdto.toString());
            output = new PrintWriter(userdto.getSocket().getOutputStream(), true);
            output.print("DESCONECTADO");
            System.out.println("Usuario desconectado");
            sc.removeUser(userdto); 
           // log.info("ENCERRADO");
        } catch (SocketException  e) {
            log.info(e.getMessage());
        } catch(IOException  e){
            log.info(e.getMessage());
        } catch(NullPointerException  e){
            log.info(e.getMessage());
        }
        
    }





}
