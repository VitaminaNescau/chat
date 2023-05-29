package com.teste.controller;

import java.util.List;

import com.teste.dto.FriendDTO;
import com.teste.service.FriendsAndGroups;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("friend")
public class FriendController {
   
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FriendDTO> findFriends(@PathParam("id") int id){
        return new FriendsAndGroups().allFriends(id); 
    }

    
}
