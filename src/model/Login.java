package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import org.sql2o.Connection;


public class Login extends RecursiveTreeObject<Login>{
    private String username;
    private String password;
    
    public static Login getLogin(String username) {
        try (Connection connection = DB.DB.sql2o.open()){
            final String query = "SELECT * FROM login WHERE `username`=:username";
            return connection.createQuery(query).addParameter("username",username).executeAndFetchFirst(Login.class);
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