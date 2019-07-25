/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import org.sql2o.Connection;

/**
 *
 * @author Tohar
 */
public class login extends RecursiveTreeObject<login>{
    private String username;
    private String password;
    
    public static login getLogin(String username) {
        try (Connection connection = DB.DB.sql2o.open()){
            final String query = "SELECT * FROM login WHERE `username`=:username";
            return connection.createQuery(query).addParameter("username",username).executeAndFetchFirst(login.class);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    
}
