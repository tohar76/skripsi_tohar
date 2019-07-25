/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.Date;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.sql2o.Connection;

/**
 *
 * @author asus
 */
public class Obat extends RecursiveTreeObject<Obat> {

    private int kode_obat;
    private String nama_obat;
    private String jenis_obat;
    private int stok;
    private int satuan;
    private Date tanggal;

    public static List<Obat> listFromDB() {
        try (Connection connection = DB.DB.sql2o.open()) {
            final String query = "SELECT * FROM dataobat";
            return connection.createQuery(query).executeAndFetch(Obat.class);
        }
    }

        public boolean createObat() {
        try (Connection connection = DB.DB.sql2o.open()) {
            final String query = "INSERT INTO dataobat (nama_obat,jenis_obat,stok,satuan, tanggal) VALUE (:nama_obat, :jenis_obat,:stok,:satuan, :tanggal)";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult() > 0;
        }
    }

    public boolean deleteObat() {
        try (Connection connection = DB.DB.sql2o.open()) {
            final String query = "DELETE FROM dataobat where kode_obat=:kode_obat";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult() > 0;
        }
    }

    public boolean updateObat() {
        try (Connection connection = DB.DB.sql2o.open()) {
            final String query = "UPDATE dataobat SET "
                    + "`nama_obat` = :nama_obat, `jenis_obat` = :jenis_obat, "
                    + "`stok` = :stok, `satuan` = :satuan, `tanggal`= :tanggal "
                    + "WHERE `kode_obat` = :kode_obat";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult()>0;
        }
    }
    
    public Obat(String nama_obat, String jenis_obat, int stok, int satuan, Date tanggal) {
        this.nama_obat = nama_obat;
        this.jenis_obat = jenis_obat;
        this.stok = stok;
        this.satuan = satuan;
        this.tanggal = tanggal;
    }

    public int getKode_obat() {
        return kode_obat;
    }

    public void setKode_obat(int kode_obat) {
        this.kode_obat = kode_obat;
    }

    public String getNama_obat() {
        return nama_obat;
    }

    public void setNama_obat(String nama_obat) {
        this.nama_obat = nama_obat;
    }

    public String getJenis_obat() {
        return jenis_obat;
    }

    public void setJenis_obat(String jenis_obat) {
        this.jenis_obat = jenis_obat;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public int getSatuan() {
        return satuan;
    }

    public void setSatuan(int satuan) {
        this.satuan = satuan;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public ObjectProperty<Integer> kodeProperty() {
        return new SimpleObjectProperty(kode_obat);
    }

    public StringProperty nama_obatProperty() {
        return new SimpleStringProperty(nama_obat);
    }

    public StringProperty jenis_obatProperty() {
        return new SimpleStringProperty(jenis_obat);
    }

    public ObjectProperty<Integer> stokProperty() {
        return new SimpleObjectProperty(stok);
    }

    public ObjectProperty<Integer> satuanProperty() {
        return new SimpleObjectProperty(satuan);
    }

    public StringProperty tanggalProperty() {
        return new SimpleStringProperty(tanggal.toString());
    }
}
