package models;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Asus
 */
public class Recommendation {
    public class Lokasi {
        private String namaLokasi;
        private String deskripsi;
        private String gambar;

        public Lokasi(String namaLokasi, String deskripsi, String gambar) {
            this.namaLokasi = namaLokasi;
            this.deskripsi = deskripsi;
            this.gambar = gambar;
        }

        public String getNamaLokasi() {
            return namaLokasi;
        }

        public void setNamaLokasi(String namaLokasi) {
            this.namaLokasi = namaLokasi;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public void setDeskripsi(String deskripsi) {
            this.deskripsi = deskripsi;
        }

        public String getGambar() {
            return gambar;
        }

        public void setGambar(String gambar) {
            this.gambar = gambar;
        }
    }


}