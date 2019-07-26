package controller;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Obat;
import model.Supplier;

public class MasukController implements Initializable {
    @FXML
    private JFXComboBox<Obat> obatCombo;
    @FXML
    private JFXComboBox<Supplier> suppCombo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void setData() {
        obatCombo.setItems(FXCollections.observableArrayList(Obat.listFromDB()));
        suppCombo.setItems(FXCollections.observableArrayList(Supplier.listFromDB()));
    }
}
