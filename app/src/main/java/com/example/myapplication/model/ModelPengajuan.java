package com.example.myapplication.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ModelPengajuan {

    public static final String VERIFIKASI_MENUNGGU = "Menunggu";
    public static final String VERIFIKASI_DITERIMA = "Diterima";
    public static final String VERIFIKASI_DITOLAK = "Ditolak";
    private String key;
    private String idPengajuan;
    private String namaPeternak;
    private String username;
    private String alamat;
    private String noKtp;
    private String jumlahSapi;
    private String urlFoto;
    private String noHp;
    private String tanggalPengajuan;


    public ModelPengajuan() {

    }

    public ModelPengajuan(String idPengajuan, String namaPeternak, String username, String alamat, String noKtp, String jumlahSapi, String urlFoto, String noHp, String tanggalPengajuan) {
        this.idPengajuan = idPengajuan;
        this.namaPeternak = namaPeternak;
        this.username = username;
        this.alamat = alamat;
        this.noKtp = noKtp;
        this.jumlahSapi = jumlahSapi;
        this.urlFoto = urlFoto;
        this.noHp = noHp;
        this.tanggalPengajuan = tanggalPengajuan;
    }

    public String getIdPengajuan() {
        return idPengajuan;
    }

    public void setIdPengajuan(String idPengajuan) {
        this.idPengajuan = idPengajuan;
    }

    public String getNamaPeternak() {
        return namaPeternak;
    }

    public void setNamaPeternak(String namaPeternak) {
        this.namaPeternak = namaPeternak;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    public String getJumlahSapi() {
        return jumlahSapi;
    }

    public void setJumlahSapi(String jumlahSapi) {
        this.jumlahSapi = jumlahSapi;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getTanggalPengajuan() {
        return tanggalPengajuan;
    }

    public void setTanggalPengajuan(String tanggalPengajuan) {
        this.tanggalPengajuan = tanggalPengajuan;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
