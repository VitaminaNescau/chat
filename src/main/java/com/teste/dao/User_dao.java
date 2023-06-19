package com.teste.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.teste.dto.FriendDTO;
import com.teste.model.Friendmodel;
import com.teste.model.MessagerModel;
import com.teste.model.Usermodel;
import com.teste.model.Usermodel.Status;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class User_dao {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private Usermodel user;
    private static volatile User_dao instance;
    //private ExecutorService poll =  Executors.newCachedThreadPool();
    private ThreadPoolExecutor poll = new ThreadPoolExecutor(0,
    Integer.MAX_VALUE,10,TimeUnit.SECONDS,new SynchronousQueue<>());
    public User_dao(){
        manager = conectionDB();
        
    }
    public static User_dao getInstance(){
        if (instance == null) {
            synchronized(User_dao.class){
                if (instance == null) {
                    instance = new User_dao();
                }
            }
        }
        return instance;
    } 
    private EntityManager conectionDB(){
        factory = Persistence.createEntityManagerFactory("serverCHAT");
        manager = factory.createEntityManager(); 
        manager.setFlushMode(FlushModeType.AUTO);
        
        return manager;
    }
    public boolean createUser(){
          
            manager.getTransaction().begin();
            Query query = manager.createNativeQuery("select nome,hostname from usuario where nome = :username or hostname = :host")
                .setParameter("username", user.getUsername())
                .setParameter("host", user.getHost());
                if (query.getResultList().isEmpty()) {
                    manager.persist(user);
                    Logger.getLogger("CREATE").info("USUARIO REGISTRADO");
                     manager.getTransaction().commit();
                     manager.close();
                    return true;
                }else{
                    Logger.getLogger("CREATE").info("USUARIO JA EXISTE NO BANCO");
                    manager.getTransaction().commit();
                    manager.close();
                    return false;
                }
        
    }
    public void deleteUser(){
       
        manager.getTransaction().begin();
        manager.remove(user);
        manager.getTransaction().commit();
        manager.close();
    }
    public FriendDTO findUser(String name){
        
        Query query = manager.createNativeQuery("Select nome,hostname,status  from usuario where nome = :username")
        .setParameter("username",name);
        manager.getTransaction().begin();
    
        if (!query.getResultList().isEmpty()) {
            FriendDTO result = (FriendDTO) query.getResultList().iterator().next();
             manager.getTransaction().commit();
             return result;
        }
        manager.getTransaction().commit();
        return null;
    }
    public List<Friendmodel> findFriend(int id){
       
        Query query = manager.createNativeQuery("Select * from amigos where id_user = :id",Friendmodel.class).setParameter("id", id);
     
       
        try {
            return poll.submit(()->{
            List<Friendmodel> result = new ArrayList<>();    
            manager.getTransaction().begin();
            result = query.getResultList();
            manager.getTransaction().commit();
            
            return result;
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        
        
       return null;
       
       
       
        
      
        
        
    
     
    }
    public Usermodel loginUser(Usermodel user){
       
        Query query = manager
        .createNativeQuery("Select *   from usuario where nome = :username and senha = :password",Usermodel.class)
        .setParameter("username",user.getUsername())
        .setParameter("password", user.getPassword());
        
        manager.getTransaction().begin();
        if(!query.getResultList().isEmpty()) {  
            user = (Usermodel) query.getResultList().iterator().next();
            user.setStatus(Status.ON);
            manager.merge(user);
           // manager.refresh(user);
          
        }else{
            manager.getTransaction().commit();
            return null;
        }
        manager.getTransaction().commit();
        return user;
    }  
    public void saveMessager(MessagerModel messager){
        
        manager.getTransaction().begin();
        manager.persist(messager);
        manager.getTransaction().commit();
    }
    public List<MessagerModel> MessagerHistory(int id){
        Query query = manager.createNativeQuery("Select * from mensagem where send_id = :id or receiver_id = :id",MessagerModel.class).setParameter("id",id);
        manager.getTransaction().begin();
        
        List<MessagerModel> result = query.getResultList();

        manager.getTransaction().commit();
        return result;
    }
}
