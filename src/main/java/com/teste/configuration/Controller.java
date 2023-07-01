package com.teste.configuration;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;
import com.teste.controller.FriendController;
import com.teste.controller.MessagerController;
import com.teste.controller.Security;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;

@ApplicationPath("/")
@Produces(MediaType.APPLICATION_JSON)
public class Controller extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(FriendController.class);
        classes.add(MessagerController.class);
        classes.add(Security.class);
        classes.add(JacksonJsonProvider.class);
        return classes;
    }

}
