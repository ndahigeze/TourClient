/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Transient;

/**
 *
 * @author Ndahigeze
 */
public class Location implements Serializable {
    private int code;
    private String name;
    private String description;
    @Transient
     private Guide guide;

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }
     @Transient
    private List<Application> request;
      @Transient
    private List<LocationServicePackage> service;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Application> getRequest() {
        return request;
    }

    public void setRequest(List<Application> request) {
        this.request = request;
    }

  
    public List<LocationServicePackage> getService() {
        return service;
    }

    public void setService(List<LocationServicePackage> service) {
        this.service = service;
    }
    @Override
    public String toString(){
      return name;
    }

}
