package com.teste.controller;

import java.util.List;

import com.teste.dto.FriendDTO;
import com.teste.dto.MessagerDTO;
import com.teste.service.FriendsAndGroups;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;




@Path("friend")
@Produces(MediaType.APPLICATION_JSON)
public class FriendController extends Application {

  @GET
    @Path("/list/{id}")
    public Response findFriends(@PathParam ("id") int id){
        List<FriendDTO> friends =  new FriendsAndGroups().allFriends(id);
        if (!friends.isEmpty()) {
            return Response.status(Status.ACCEPTED).entity(friends).build();
        }
            return Response.status(Status.NO_CONTENT).entity(friends).build();
    }

    @GET
    @Path("messager/{id}/{name}")
    public Response findMessager(@PathParam("id") int id,@PathParam("name") String name){
        List<MessagerDTO> messager = new FriendsAndGroups().MessagerHistory(id,name);
        if (!messager.isEmpty()) {
            return Response.status(Status.ACCEPTED).entity(messager).build();
        }
            return Response.status(Status.NO_CONTENT).entity(messager).build();
        
    }
    
    
}

 
