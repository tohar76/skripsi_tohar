/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
/**
 *
 * @author Tohar
 */
public class laporan {
    private static final String biasa = "/gambar/times.ttf";
    private static final String bold = "/gambar/timesbold.ttf";
    
    private static Cell cellNoBorder(Image image) {
        return new Cell()
                .setBorder(Border.NO_BORDER)
                .add(image);
    }
    
    private static List<String> hari() {
        return Arrays.asList(
                "",
                "Senin",
                "Selasa",
                "Rabu",
                "Kamis",
                "Jumat",
                "Sabtu",
                "Minggu"
        );
    }

    private static List<String> bulan() {
        return Arrays.asList(
                "",
                "Januari",
                "Februari",
                "Maret",
                "April",
                "Mei",
                "Juni",
                "Juli",
                "Agustus",
                "September",
                "Oktober",
                "November",
                "Desember"
        );
    }
    
    private static Table kop_surat(String judul) throws MalformedURLException, IOException {
        BufferedImage img = ImageIO.read(
                laporan.class.getResourceAsStream("/gambar/564d8c274433987b55926961c0aa2de5.jpg"));
        byte[] imageInByte;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(img, "png", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
        }
        Image image = new Image(ImageDataFactory.create(imageInByte));

        return new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 10),
                new UnitValue(UnitValue.PERCENT, 90)}, true)
                .setFontSize(12)
                .addCell(cellNoBorder(image.setAutoScale(true)))
                .addCell(
                        cellNoBorder("BIDAN YANTI SOETARNO\n Jalan Raya Kalisari No. 63 Kalisari Pasar Rebo Jakarta Timur \n Telepon : 081293950637\n \n \n" +judul+ "\n \n")
                                .setTextAlignment(TextAlignment.CENTER)
                                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                                .setVerticalAlignment(VerticalAlignment.MIDDLE));               
    }

    private static Table signature(LocalDate tgl) {
        return new Table(
                1)
                .setFontSize(10)
                .setWidth(200)
                .setHeight(100)
                .setMarginTop(50)//antara table dan ttd
                .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                .addCell(
                        cellNoBorder("Jakarta" + ", " +
                                hari().get(tgl.getDayOfWeek()) + ", " +
                                tgl.getDayOfMonth() + " " +
                                bulan().get(tgl.getMonthOfYear()) + " " +
                                tgl.getYear())
                                .setTextAlignment(TextAlignment.CENTER))
                .addCell(
                        cellNoBorder("Asisten Bidan\n Bunga Amelia")
                                .setTextAlignment(TextAlignment.CENTER)
                                .setVerticalAlignment(VerticalAlignment.BOTTOM));
    }

    public static void daftar_obat() throws IOException {
        LocalDate localDate = new LocalDate(new Date());
        String fileName = String.format("laporan-data-obat-%s.pdf", localDate.toString());

        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf)) {
            document.add(kop_surat("Laporan Data Obat"));
            
            Table detailTable = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 50),
                new UnitValue(UnitValue.PERCENT, 50)}, true)
                ;
   
            Table obatTable = new Table(8);
            obatTable.setWidth(520);
            obatTable.addHeaderCell(cell("Kode Obat"));
            obatTable.addHeaderCell(cell("Nama Obat"));
            obatTable.addHeaderCell(cell("Jenis Obat"));
            obatTable.addHeaderCell(cell("Stok"));
            obatTable.addHeaderCell(cell("Harga Beli"));
            obatTable.addHeaderCell(cell("Harga Jual"));
            
            Obat.listFromDB().forEach(Obat -> {
                obatTable.addCell(cell(String.valueOf(Obat.getKode_obat())));
                obatTable.addCell(cell(Obat.getNama_obat()));
                obatTable.addCell(cell(Obat.getJenis_obat()));
                obatTable.addCell(cell(String.valueOf(Obat.getSatuan())));
                obatTable.addCell(cell(String.valueOf(Obat.getHarga_beli())));
                obatTable.addCell(cell(String.valueOf(Obat.getHarga_jual())));
                });
            
            document.add(obatTable.setMarginTop(10));
            document.add(signature(localDate));
            document.close();
            showReport(fileName);
        }
        
    }


    public static void daftar_masuk() throws IOException {
        LocalDate localDate = new LocalDate(new Date());
        String fileName = String.format("laporan-data-obat-masuk-%s.pdf", localDate.toString());

        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf)) {
            document.add(kop_surat("Laporan Data Obat Masuk"));
            
            Table detailTable = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 50),
                new UnitValue(UnitValue.PERCENT, 50)}, true)
                ;
            
            Table masukTable = new Table(6);
            masukTable.setWidth(520);
            masukTable.addHeaderCell(cell("Id Masuk"));
            masukTable.addHeaderCell(cell("Kode Obat"));
            masukTable.addHeaderCell(cell("Jumlah"));
            masukTable.addHeaderCell(cell("Id Supplier"));
            masukTable.addHeaderCell(cell("Tanggal Expired"));
            masukTable.addHeaderCell(cell("Tanggal Masuk"));
            
            ObatMasuk.listFromDB().forEach(masuk -> {
                masukTable.addCell(cell(String.valueOf(masuk.getId_masuk())));
                masukTable.addCell(cell(String.valueOf(Obat.getObat(masuk).getNama_obat())));
                masukTable.addCell(cell(String.valueOf(masuk.getJumlah())));
                masukTable.addCell(cell(String.valueOf(masuk.getId_supplier())));
                masukTable.addCell(cell(masuk.getTgl_expired().toString()));
                masukTable.addCell(cell(masuk.getTgl_masuk().toString()));
                });
            
            document.add(masukTable.setMarginTop(10));
            document.add(signature(localDate));
            document.close();
            showReport(fileName);
        }
        
    }
    
    public static void daftar_keluar() throws IOException {
        LocalDate localDate = new LocalDate(new Date());
        String fileName = String.format("laporan-data-obat-keluar-%s.pdf", localDate.toString());

        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf)) {
            document.add(kop_surat("Laporan Data Obat Keluar"));
            
            Table detailTable = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 50),
                new UnitValue(UnitValue.PERCENT, 50)}, true);
            
            Table keluarTable = new Table(4);
            keluarTable.setWidth(520);
            keluarTable.addHeaderCell(cell("Id Keluar"));
            keluarTable.addHeaderCell(cell("Kode Obat"));
            keluarTable.addHeaderCell(cell("Jumlah"));
            keluarTable.addHeaderCell(cell("Tanggal Keluar"));
            
            ObatKeluar.listFromDB().forEach(keluar -> {
                keluarTable.addCell(cell(String.valueOf(keluar.getId_keluar())));
                keluarTable.addCell(cell(String.valueOf(Obat.getObat(keluar).getNama_obat())));
                keluarTable.addCell(cell(String.valueOf(keluar.getJumlah())));
                keluarTable.addCell(cell(keluar.getTgl_keluar().toString()));
                });
            
            document.add(keluarTable.setMarginTop(10));
            document.add(signature(localDate));
            document.close();
            showReport(fileName);
        }
        
    }
    
    public static void daftar_supplier() throws IOException {
        LocalDate localDate = new LocalDate(new Date());
        String fileName = String.format("laporan-data-supplier-%s.pdf", localDate.toString());

        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf)) {
            document.add(kop_surat("Laporan Data Supplier"));
            
            Table detailTable = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 50),
                new UnitValue(UnitValue.PERCENT, 50)}, true)
                ;
            
            Table supplierTable = new Table(6);
            supplierTable.setWidth(520);
            supplierTable.addHeaderCell(cell("Id Supplier"));
            supplierTable.addHeaderCell(cell("Nama Supplier"));
            supplierTable.addHeaderCell(cell("Alamat"));
            supplierTable.addHeaderCell(cell("Nomer telf"));
            
            Supplier.listFromDB().forEach(supplier -> {
                supplierTable.addCell(cell(String.valueOf(supplier.getId_supplier())));
                supplierTable.addCell(cell(supplier.getNama_supplier()));
                supplierTable.addCell(cell(supplier.getAlamat()));
                supplierTable.addCell(cell(supplier.getTelf()));
                });
            
            document.add(supplierTable.setMarginTop(10));
            document.add(signature(localDate));
            document.close();
            showReport(fileName);
        }
        
    }
        
    private static Cell cellNoBorder(String text) {
        return new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(text));
    }

    private static Cell cell(String text) {
        return new Cell().add(new Paragraph(text));
    }

    private static void showReport(String fileName) {
        File file = new File(fileName);
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(file);
        } catch (IOException e) {
        }
}
}    
