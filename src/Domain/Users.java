/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ndahigeze
 */
public class Users implements Serializable {
    private int code;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String gender;
    private String emainl;
     private Privilege privilege;
     private Date RegDate;

    public Date getRegDate() {
        return RegDate;
    }

    public void setRegDate(Date RegDate) {
        this.RegDate = RegDate;
    }
    private List<Application> aplication;

    public List<Application> getAplication() {
        return aplication;
    }

    public void setAplication(List<Application> aplication) {
        this.aplication = aplication;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmainl() {
        return emainl;
    }

    public void setEmainl(String emainl) {
        this.emainl = emainl;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }
 
}
