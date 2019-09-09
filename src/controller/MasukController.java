package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
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
import model.laporan;

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
    @FXML
    private JFXButton print;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeTableColumn<ObatMasuk, Integer> idCol = new TreeTableColumn<>("Id");
        TreeTableColumn<ObatMasuk, String> namaObatCol = new TreeTableColumn<>("Nama Obat");
        TreeTableColumn<ObatMasuk, Integer> jumlahCol = new TreeTableColumn<>("Jumlah");
        TreeTableColumn<ObatMasuk, String> namaSuppCol = new TreeTableColumn<>("Nama Supplier");
        TreeTableColumn<ObatMasuk, Date> tglExpiredCol = new TreeTableColumn<>("Tgl Expired");
        TreeTableColumn<ObatMasuk, Date> tglMasukCol = new TreeTableColumn<>("Tgl Masuk");

        idCol.setCellValueFactory(param -> param.getValue().getValue().id_masukProperty());
        namaObatCol.setCellValueFactory(param -> param.getValue().getValue().nama_obatProperty());
        jumlahCol.setCellValueFactory(param -> param.getValue().getValue().jumlahProperty());
        namaSuppCol.setCellValueFactory(param -> param.getValue().getValue().nama_supplierProperty());
        tglExpiredCol.setCellValueFactory(param -> param.getValue().getValue().tgl_expiredProperty());
        tglMasukCol.setCellValueFactory(param -> param.getValue().getValue().tgl_masukProperty());

        idCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.1));
        namaObatCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.2));
        jumlahCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.1));
        namaSuppCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.2));
        tglExpiredCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.2));
        tglMasukCol.prefWidthProperty().bind(tableView.prefWidthProperty().multiply(0.2));

        tableView.getColumns().add(idCol);
        tableView.getColumns().add(namaObatCol);
        tableView.getColumns().add(jumlahCol);
        tableView.getColumns().add(namaSuppCol);
        tableView.getColumns().add(tglExpiredCol);
        tableView.getColumns().add(tglMasukCol);
        setTableRoot();
        resetButton2();
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
        obatCombo.getSelectionModel().clearSelection();
        suppCombo.getSelectionModel().clearSelection();
        jumlahField.setText("");
        expDatePicker.setValue(null);
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

    @FXML
    void printlaporan(ActionEvent event) throws IOException {
        laporan.daftar_masuk();
    }
    
    void ambilData() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            ObatMasuk obtMasuk = tableView.getSelectionModel().getSelectedItem().getValue();
            Obat obt = obatCombo
                    .getItems()
                    .stream()
                    .filter(obat -> obat.getKode_obat() == obtMasuk.getKode_obat())
                    .findFirst()
                    .orElse(null);
            Supplier supp = suppCombo
                    .getItems()
                    .stream()
                    .filter(spp -> spp.getId_supplier() == obtMasuk.getId_supplier())
                    .findFirst()
                    .orElse(null);
            Date tgl = obtMasuk.getTgl_expired();
            LocalDate localTgl = new Date(tgl.getYear(), tgl.getMonth(), tgl.getDate()).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            obatCombo.getSelectionModel().select(obt);
            suppCombo.getSelectionModel().select(supp);
            jumlahField.setText(String.valueOf(obtMasuk.getJumlah()));
            expDatePicker.setValue(localTgl);
            resetButton();
        }
    }

    private boolean validasi() {
        return obatCombo.getSelectionModel().getSelectedItem() != null
                && suppCombo.getSelectionModel().getSelectedItem() != null
                && !jumlahField.getText().isEmpty()
                && expDatePicker.getValue() != null;
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
        alert.setTitle("Obat Masuk");
        if (validasi()) {
            Date tgl = java.util.Date.from(expDatePicker.getValue().atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
            ObatMasuk obt = new ObatMasuk(
                    obatCombo.getSelectionModel().getSelectedItem().getKode_obat(),
                    Integer.parseInt(jumlahField.getText()),
                    suppCombo.getSelectionModel().getSelectedItem().getId_supplier(),
                    tgl,
                    new Date()
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
        if (validasi()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edit Data Obat Masuk");
            ObatMasuk obt = tableView.getSelectionModel().getSelectedItem().getValue();
            obt.setKode_obat(obatCombo.getValue().getKode_obat());
            obt.setId_supplier(suppCombo.getValue().getId_supplier());
            obt.setJumlah(Integer.parseInt(jumlahField.getText()));
            Date tgl = java.util.Date.from(expDatePicker.getValue().atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
            obt.setTgl_expired(tgl);
            alert.setHeaderText(null);
            alert.setContentText("Apakah anda yakin untuk mengedit data obat masuk?");
            Optional result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (obt.updateObatMasuk()) {
                    Alert keluar = new Alert(Alert.AlertType.INFORMATION);
                    keluar.setTitle("Data Obat Masuk");
                    keluar.setHeaderText(null);
                    keluar.setContentText("data berhasil di ubah");
                    resetButton2();
                    keluar.show();
                    setTableRoot();
                }
                resetForm();
            } else {
                alert.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Data Obat Masuk");
            alert.setContentText("Data Tidak Lengkap");
            alert.show();
        }
    }
}
