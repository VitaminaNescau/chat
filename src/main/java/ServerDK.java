

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.logging.Logger;

import com.teste.configuration.ManagerUser;
import com.teste.configuration.ServerNetty;
import com.teste.configuration.security;
import com.teste.dao.User_dao;
import com.teste.dto.Userdto;
import com.teste.model.Usermodel.Status;
import com.teste.service.FriendsAndGroups;
import com.teste.service.SendMessage;
public class ServerDK implements Runnable  {
    private BufferedReader input;
    private PrintStream output;
    //private  HashMap<String,FriendDTO> user = new HashMap<>();
    // private static ConcurrentHashMap<String,Userdto> accounts = new ConcurrentHashMap<>();
    private static ManagerUser accounts = new ManagerUser();
    private  ServerSocket server;
    private FriendsAndGroups friend;
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
        //ExecutorService pool = Executors.newFixedThreadPool(50);
        
        while (true) {
            userdto = new Userdto();
        try {
            userdto.setSocket(server.accept());
            
            //accounts.add(userdto);
            new Thread(new ServerDK(userdto)).start();
            //pool.submit(new ServerDK(userdto));
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
            System.out.println(info[0]+info[1]);
            userdto = sc.login(info,userdto);
            output = new PrintStream(userdto.getSocket().getOutputStream(), true);
            /*Sessão do usuario 100% iniciada */
            if (userdto.getStatus() == Status.ON) {
                output.println("OK");
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
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        } catch (NullPointerException | IOException e) {
                          
                            e.printStackTrace();
                        }

                    }
                });
                messager.start();
                         
           }
           while (true) {
            if (userdto.getSocket().isClosed()) {
                        sc.removeUser(userdto);
                        accounts.removerUSer(userdto);
                        System.out.println("Usuario desconectado ");
                        break;
           }
           try {
            friend.updateListFriend();
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            
           }
           
         
        } catch (SocketException  e) {
            log.info(e.getMessage());
        } catch(IOException  e){
            log.info(e.getMessage());
        } catch(NullPointerException  e){
            log.info(e.getMessage());
        }
        System.out.println("Thread desligada ");  
    }





}
