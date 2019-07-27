package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.sql2o.Connection;

public class ObatMasuk extends RecursiveTreeObject<ObatMasuk> {
    
    private int id_masuk;
    private int kode_obat;
    private int jumlah;
    private int id_supplier;
    Date tgl_expired;
    Date tgl_masuk;
    
    public static List<ObatMasuk> listFromDB() {
        try (Connection connection = DB.DB.sql2o.open()) {
            final String query = "SELECT * FROM obatmasuk";
            return connection.createQuery(query).executeAndFetch(ObatMasuk.class);
        }
    }

    public static List<ObatMasuk> getObatMasuk(Obat obat) {
        return listFromDB()
                .stream()
                .filter(obtMasuk -> obtMasuk.getKode_obat() == obat.getKode_obat())
                .collect(Collectors.toList());
    }

    public boolean createObatMasuk() {
        try (Connection connection = DB.DB.sql2o.open()) {
            final String query = "INSERT INTO obatmasuk "
                    + "(kode_obat,jumlah, id_supplier, tgl_expired, tgl_masuk) VALUE "
                    + "(:kode_obat,:jumlah, :id_supplier, :tgl_expired, :tgl_masuk)";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult() > 0;
        }
    }
    
    public boolean deleteObatMasuk() {
        try (Connection connection = DB.DB.sql2o.open()) {
            final String query = "DELETE FROM obatmasuk where id_masuk=:id_masuk";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult() > 0;
        }
    }
    
    public boolean updateObatMasuk() {
        try (Connection connection = DB.DB.sql2o.open()) {
            final String query = "UPDATE obatmasuk SET "
                    + "kode_obat = :kode_obat, jumlah =:jumlah, "
                    + "id_supplier =:id_supplier, tgl_expired=:tgl_expired, tgl_masuk=:tgl_masuk "
                    + "WHERE id_masuk = :id_masuk";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult() > 0;
        }
    }
    
    public ObatMasuk(int kode_obat, int jumlah, int id_supplier, Date tgl_expired, Date tgl_masuk) {
        this.kode_obat = kode_obat;
        this.jumlah = jumlah;
        this.id_supplier = id_supplier;
        this.tgl_expired = tgl_expired;
        this.tgl_masuk = tgl_masuk;
    }
    
    public int getId_masuk() {
        return id_masuk;
    }

    public void setId_masuk(int id_masuk) {
        this.id_masuk = id_masuk;
    }

    public int getKode_obat() {
        return kode_obat;
    }

    public void setKode_obat(int kode_obat) {
        this.kode_obat = kode_obat;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(int id_supplier) {
        this.id_supplier = id_supplier;
    }

    public Date getTgl_expired() {
        return tgl_expired;
    }

    public void setTgl_expired(Date tgl_expired) {
        this.tgl_expired = tgl_expired;
    }

    public Date getTgl_masuk() {
        return tgl_masuk;
    }

    public void setTgl_masuk(Date tgl_masuk) {
        this.tgl_masuk = tgl_masuk;
    }

    public ObjectProperty<Integer> id_masukProperty() {
        return new SimpleObjectProperty(id_masuk);
    }

    public StringProperty nama_obatProperty() {
        return new SimpleStringProperty(Obat.getObat(this).getNama_obat());
    }

    public ObjectProperty<Integer> jumlahProperty() {
        return new SimpleObjectProperty(jumlah);
    }

    public StringProperty nama_supplierProperty() {
        return new SimpleStringProperty(Supplier.getSupplier(this).getNama_supplier());
    }
    
    public ObjectProperty<Date> tgl_expiredProperty() {
        return new SimpleObjectProperty(tgl_expired);
    }
     
     public ObjectProperty<Date> tgl_masukProperty() {
        return new SimpleObjectProperty(tgl_masuk);
    }
     
    public int toint() {
        return kode_obat;
    }
}
