/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import model.Supplier;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

/**
 *
 * @author Tohar
 */
public class SupplierController implements Initializable{
    @FXML
    private JFXTextField nama_supplier;
    @FXML
    private JFXTextArea alamat_supplier;
    @FXML
    private JFXTextField telf_supplier;
    @FXML
    private JFXTreeTableView<Supplier> tableView;
    @FXML
    private JFXButton tambah;
    @FXML
    private JFXButton ubah;
    @FXML
    private JFXButton hapus;
    @FXML
    private JFXButton batal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TreeTableColumn<Supplier, Integer> idCol = new TreeTableColumn<>("Id");
        TreeTableColumn<Supplier, String> namaCol = new TreeTableColumn<>("Nama");
        TreeTableColumn<Supplier, String> alamatCol = new TreeTableColumn<>("Alamat");
        TreeTableColumn<Supplier, String> telfCol = new TreeTableColumn<>("No Telf");
        
        idCol.setCellValueFactory(param -> param.getValue().getValue().id_supplierProperty());
        namaCol.setCellValueFactory(param -> param.getValue().getValue().nama_supplierProperty());
        alamatCol.setCellValueFactory(param -> param.getValue().getValue().alamatProperty());
        telfCol.setCellValueFactory(param -> param.getValue().getValue().telfProperty());
        
        idCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.2));
        namaCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.2));
        alamatCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.4));
        telfCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.2));
        
        tableView.getColumns().add(idCol);
        tableView.getColumns().add(namaCol);
        tableView.getColumns().add(alamatCol);
        tableView.getColumns().add(telfCol);
        setTableRoot();
    }     
    
    private void setTableRoot() {
        ObservableList<Supplier> suppOvList = FXCollections.observableArrayList(Supplier.listFromDB());
        TreeItem<Supplier> supplierRoot = new RecursiveTreeItem<>(suppOvList, RecursiveTreeObject::getChildren);
        tableView.setRoot(supplierRoot);
        tableView.setShowRoot(false);
    }
    
    void resetForm(){
        nama_supplier.setText("");
        alamat_supplier.setText("");
        telf_supplier.setText("");
    }
    
    void resetButton(){
        tambah.setDisable(true);
        ubah.setDisable(false);
        hapus.setDisable(false);
        batal.setDisable(false);
    }
    
    private void resetButton2(){
        tambah.setDisable(false);
        ubah.setDisable(true);
        hapus.setDisable(true);
        batal.setDisable(true);
    }
    
    void ambilData(){
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Supplier spl = tableView.getSelectionModel().getSelectedItem().getValue();
            nama_supplier.setText(spl.getNama_supplier());
            alamat_supplier.setText(spl.getAlamat());
            telf_supplier.setText(spl.getTelf());
            resetButton();     
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
    void hapusaction(ActionEvent event) {
    Supplier spl = tableView.getSelectionModel().getSelectedItem().getValue();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hapus Data Supplier");
        alert.setHeaderText(null);
        alert.setContentText("Apakah anda yakin untuk menghapus data supplier?");
        Optional result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Alert keluar = new Alert(Alert.AlertType.INFORMATION);
            keluar.setTitle("Data Supplier");
            keluar.setHeaderText(null);
            if(spl.deleteSupplier()){
                setTableRoot();
                keluar.setContentText("data berhasil di hapus");
                keluar.show();
                resetButton2();
                resetForm();
            } else {
                keluar.setContentText("data gagal di hapus");
                keluar.show();
            }
        }
        else {
            alert.close();
        }
    }

    private boolean validasi(){
        return !nama_supplier.getText().isEmpty() && !alamat_supplier.getText().isEmpty() && !telf_supplier.getText().isEmpty();
    }
    
    @FXML
    void tambahaction(ActionEvent event) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Supplier");
        if(validasi()){
           Supplier spl = new Supplier(
                nama_supplier.getText(),
                alamat_supplier.getText(),
                telf_supplier.getText()
                );
            if(spl.createSupplier()){
                setTableRoot();
                alert.setContentText("Data Berhasil Disimpan");
                alert.show();
                resetForm();
            }
        }else {
            alert.setContentText("Data Tidak Lengkap");
            alert.show();
        }
    }

    @FXML
    void ubahaction(ActionEvent event) {
        if(validasi()){
            Supplier spl = tableView.getSelectionModel().getSelectedItem().getValue();
            spl.setNama_supplier(nama_supplier.getText());
            spl.setAlamat(alamat_supplier.getText());
            spl.setTelf(telf_supplier.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edit Data Supplier");
            alert.setHeaderText(null);
            alert.setContentText("Apakah anda yakin untuk mengedit data supplier?");
            Optional result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if(spl.updateSupplier()){
                        tableView.refresh();
                        Alert keluar = new Alert(Alert.AlertType.INFORMATION);
                        keluar.setTitle("Data Supplier");
                        keluar.setHeaderText(null);
                        keluar.setContentText("data berhasil di ubah");
                        resetButton2();
                        keluar.show();
                    }
                resetForm();
            }
            else {
                alert.close();
            }     
        }
    }
}
