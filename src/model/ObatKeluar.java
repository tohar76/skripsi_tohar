package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.Date;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.sql2o.Connection;

public class ObatKeluar extends RecursiveTreeObject<ObatKeluar>{
    
    private int id_keluar;
    private int kode_obat;
    private int harga;
    private int jumlah;
    Date tgl_keluar;
    
    public static List<ObatKeluar> listFromDB() {
        try (Connection connection = DB.DB.sql2o.open()) {
            final String query = "SELECT * FROM obatkeluar";
            return connection.createQuery(query).executeAndFetch(ObatKeluar.class);
        }
    }
    
    public boolean createObatKeluar() {
        try (Connection connection = DB.DB.sql2o.open()) {
            final String query = "INSERT INTO obatkeluar "
                    + "(id_keluar,kode_obat,harga,jumlah, tgl_keluar) VALUE "
                    + "(:id_keluar, :kode_obat,:harga,:jumlah, :tgl_keluar)";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult() > 0;
        }
    }
    
    public boolean deleteObatKeluar() {
        try (Connection connection = DB.DB.sql2o.open()) {
            final String query = "DELETE FROM obatkeluar where id_keluar=:id_keluar";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult() > 0;
        }
    }
    
    public boolean updateObatKeluar() {
        try (Connection connection = DB.DB.sql2o.open()) {
            final String query = "UPDATE obatkeluar SET "
                    + "kode_obat = :kode_obat, harga =:harga, jumlah =:jumlah, "
                    + "tgl_masuk=:tgl_masuk "
                    + "WHERE id_keluar = :id_keluar";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult() > 0;
        }
    }
    
    public ObatKeluar(int kode_obat, int harga, int jumlah, Date tgl_keluar) {
        this.kode_obat = kode_obat;
        this.harga = harga;
        this.jumlah = jumlah;
        this.tgl_keluar = tgl_keluar;
    }
    
    public int getId_keluar() {
        return id_keluar;
    }

    public void setId_keluar(int id_keluar) {
        this.id_keluar = id_keluar;
    }

    public int getKode_obat() {
        return kode_obat;
    }

    public void setKode_obat(int kode_obat) {
        this.kode_obat = kode_obat;
    }
    
    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public Date getTgl_keluar() {
        return tgl_keluar;
    }

    public void setTgl_keluar(Date tgl_keluar) {
        this.tgl_keluar = tgl_keluar;
    }

    public ObjectProperty<Integer> id_keluarProperty() {
        return new SimpleObjectProperty(id_keluar);
    }

    public ObjectProperty<Integer> kode_obatProperty() {
        return new SimpleObjectProperty(kode_obat);
    }
    
    public ObjectProperty<Integer> hargaProperty() {
        return new SimpleObjectProperty(harga);
    }

    public ObjectProperty<Integer> jumlahProperty() {
        return new SimpleObjectProperty(jumlah);
    }
 
     public ObjectProperty<Date> tgl_keluarProperty() {
        return new SimpleObjectProperty(tgl_keluar);
    }
     
    public int toint() {
        return kode_obat;
    }
}
