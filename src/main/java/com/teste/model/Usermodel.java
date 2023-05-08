package com.teste.model;



import java.net.Socket;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/*Essa classe contera os dados do usuario */
/**
 * Usermodel
 */
@Entity
@Table(name = "usuario")
public class Usermodel {

    public enum Status{OFF,ON}
    @Transient
    private Socket socket;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_username;
    @Column(name = "nome",unique = true,nullable = false)
    private String username;
    @Column(name = "hostname",unique = true,nullable = false)
    private String host;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Column(name = "senha",nullable = false)
    private String password;




    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public Long getId_username() {
        return id_username;
    }
    public void setId_username(Long id_username) {
        this.id_username = id_username;
    }
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
@Override
public String toString() {
    return String.format("ID: %d\nHost: %s\nUsername: %s\nStatus: %s", id_username, host, username, status.toString());
}
}
