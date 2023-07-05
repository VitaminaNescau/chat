package com.chat.controller;

import java.util.List;

import com.chat.dto.MessagerDTO;
import com.chat.service.FriendsAndGroups;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("messager")
@Produces(MediaType.APPLICATION_JSON)
public class MessagerController {
    @GET
    @Path("/{id}/{name}")
    public Response findMessager(@PathParam("id") int id,@PathParam("name") String name){
        List<MessagerDTO> messager = new FriendsAndGroups().MessagerHistory(id,name);
        if (!messager.isEmpty() ) {
        return Response.status(Status.ACCEPTED).entity(messager).build();
        }
        return Response.status(Status.NO_CONTENT).entity("Não foi encontrado dados ").build();
    }

}
