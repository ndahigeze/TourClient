/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Conf.ServerConnection;
import Domain.Message;
import Domain.Users;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Ndahigeze
 */
public class UserService {
    Converter c;
   private ServerConnection con;
    Gson gson;
     public List<Users> login(String username,String password){
         con=new ServerConnection();
        con.target();
        String response=con.target().path("service/login/"+username+"/"+password+"/"+1).request(MediaType.APPLICATION_JSON).get(JsonArray.class).toString();
                gson=new Gson();
                Type type = new TypeToken<List<Users>>() {}.getType();
                List<Users> list = gson.fromJson(response, type);
        return list;
              
     }
     //creating account
     public String createUser(Users u){
         String path="service/create";
         return userInvocation(path,u);
     }
     
     public List<Users> findByUsername(String username){
       con=new ServerConnection();
       c=new Converter();
       String response=con.target().path("service/checkSave/"+username).request(MediaType.APPLICATION_JSON).get(JsonArray.class).toString();
       List<Users> l=c.fromListJson(response);
       return l;
     }
     //invocation Builder
     public String userInvocation(String path,Users u){
        con=new ServerConnection();
        Invocation.Builder invoc = con.target().path(path).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        Response response = invoc.post(Entity.entity(u, MediaType.APPLICATION_JSON));
        Message msg=response.readEntity(Message.class);
        return msg.getMessage();
     }
     
      public Users findById(int id){
        con=new ServerConnection();
        c=new Converter();
        String response=con.target().path("service/Byid/"+id).request(MediaType.APPLICATION_JSON).get(JsonObject.class).toString();
        Users u=(Users)c.fromJson(response, Users.class);
        return u;
     }
 
}
