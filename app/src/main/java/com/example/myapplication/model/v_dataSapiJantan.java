package com.example.myapplication.model;

public class v_dataSapiJantan {
    private String tanggal, waktu, status, gambar;


    public v_dataSapiJantan(String tanggal, String waktu, String status, String gambar) {

        this.tanggal = tanggal;
        this.waktu = waktu;
        this.status = status;
        this.gambar = gambar;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}