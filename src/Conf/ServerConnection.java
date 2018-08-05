/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conf;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author Ndahigeze
 */
public class ServerConnection {
     public WebTarget target(){
           ClientConfig c=new ClientConfig();
          Client client = ClientBuilder.newClient(c);
         //Connection to the Server on the Service 
          WebTarget target = client.target("http://localhost:9998");
          //String response = target.path("Users"). request().accept(MediaType.APPLICATION_JSON).get(JsonArray.class).toString();
          return target;
     }
}
