package com.teste.configuration;

import org.jboss.resteasy.core.ResteasyDeploymentImpl;
import org.jboss.resteasy.plugins.server.reactor.netty.ReactorNettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

// import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpContainerProvider;
// import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
// import org.glassfish.jersey.server.ResourceConfig;

public class ServerNetty {
    

    public void start(){
        ReactorNettyJaxrsServer server =  new ReactorNettyJaxrsServer();
        ResteasyDeployment dp = new ResteasyDeploymentImpl();
        dp.setApplicationClass(Controller.class.getName());
        server.setDeployment(dp);
        server.setHostname("localhost");
        server.setPort(8080);
        server.start();
        // GrizzlyHttpServerFactory
        // .createHttpServer(URI.create("http://localhost:8080/"),
        // new ResourceConfig().packages("com.teste.controller"));
        
}
} 


