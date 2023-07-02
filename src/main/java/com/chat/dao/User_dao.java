package com.chat.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.chat.dto.UserDTO;
import com.chat.model.Friendmodel;
import com.chat.model.MessagerModel;
import com.chat.model.Usermodel;
import com.chat.model.Usermodel.Status;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class User_dao {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private static volatile User_dao instance;
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
    public boolean createUser(Usermodel user){
        manager.getTransaction().begin();
        Query query = manager.createNativeQuery("select nome,hostname from usuario where nome = :username")
            .setParameter("username", user.getUsername());
                if (query.getResultList().isEmpty()) {
                    manager.persist(user);
                    manager.getTransaction().commit();
                    Logger.getLogger("CREATE").info("NOVO USUARIO REGISTRADO:"+user.getUsername());
                    return true;
                }else{
                    manager.getTransaction().commit();
                    Logger.getLogger("CREATE").info("USUARIO JA EXISTE NO BANCO");
                    return false;
                }
    }
    public boolean deleteUser(Usermodel user){
     manager.getTransaction().begin();
     Usermodel usermodel = manager.find(Usermodel.class,user.getId_username());
        if (usermodel == null) {
            manager.getTransaction().commit();
            return false;
        }
        manager.remove(usermodel);
        manager.getTransaction().commit();
        return true;
    }
    public Usermodel findUser(String name){
        Query query = manager
        .createNativeQuery("Select * from usuario where nome = :username",Usermodel.class)
        .setParameter("username",name);
        manager.getTransaction().begin();
        if (!query.getResultList().isEmpty()) {
            Usermodel resultModel =  (Usermodel)query.getResultList().iterator().next();
            manager.getTransaction().commit();
            return resultModel;
        }
        manager.getTransaction().commit();
        return null;
    }
    public UserDTO findUser(String name,boolean verify){
        if (verify) {
            Query query = manager
            .createNativeQuery("Select * from usuario where nome = :username",Usermodel.class)
            .setParameter("username",name);
            manager.getTransaction().begin();
            if (!query.getResultList().isEmpty()) {
                Usermodel resultModel =  (Usermodel)query.getResultList().iterator().next();
                manager.getTransaction().commit();
                UserDTO result = new UserDTO();
                result.setUsername(resultModel.getUsername());
                result.setStatus(resultModel.getStatus());
                result.setId(resultModel.getId_username().intValue());
                return result;
            }
        manager.getTransaction().commit();
        }
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
    public void AddFriend(String nameFriend,String nameUser){
        Friendmodel friendmodel = new Friendmodel();
        friendmodel.setId_friend(findUser(nameFriend));
        //friendmodel.setId_user(()->{t});
        friendmodel.setId_user(findUser(nameUser));
        manager.getTransaction().begin();
            manager.persist(friendmodel);
        manager.getTransaction().commit();
    }

}
