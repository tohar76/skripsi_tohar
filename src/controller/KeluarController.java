package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import model.Obat;
import model.ObatKeluar;

public class KeluarController implements Initializable{
    @FXML
    private JFXComboBox<Obat> obatCombo;
    @FXML
    private JFXTextField hargaField;
    @FXML
    private JFXTextField jumlahField;
    @FXML
    private JFXTreeTableView<ObatKeluar> tableView;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    void setData() {
       obatCombo.setItems(FXCollections.observableArrayList(Obat.listFromDB()));
    }

    private void setTableRoot() {
      
    }

    private void resetForm() {
     
    }

    void resetButton() {
       
    }

    private void resetButton2() {
     
    }

    void ambilData() {
      
    }

    private boolean validasi() {
        // Ganti return nya
        return false;
    }

    @FXML
    void pilihobataction(ActionEvent event) {
       Obat obt = obatCombo.getValue();
       if (obt != null){
           hargaField.setText(String.valueOf(obt.getHarga_jual()));           
       }
    }
    
    @FXML
    void onmousepressed(MouseEvent event) {
       
    }

    @FXML
    void batalaction(ActionEvent event) {
       
    }

    @FXML
    void tambahaction(ActionEvent event) {
     
    }

    @FXML
    void hapusaction(ActionEvent event) {
       
    }

    @FXML
    void ubahaction(ActionEvent event) {
        
    }
}
