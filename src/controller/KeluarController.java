package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
        TreeTableColumn<ObatKeluar, Integer> idCol = new TreeTableColumn<>("Id Keluar");
        TreeTableColumn<ObatKeluar, Integer> kodeCol = new TreeTableColumn<>("Kode Obat");
        TreeTableColumn<ObatKeluar, Integer> hrgCol = new TreeTableColumn<>("Harga");
        TreeTableColumn<ObatKeluar, Integer> jumlahCol = new TreeTableColumn<>("Jumlah");
        TreeTableColumn<ObatKeluar, Date> tglCol = new TreeTableColumn<>("Tgl Keluar");
        
        idCol.setCellValueFactory(param -> param.getValue().getValue().id_keluarProperty());
        kodeCol.setCellValueFactory(param -> param.getValue().getValue().kode_obatProperty());
        hrgCol.setCellValueFactory(param -> param.getValue().getValue().hargaProperty());
        jumlahCol.setCellValueFactory(param -> param.getValue().getValue().jumlahProperty());
        tglCol.setCellValueFactory(param -> param.getValue().getValue().tgl_keluarProperty());
        
        idCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.1));
        kodeCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.2));
        hrgCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.2));
        jumlahCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.1));
        tglCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.2));

        tableView.getColumns().add(idCol);
        tableView.getColumns().add(kodeCol);
        tableView.getColumns().add(hrgCol);
        tableView.getColumns().add(jumlahCol);
        tableView.getColumns().add(tglCol);
        
        setTableRoot();
    }
    
    void setData() {
       obatCombo.setItems(FXCollections.observableArrayList(Obat.listFromDB()));
    }

    private void setTableRoot() {
        ObservableList<ObatKeluar> obatOvList = FXCollections.observableArrayList(ObatKeluar.listFromDB());
        TreeItem<ObatKeluar> obatTreeItem = new RecursiveTreeItem<>(obatOvList, RecursiveTreeObject::getChildren);
        tableView.setRoot(obatTreeItem);
        tableView.setShowRoot(false);
    }

    private void resetForm() {
        hargaField.setText("");
        jumlahField.setText("");
    }

    void resetButton() {
        tambah.setDisable(true);
        ubah.setDisable(false);
        hapus.setDisable(false);
        batal.setDisable(false);
    }

    private void resetButton2() {
        tambah.setDisable(true);
        ubah.setDisable(false);
        hapus.setDisable(false);
        batal.setDisable(false);
    }

    void ambilData() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            ObatKeluar obt = tableView.getSelectionModel().getSelectedItem().getValue();
            
            resetButton();
        }
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
     
    }

    @FXML
    void hapusaction(ActionEvent event) {
       ObatKeluar obt = tableView.getSelectionModel().getSelectedItem().getValue();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hapus Data Obat Keluar");
        alert.setHeaderText(null);
        alert.setContentText("Apakah anda yakin untuk menghapus data obat keluar?");
        Optional result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Alert keluar = new Alert(Alert.AlertType.INFORMATION);
            keluar.setTitle("Data Obat Keluar");
            keluar.setHeaderText(null);
            if (obt.deleteObatKeluar()) {
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
