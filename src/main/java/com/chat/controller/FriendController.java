package com.chat.controller;

import java.util.List;

import com.chat.dao.User_dao;
import com.chat.dto.UserDTO;
import com.chat.service.FriendsAndGroups;

import jakarta.ws.rs.Consumes;
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
@Consumes(MediaType.APPLICATION_JSON)
public class FriendController extends Application{
;
    @GET
    @Path("/list/{id}")
    public Response findFriends(@PathParam ("id") int id){
        List<UserDTO> friends =  new FriendsAndGroups().allFriends(id);
        if (!friends.isEmpty()) {
            return Response.status(Status.ACCEPTED).entity(friends).build();
        }
            return Response.status(Status.NO_CONTENT).entity(friends).build();
    }
    @GET
    @Path("list/new/{user_name}/{name}")
    public Response findNewFriend(@PathParam ("name") String name,@PathParam ("user_name") String user_name){
       UserDTO friend = User_dao.getInstance().findUser(name,true);
        if (friend != null) {
             User_dao.getInstance().AddFriend(name,user_name);
            return Response.status(Status.ACCEPTED).entity(friend).build();
        }
        return Response.status(Status.NO_CONTENT).entity(false).build();
    }
}

 
