/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Tohar
 */
public class MenuController implements Initializable{
     @FXML
    private JFXButton dataobat;

    @FXML
    private JFXButton obatmasuk;

    @FXML
    private JFXButton obatkeluar;

    @FXML
    private JFXButton datasupplier;

    @FXML
    private JFXButton logout;

    @FXML
    private ScrollPane scrollpane;

    AnchorPane tampilan1;
    AnchorPane tampilan2;
    AnchorPane tampilan3;
    AnchorPane tampilan4;
    
    ObatController obatCtrl;
    MasukController masukCtrl;
    KeluarController keluarCtrl;
    
    @FXML
    void actionhandle(ActionEvent event) throws IOException {
        if (event.getSource() == dataobat){
            scrollpane.setContent(tampilan1);
            obatCtrl.setData();
        }else if (event.getSource() == obatmasuk){
            scrollpane.setContent(tampilan2);
            masukCtrl.setData();
        }else if (event.getSource() == obatkeluar) {
            scrollpane.setContent(tampilan3);
            keluarCtrl.setData();
        }else if (event.getSource() == datasupplier){
            scrollpane.setContent(tampilan4);
        }else if(event.getSource() == logout){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent parent = loader.load();
            Stage stage = (Stage)logout.getScene().getWindow();
            stage.setScene(new Scene(parent));
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getResource("/view/data obat.fxml"));
            tampilan1 = fxml.load();
            obatCtrl = fxml.getController();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getResource("/view/obat masuk.fxml"));
            tampilan2 = fxml.load();
            masukCtrl = fxml.getController();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FXMLLoader fxml = new FXMLLoader();
            fxml.setLocation(getClass().getResource("/view/obat keluar.fxml"));
            tampilan3 = fxml.load();
            keluarCtrl = fxml.getController();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            tampilan4 = FXMLLoader.load(getClass().getResource("/view/data supplier.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        scrollpane.setContent(tampilan1);
    }
    }

