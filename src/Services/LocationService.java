/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Conf.ServerConnection;
import Domain.Location;
import Domain.Message;
import Domain.Users;
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
public class LocationService {
    ServerConnection con;
    Converter c;
    Gson g;
    public void createLocation(Location u){
         String path="location/create";
          userInvocation(path,u);
     }
    public List<Location> viewLocation(){
              g = new Gson();
              con=new ServerConnection();
              String response=con.target().path("location/viewLocation").
                      request(MediaType.APPLICATION_JSON).
                      get(JsonArray.class).
                      toString();
               Type type = new TypeToken<List<Location>>() {}.getType();
                List<Location> list = g.fromJson(response, type);
                return list;
    }
    public List<Location> viewByName(String name){
         g = new Gson();
              con=new ServerConnection();
              String response=con.target().path("location/findByName/"+name).
                      request(MediaType.APPLICATION_JSON).
                      get(JsonArray.class).
                      toString();
               Type type = new TypeToken<List<Location>>() {}.getType();
                List<Location> list = g.fromJson(response, type);
                return list;
    }
    public String updateList(Location l){
       g = new Gson();
              con=new ServerConnection();
            con=new ServerConnection();
       Invocation.Builder invoc = con.target().path("location/update").
                request(MediaType.APPLICATION_JSON).
               accept(MediaType.APPLICATION_JSON);
        Response response = invoc.put(Entity.entity(l, MediaType.APPLICATION_JSON));             
      Message  msg=response.readEntity(Message.class);
      return msg.getMessage();
    }
   public void userInvocation(String path,Location u){
        con=new ServerConnection();
       Invocation.Builder invoc = con.target().path(path).
                request(MediaType.APPLICATION_JSON).
               accept(MediaType.APPLICATION_JSON);
        Response response = invoc.post(Entity.entity(u, MediaType.APPLICATION_JSON));
     }
}
