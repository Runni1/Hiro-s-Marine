/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Asus
 */
public class recommendation {
    public class Lokasi {
    private String nama;
    private String deskripsi;
    private String gambar; // URL atau path ke gambar
    private double rating;

    public Lokasi(String nama, String deskripsi, String gambar) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
        this.rating = 0.0;
    }

    // Getter dan Setter
    public String getNama() {
        return nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
    public class Komentar {
    private String user;
    private String isi;
    private String gambar; // Optional, untuk gambar yang di-upload
    private Lokasi lokasi;

    public Komentar(String user, String isi, String gambar, Lokasi lokasi) {
        this.user = user;
        this.isi = isi;
        this.gambar = gambar;
        this.lokasi = lokasi;
    }

    // Getter dan Setter
    public String getUser () {
        return user;
    }

    public String getIsi() {
        return isi;
    }

    public String getGambar() {
        return gambar;
    }

    public Lokasi getLokasi() {
        return lokasi;
    }
}
    
}
