/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Conf.ServerConnection;
import Domain.Application;
import Domain.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public class ApplicationService {
    ServerConnection con;
    Gson gson;
       public String createRequest(Application u){
         String path="applicationService/create";
         return requestInvocation(path,u);
     }
     
    public String requestInvocation(String path,Application u){
        con=new ServerConnection();
        Invocation.Builder invoc = con.target().path(path).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        Response response = invoc.post(Entity.entity(u, MediaType.APPLICATION_JSON));
        Message msg=response.readEntity(Message.class);
        return msg.getMessage();
     }
    
    public List<Application> tripDetails(int id,int locId){
        con=new ServerConnection(); 
        con.target();
        String response=con.target().path("applicationService/getDetails/"+id+"/"+locId).request(MediaType.APPLICATION_JSON).get(JsonArray.class).toString();
                Gson gso = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                Type type = new TypeToken<List<Application>>() {}.getType();
                List<Application> list = gso.fromJson(response, type);
        return list;
    }
     
     public List<Application> findByDoneBy(int id){
        con=new ServerConnection(); 
        con.target();
        String response=con.target().path("applicationService/findDoneByCustomer/"+id+"/"+1).request(MediaType.APPLICATION_JSON).get(JsonArray.class).toString();
                Gson gso = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                Type type = new TypeToken<List<Application>>() {}.getType();
                List<Application> list = gso.fromJson(response, type);
        return list;
     }
}
