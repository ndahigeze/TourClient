/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Conf.ServerConnection;
import Domain.Location;
import Domain.LocationServicePackage;
import Domain.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import javax.json.JsonArray;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Ndahigeze
 */
public class ServicePackage {
    ServerConnection con;
    Converter c;
    Gson g;
    public void createTripService(LocationServicePackage u){
         String path="servicePackage/create";
         invocation(path,u);
     }
    public List<LocationServicePackage> viewTripService(){
              g = new Gson();
              con=new ServerConnection();
              String response=con.target().path("servicePackage/viewTripService").
                      request(MediaType.APPLICATION_JSON).
                      get(JsonArray.class).
                      toString();
               Type type = new TypeToken<List<LocationServicePackage>>() {}.getType();
                List<LocationServicePackage> list = g.fromJson(response, type);
                return list;
    }
    public List<LocationServicePackage> findByLocation(int id){
         g = new Gson();
              con=new ServerConnection();
              String response=con.target().path("servicePackage/viewByLocation/"+id).
                      request(MediaType.APPLICATION_JSON).
                      get(JsonArray.class).
                      toString();
               Type type = new TypeToken<List<LocationServicePackage>>() {}.getType();
                List<LocationServicePackage> list = g.fromJson(response, type);
                return list;
    }
   public void invocation(String path,LocationServicePackage u){
        con=new ServerConnection();
        Invocation.Builder invoc = con.target().path(path).
                request(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON);
        Response response = invoc.post(Entity.entity(u, MediaType.APPLICATION_JSON));
   }
   
   public String serviceUpdater(LocationServicePackage u){
        con=new ServerConnection();
        Invocation.Builder invoc = con.target().path("servicePackage/update").
                request(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON);
        Response response = invoc.put(Entity.entity(u, MediaType.APPLICATION_JSON));
        Message msg=response.readEntity(Message.class);
        return msg.getMessage();
   }
    
}
