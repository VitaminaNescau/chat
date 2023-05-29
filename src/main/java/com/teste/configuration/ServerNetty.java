package com.teste.configuration;

import java.net.URI;

import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider;
import org.glassfish.jersey.server.ResourceConfig;

import io.netty.channel.Channel;
import jakarta.ws.rs.core.UriBuilder;

public class ServerNetty {
    

    public void start(){
        URI baseApi = UriBuilder.fromUri("http://localhost/").port(8080).build();
        ResourceConfig rc = new ResourceConfig().packages("com.teste.controller");
        Channel server = NettyHttpContainerProvider.createHttp2Server(baseApi, rc, null);
        System.out.println("API Iniciada");
    }
}
