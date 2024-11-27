package models;

import java.util.List;

public class Recommendation {
    private int id;
    private String namaLokasi;
    private String deskripsi;
    private String gambar;
    private List<Comment> comments;

    public Recommendation(int id, String namaLokasi, String deskripsi, String gambar, List<Comment> comments) {
        this.id = id;
        this.namaLokasi = namaLokasi;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
        this.comments = comments;
    }

    // Getters
    public String getNamaLokasi() {
        return namaLokasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getGambar() {
        return gambar;
    }
}