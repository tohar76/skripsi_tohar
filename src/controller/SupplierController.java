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
import model.supplier;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import static model.supplier.getSupplier;

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
    private JFXTreeTableView<supplier> tabel_suplier;

    @FXML
    private JFXButton tambah;

    @FXML
    private JFXButton ubah;

    @FXML
    private JFXButton hapus;

    @FXML
    private JFXButton batal;
    
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
        supplier spl = tabel_suplier.getSelectionModel().getSelectedItem().getValue();
        nama_supplier.setText(spl.getNama_supplier());
        alamat_supplier.setText(spl.getAlamat());
        telf_supplier.setText(spl.getTelf());
        resetButton();
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
    supplier spl = tabel_suplier.getSelectionModel().getSelectedItem().getValue();
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
           supplier spl = new supplier(
                nama_supplier.getText(),
                alamat_supplier.getText(),
                telf_supplier.getText()
                );
            if(spl.createSupplier()){
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
            supplier spl = tabel_suplier.getSelectionModel().getSelectedItem().getValue();
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
                        Alert keluar = new Alert(Alert.AlertType.INFORMATION);
                        keluar.setTitle("Data Supplier");
                        keluar.setHeaderText(null);
                        keluar.setContentText("data berhasil di ubah");
                        resetButton2();
                        keluar.show();
                        tabel_suplier.refresh();
                    }
                resetForm();
            }
            else {
                alert.close();
            }     
        }
    }


    
    public void initialize(URL url, ResourceBundle rb) {
        TreeTableColumn<supplier, Integer> idCol = new TreeTableColumn<>("Id");
        TreeTableColumn<supplier, String> namaCol = new TreeTableColumn<>("Nama");
        TreeTableColumn<supplier, String> alamatCol = new TreeTableColumn<>("Alamat");
        TreeTableColumn<supplier, String> telfCol = new TreeTableColumn<>("No Telf");
        
        idCol.setCellValueFactory(param -> param.getValue().getValue().id_supplierProperty());
        namaCol.setCellValueFactory(param -> param.getValue().getValue().nama_supplierProperty());
        alamatCol.setCellValueFactory(param -> param.getValue().getValue().alamatProperty());
        telfCol.setCellValueFactory(param -> param.getValue().getValue().telfProperty());
        
        idCol.prefWidthProperty().bind(tabel_suplier.prefWidthProperty().multiply(0.2));
        namaCol.prefWidthProperty().bind(tabel_suplier.prefWidthProperty().multiply(0.2));
        alamatCol.prefWidthProperty().bind(tabel_suplier.prefWidthProperty().multiply(0.4));
        telfCol.prefWidthProperty().bind(tabel_suplier.prefWidthProperty().multiply(0.2));
        
        tabel_suplier.getColumns().add(idCol);
        tabel_suplier.getColumns().add(namaCol);
        tabel_suplier.getColumns().add(alamatCol);
        tabel_suplier.getColumns().add(telfCol);
        
        TreeItem<supplier> supplierRoot = new RecursiveTreeItem<>(getSupplier(), RecursiveTreeObject::getChildren);
        
    
        tabel_suplier.setRoot(supplierRoot);
        tabel_suplier.setShowRoot(false);
        
    }            
}
