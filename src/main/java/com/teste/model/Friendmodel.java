package com.teste.model;

import org.hibernate.annotations.Formula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;


@Entity
@Table(name = "amigos", 
uniqueConstraints = @UniqueConstraint(columnNames = {"min_user_friend", "max_user_friend"}))

public class Friendmodel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "id_user",referencedColumnName = "id_username",unique = false)
    private Usermodel id_user;
    @ManyToOne
    @JoinColumn(name = "id_friend",referencedColumnName = "id_username",unique = false) 
    private Usermodel id_friend;
    @Formula("LEAST(id_user, id_friend)")
    @Column(name = "min_user_friend")
    private Long minUserFriend;
 
    @Formula("GREATEST(id_user, id_friend)")
    @Column(name = "max_user_friend")
    private Long maxUserFriend;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Usermodel getId_user() {
        return id_user;
    }
    public void setId_user(Usermodel id_user) {
        this.id_user = id_user;
    }
    public Usermodel getId_friend() {
        return id_friend;
    }
    public void setId_friend(Usermodel id_friend) {
        this.id_friend = id_friend;
    }


}
