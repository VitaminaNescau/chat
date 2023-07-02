package com.chat.configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import org.jboss.resteasy.core.ResteasyDeploymentImpl;
import org.jboss.resteasy.plugins.server.reactor.netty.ReactorNettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;


public class ServerNetty {
    

    public void start(){
        ReactorNettyJaxrsServer server =  new ReactorNettyJaxrsServer();
        ResteasyDeployment dp = new ResteasyDeploymentImpl();
        dp.setApplicationClass(Controller.class.getName());
        server.setDeployment(dp);
        try {
            server.setHostname(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
           Logger.getGlobal().info(e.getMessage());
        }
        server.setPort(8080);
        server.deploy();
        server.start();
     
       
        
}
} 


