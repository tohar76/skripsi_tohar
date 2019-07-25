/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.sql2o.Connection;

/**
 *
 * @author Tohar
 */
public class Supplier extends RecursiveTreeObject<Supplier>{
    int id_supplier;
    String nama_supplier;
    String alamat;
    String telf;
    
     public static List<Supplier> listFromDB() {
        try(Connection connection = DB.DB.sql2o.open()) {
            final String query = "SELECT * FROM datasupplier";
            return connection.createQuery(query).executeAndFetch(Supplier.class);
        }
    }
    
    public boolean createSupplier(){
        try (Connection connection = DB.DB.sql2o.open()) {
            final String query = "INSERT INTO datasupplier (nama_supplier,alamat,telf) VALUE (:nama_supplier, :alamat, :telf)";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult()>0;
        }
    }
    
    public boolean updateSupplier(){
        try (Connection connection = DB.DB.sql2o.open()) {
            final String query = "UPDATE datasupplier SET `nama_supplier` = :nama_supplier, `alamat` = :alamat, `telf` = :telf WHERE `id_supplier` = :id_supplier";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult()>0;
        }
    }
    
    public boolean deleteSupplier(){
        try (Connection connection = DB.DB.sql2o.open()) {
            final String query = "DELETE FROM datasupplier where id_supplier=:id_supplier";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult()>0;
        }
    }
    
    public Supplier(String nama_supplier, String alamat, String telf) {
        this.nama_supplier = nama_supplier;
        this.alamat = alamat;
        this.telf = telf;
    }

    public int getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(int id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getNama_supplier() {
        return nama_supplier;
    }

    public void setNama_supplier(String nama_supplier) {
        this.nama_supplier = nama_supplier;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelf() {
        return telf;
    }

    public void setTelf(String telf) {
        this.telf = telf;
    }
    
   
    
    public ObjectProperty<Integer> id_supplierProperty() {
        return new SimpleObjectProperty(id_supplier);
    }
    
    public StringProperty nama_supplierProperty() {
        return new SimpleStringProperty(nama_supplier);
    }
    
    public StringProperty alamatProperty() {
        return new SimpleStringProperty(alamat);
    }
    
    public StringProperty telfProperty() {
        return new SimpleStringProperty(telf);
    }
}
