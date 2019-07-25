/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.login;

/**
 * FXML Controller class
 *
 * @author Tohar
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;
    
    @FXML
    private JFXButton login;

    @FXML
    private JFXButton batal;

    @FXML
    void actionBatal(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    void actionLogin(ActionEvent event) throws IOException {
        login log = model.login.getLogin(username.getText());
        if (log !=null){
            if (log.getPassword().equals(password.getText())){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu utama.fxml"));
                Parent parent = loader.load();
                Stage stage = (Stage)login.getScene().getWindow();
                stage.setScene(new Scene(parent));
                stage.centerOnScreen();
                
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setContentText("Password Salah");
                alert.show();
                password.requestFocus();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setContentText("Username tidak ditemukan");
            alert.show();
            username.requestFocus();
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
