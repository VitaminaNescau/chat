package com.teste.configuration;

import org.jboss.resteasy.core.ResteasyDeploymentImpl;
import org.jboss.resteasy.plugins.server.reactor.netty.ReactorNettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;


public class ServerNetty {
    

    public void start(){
        ReactorNettyJaxrsServer server =  new ReactorNettyJaxrsServer();
        ResteasyDeployment dp = new ResteasyDeploymentImpl();
        dp.setApplicationClass(Controller.class.getName());
        server.setDeployment(dp);
        server.setHostname("localhost");
        server.setPort(8080);
        server.start();
       
        
}
} 


