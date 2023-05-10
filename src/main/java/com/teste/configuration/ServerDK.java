package com.teste.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import com.teste.dao.User_dao;
import com.teste.dto.FriendDTO;
import com.teste.dto.Userdto;
import com.teste.model.Usermodel.Status;
import com.teste.service.FriendsAndGroups;
import com.teste.service.SendMessage;

public class ServerDK implements Runnable  {
    private BufferedReader input;
    private PrintWriter output;
    //private  HashMap<String,FriendDTO> user = new HashMap<>();
    // private static ConcurrentHashMap<String,Userdto> accounts = new ConcurrentHashMap<>();
    private static ManagerUser accounts = new ManagerUser();
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
            server.bind(new InetSocketAddress("192.168.20.209", 3030));  
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
        ExecutorService pool = Executors.newFixedThreadPool(5);
        while (true) {
            userdto = new Userdto();
        try {
            userdto.setSocket(server.accept());
            //accounts.add(userdto);
            //new Thread(new ServerDK(userdto)).start();
            pool.submit(new ServerDK(userdto));
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
            //new FriendsAndGroups(userdto,accounts)
            FriendsAndGroups friend;
            Thread friends = new Thread(friend = new FriendsAndGroups(userdto,accounts));
            friends.start();
            // new FriendsAndGroups().users.get(friends);
            /*Sessão do usuario 100% iniciada */
            if (userdto.getStatus() == Status.ON) {
                accounts.addUser(userdto);
              
               
              
                while (true) {
                 
                    SendMessage.getInstance().forwardMessage(friend.users,userdto,input.readLine());
                      
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
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
