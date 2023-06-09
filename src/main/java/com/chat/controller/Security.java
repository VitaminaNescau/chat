package com.chat.controller;

import com.chat.dao.User_dao;
import com.chat.model.Usermodel;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
@Path("/security")
@Consumes(MediaType.APPLICATION_JSON)
public class Security {
    
    @POST
    @Path("singup")
    public Response singUp(Usermodel user){
        if (user == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        else{
            if(User_dao.getInstance().createUser(user)){
                 return Response.status(Status.CREATED).entity("Conta criada com sucesso").build();
            }else{
                return Response.status(Status.NOT_ACCEPTABLE).entity("Conta já existe").build();
            }
           
        }
        
        
    }
    // @PUT
    // @Path("delete")
    // public Response DeleteAccount(Usermodel user){
    //     if (user == null) {
    //         return Response.status(Status.NOT_ACCEPTABLE).entity("Conta não encontrada").build();
    //     }else{
    //         return Response.status(Status.ACCEPTED).entity(User_dao.getInstance().deleteUser(user)).build();
    // }
    // }
}
