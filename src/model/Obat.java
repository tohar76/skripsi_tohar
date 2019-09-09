package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.sql2o.Connection;

public class Obat extends RecursiveTreeObject<Obat> {
    
    private int kode_obat;
    private String nama_obat;
    private String jenis_obat;
    private String satuan;
    private int harga_beli;
    private int harga_jual;

    //Ambil Semua Data Obat Dari Database
    public static List<Obat> listFromDB() {
        try (Connection connection = DB.DB.sql2o.open()) {
            final String query = "SELECT * FROM dataobat";
            return connection.createQuery(query).executeAndFetch(Obat.class);
        }
    }
    
    //Ambil Obat Berdasarkan Obat Masuk
    public static Obat getObat(ObatMasuk obtMasuk) {
        return listFromDB()
                .stream()
                .filter(obat -> obat.kode_obat == obtMasuk.getKode_obat())
                .findFirst()
                .orElse(null);
    }
    
    //Ambil Obat Berdasarkan Obat Keluar
    public static Obat getObat(ObatKeluar obtKeluar) {
        return listFromDB()
                .stream()
                .filter(obat -> obat.kode_obat == obtKeluar.getKode_obat())
                .findFirst()
                .orElse(null);
    }

    public Obat(String nama_obat, String jenis_obat, String satuan, int harga_beli, int harga_jual) {
       this.nama_obat = nama_obat;
        this.jenis_obat = jenis_obat;
        this.satuan = satuan;
        this.harga_beli = harga_beli;
        this.harga_jual = harga_jual;
    }

    
  
    public int getStock() {
        int countMasuk = ObatMasuk.getObatMasuk(this).stream().mapToInt(ObatMasuk::getJumlah).sum();
        int countKeluar = ObatKeluar.getObatKeluar(this).stream().mapToInt(ObatKeluar::getJumlah).sum();
        return countMasuk - countKeluar;
    }

    public boolean createObat() {
        try (Connection connection = DB.DB.sql2o.open()) {
            final String query = "INSERT INTO dataobat "
                    + "(nama_obat, jenis_obat, satuan, harga_beli, harga_jual) VALUE "
                    + "(:nama_obat, :jenis_obat, :satuan, :harga_beli, :harga_jual)";
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
                    + "nama_obat = :nama_obat, jenis_obat =:jenis_obat,  "
                    + "satuan =:satuan, harga_beli=:harga_beli, harga_jual=:harga_jual "
                    + "WHERE kode_obat = :kode_obat";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult() > 0;
        }
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
    

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getHarga_beli() {
        return harga_beli;
    }

    public void setHarga_beli(int harga_beli) {
        this.harga_beli = harga_beli;
    }

    public int getHarga_jual() {
        return harga_jual;
    }

    public void setHarga_jual(int harga_jual) {
        this.harga_jual = harga_jual;
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
    
    public ObjectProperty<Integer> stockProperty() {
        return new SimpleObjectProperty(getStock());
    }

    public StringProperty satuanProperty() {
        return new SimpleStringProperty(satuan);
    }
    
    public ObjectProperty<Integer> harga_beliProperty() {
        return new SimpleObjectProperty(harga_beli);
    }
     
     public ObjectProperty<Integer> harga_jualProperty() {
        return new SimpleObjectProperty(harga_jual);
    }

    @Override
    public String toString() {
        return nama_obat;
    }  
}
