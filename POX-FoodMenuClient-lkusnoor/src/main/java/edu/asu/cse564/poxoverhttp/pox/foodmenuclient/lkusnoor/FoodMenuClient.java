/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.poxoverhttp.pox.foodmenuclient.lkusnoor;

import javax.ws.rs.client.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Lucky
 */
public class FoodMenuClient {
    private WebTarget webTarget;
    private Client client;
    
    public final String BASE_URI = "http://localhost:8080/POX-FoodMenu-lkusnoor/webapi/inventory";
    
    public FoodMenuClient(){
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI);
    }
    
    public String getHTML() throws javax.ws.rs.ClientErrorException{
        WebTarget resource = webTarget;
        return resource.request(MediaType.TEXT_HTML).get(String.class);
    }
    
    public Response sendXML(String xml){
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).post(Entity.entity(xml, MediaType.WILDCARD_TYPE),Response.class);
    }
    
    public void close(){
        client.close();
    }

}
