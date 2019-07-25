/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
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

/**
 *
 * @author Tohar
 */
public class ObatController implements Initializable {

    @FXML
    private JFXTextField namaField;
    @FXML
    private JFXTextField jenisField;
    @FXML
    private JFXTextField stokField;
    @FXML
    private JFXTextField satuanField;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXTreeTableView<Obat> tableView;
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
        TreeTableColumn<Obat, Integer> kodeCol = new TreeTableColumn<>("Kode");
        TreeTableColumn<Obat, String> namaCol = new TreeTableColumn<>("Nama Obat");
        TreeTableColumn<Obat, String> jenisCol = new TreeTableColumn<>("Jenis");
        TreeTableColumn<Obat, Integer> stokCol = new TreeTableColumn<>("Stok");
        TreeTableColumn<Obat, Integer> satCol = new TreeTableColumn<>("Satuan");
        TreeTableColumn<Obat, String> tglCol = new TreeTableColumn<>("Tanggal");

        kodeCol.setCellValueFactory(param -> param.getValue().getValue().kodeProperty());
        namaCol.setCellValueFactory(param -> param.getValue().getValue().nama_obatProperty());
        jenisCol.setCellValueFactory(param -> param.getValue().getValue().jenis_obatProperty());
        stokCol.setCellValueFactory(param -> param.getValue().getValue().stokProperty());
        satCol.setCellValueFactory(param -> param.getValue().getValue().satuanProperty());
        tglCol.setCellValueFactory(param -> param.getValue().getValue().tanggalProperty());

        kodeCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.2));
        namaCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.2));
        jenisCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.4));
        stokCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.4));
        satCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.4));
        tglCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.2));

        tableView.getColumns().add(kodeCol);
        tableView.getColumns().add(namaCol);
        tableView.getColumns().add(jenisCol);
        tableView.getColumns().add(stokCol);
        tableView.getColumns().add(satCol);
        tableView.getColumns().add(tglCol);
        setTableRoot();
    }

    private void setTableRoot() {
        ObservableList<Obat> obatOvList = FXCollections.observableArrayList(Obat.listFromDB());
        TreeItem<Obat> obatTreeItem = new RecursiveTreeItem<>(obatOvList, RecursiveTreeObject::getChildren);
        tableView.setRoot(obatTreeItem);
        tableView.setShowRoot(false);
    }

    private boolean validasi() {
        return !namaField.getText().isEmpty()
                && !jenisField.getText().isEmpty()
                && !stokField.getText().isEmpty()
                && !satuanField.getText().isEmpty()
                && datePicker.getValue() != null;
    }

    private void resetForm() {
        namaField.setText("");
        jenisField.setText("");
        stokField.setText("");
        satuanField.setText("");
        datePicker.setValue(null);
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
            Obat obt = tableView.getSelectionModel().getSelectedItem().getValue();
            namaField.setText(obt.getNama_obat());
            jenisField.setText(obt.getJenis_obat());
            stokField.setText(String.valueOf(obt.getStok()));
            satuanField.setText(String.valueOf(obt.getSatuan()));
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
    void tambahaction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Obat");
        if (validasi()) {
            Obat obt = new Obat(
                    namaField.getText(),
                    jenisField.getText(),
                    Integer.parseInt(stokField.getText()),
                    Integer.parseInt(satuanField.getText()),
                    new Date()
            );
            if (obt.createObat()) {
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
        Obat obt = tableView.getSelectionModel().getSelectedItem().getValue();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hapus Data Obat");
        alert.setHeaderText(null);
        alert.setContentText("Apakah anda yakin untuk menghapus data obat?");
        Optional result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Alert keluar = new Alert(Alert.AlertType.INFORMATION);
            keluar.setTitle("Data Obat");
            keluar.setHeaderText(null);
            if (obt.deleteObat()) {
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
        if(validasi()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edit Data Obat");
            Obat obt = tableView.getSelectionModel().getSelectedItem().getValue();
            obt.setNama_obat(namaField.getText());
            obt.setJenis_obat(jenisField.getText());
            obt.setStok(Integer.parseInt(stokField.getText()));
            obt.setSatuan(Integer.parseInt(satuanField.getText()));
            alert.setHeaderText(null);
            alert.setContentText("Apakah anda yakin untuk mengedit data obat?");
            Optional result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if(obt.updateObat()){
                        Alert keluar = new Alert(Alert.AlertType.INFORMATION);
                        keluar.setTitle("Data Obat");
                        keluar.setHeaderText(null);
                        keluar.setContentText("data berhasil di ubah");
                        resetButton2();
                        keluar.show();
                        tableView.refresh();
                    }
                resetForm();
            }
            else {
                alert.close();
            }     
        }  else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Data Obat");
            alert.setContentText("Data Tidak Lengkap");
            alert.show();
        }
    }

}