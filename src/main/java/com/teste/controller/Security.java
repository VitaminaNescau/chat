package com.teste.controller;

import com.teste.dao.User_dao;
import com.teste.model.Usermodel;


import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
@Path("/security")
public class Security {
    
    @POST
    @Path("singup")
    @Consumes(MediaType.APPLICATION_JSON)
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

}
