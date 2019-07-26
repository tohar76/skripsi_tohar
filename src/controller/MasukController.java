package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import model.Obat;
import model.ObatMasuk;
import model.Supplier;

public class MasukController implements Initializable {
    @FXML
    private JFXComboBox<Obat> obatCombo;
    @FXML
    private JFXComboBox<Supplier> suppCombo;
    @FXML
    private JFXTextField jumlahField;
    @FXML
    private JFXDatePicker expDatePicker;
    @FXML
    private JFXTreeTableView<ObatMasuk> tableView;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void setData() {
        obatCombo.setItems(FXCollections.observableArrayList(Obat.listFromDB()));
        suppCombo.setItems(FXCollections.observableArrayList(Supplier.listFromDB()));
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
