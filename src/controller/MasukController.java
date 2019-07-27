package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
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
    @FXML
    private JFXButton tambah;
    @FXML
    private JFXButton ubah;
    @FXML
    private JFXButton hapus;
    @FXML
    private JFXButton batal;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeTableColumn<ObatMasuk, Integer> idCol = new TreeTableColumn<>("Id Masuk");
        TreeTableColumn<ObatMasuk, Integer> kodeCol = new TreeTableColumn<>("Kode Obat");
        TreeTableColumn<ObatMasuk, Integer> jumlahCol = new TreeTableColumn<>("Jumlah");
        TreeTableColumn<ObatMasuk, Integer> idSupplierCol = new TreeTableColumn<>("Id Supplier");
        TreeTableColumn<ObatMasuk, Date> tglExpiredCol = new TreeTableColumn<>("Tgl Expired");
        TreeTableColumn<ObatMasuk, Date> tglMasukCol = new TreeTableColumn<>("Tgl Masuk");

        idCol.setCellValueFactory(param -> param.getValue().getValue().id_masukProperty());
        kodeCol.setCellValueFactory(param -> param.getValue().getValue().kode_obatProperty());
        jumlahCol.setCellValueFactory(param -> param.getValue().getValue().jumlahProperty());
        idSupplierCol.setCellValueFactory(param -> param.getValue().getValue().id_supplierProperty());
        tglExpiredCol.setCellValueFactory(param -> param.getValue().getValue().tgl_expiredProperty());
        tglMasukCol.setCellValueFactory(param -> param.getValue().getValue().tgl_masukProperty());

        idCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.1));
        kodeCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.2));
        jumlahCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.2));
        idSupplierCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.1));
        tglExpiredCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.2));
        tglMasukCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.2));

        tableView.getColumns().add(idCol);
        tableView.getColumns().add(kodeCol);
        tableView.getColumns().add(jumlahCol);
        tableView.getColumns().add(idSupplierCol);
        tableView.getColumns().add(tglExpiredCol);
        tableView.getColumns().add(tglMasukCol);
        setTableRoot();
    }
       
    
    public void setData() {
        obatCombo.setItems(FXCollections.observableArrayList(Obat.listFromDB()));
        suppCombo.setItems(FXCollections.observableArrayList(Supplier.listFromDB()));
    }
    
    private void setTableRoot() {
        ObservableList<ObatMasuk> obatOvList = FXCollections.observableArrayList(ObatMasuk.listFromDB());
        TreeItem<ObatMasuk> obatmasukTreeItem = new RecursiveTreeItem<>(obatOvList, RecursiveTreeObject::getChildren);
        tableView.setRoot(obatmasukTreeItem);
        tableView.setShowRoot(false);
      
    }

    private void resetForm() {
        jumlahField.setText("");
    }

    void resetButton() {
        tambah.setDisable(true);
        ubah.setDisable(false);
        hapus.setDisable(false);
        batal.setDisable(false);
    }

    private void resetButton2() {
        tambah.setDisable(false);
        ubah.setDisable(true);
        hapus.setDisable(true);
        batal.setDisable(true);
    }

    void ambilData() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            ObatMasuk obt = tableView.getSelectionModel().getSelectedItem().getValue();
            resetButton();
        }
    }

    private boolean validasi() {
        // Ganti return nya
        return false;
    }

    @FXML
    void onmousepressed(MouseEvent event) {
        if (event.getClickCount() == 1) {
            ambilData();
        }
       
    }

    @FXML
    void batalaction(ActionEvent event) {
         resetForm();
        resetButton2();
       
    }

    @FXML
    void tambahaction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ObatMasuk");
        if (validasi()) {
            ObatMasuk obt = new ObatMasuk(
                    obatCombo.getItems(),
        suppCombo.getItems(),
                    //id_masuk.getText(),
                    //jenisField.getText(),
                    //satuanField.getText(),
                    Integer.parseInt(jumlahField.getText())
                    //Integer.parseInt(hargaJualField.getText())
            );
            if (obt.createObatMasuk()) {
                alert.setContentText("Data Berhasil Disimpan");
                alert.show();
                setTableRoot();
                resetForm();
            }
        } else {
            alert.setContentText("Data Tidak Lengkap");
            alert.show();
        }
    }

    @FXML
    void hapusaction(ActionEvent event) {
        ObatMasuk obt = tableView.getSelectionModel().getSelectedItem().getValue();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hapus Data Obat Masuk");
        alert.setHeaderText(null);
        alert.setContentText("Apakah anda yakin untuk menghapus data obat masuk?");
        Optional result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Alert keluar = new Alert(Alert.AlertType.INFORMATION);
            keluar.setTitle("Data Obat mMasuk");
            keluar.setHeaderText(null);
            if (obt.deleteObatMasuk()) {
                keluar.setContentText("data berhasil di hapus");
                keluar.show();
                resetButton2();
                setTableRoot();
                resetForm();
            } else {
                keluar.setContentText("data gagal di hapus");
                keluar.show();
            }
        } else {
            alert.close();
        }
    }

    @FXML
    void ubahaction(ActionEvent event) {
        
    }
}
