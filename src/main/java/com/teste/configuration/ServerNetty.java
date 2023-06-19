package com.teste.configuration;


import org.jboss.resteasy.core.ResteasyDeploymentImpl;
import org.jboss.resteasy.plugins.server.reactor.netty.ReactorNettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;



public class ServerNetty {
    

    public void start(){
        ReactorNettyJaxrsServer server =  new ReactorNettyJaxrsServer();
        ResteasyDeployment dp = new ResteasyDeploymentImpl();
        dp.setApplicationClass(Controller.class.getName());
        server.setDeployment(dp);
        server.setPort(8080);
        server.start();
    
}
} 
 class ExceptionMappingProvider implements ExceptionMapper<Exception> {
        @Override
        public Response toResponse(Exception exception) {
            if (exception instanceof IllegalStateException) {
                return Response.status(Status.BAD_REQUEST).build();
            }
            return Response.serverError().build();
        }

    }

