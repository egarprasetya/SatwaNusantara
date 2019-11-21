package com.example.myapplication.model;

public class ModelDataSapi {
    private String  beratAwal,  jenisSapi, jenisKelamin, namaPeternak,  nomorSapi, dataDiambil;

    public ModelDataSapi() {
    }

    public ModelDataSapi(String beratAwal, String jenisSapi, String jenisKelamin, String namaPeternak, String nomorSapi) {
        this.beratAwal=beratAwal;
        this.jenisSapi = jenisSapi;
        this.namaPeternak = namaPeternak;
        this.nomorSapi = nomorSapi;
        this.jenisKelamin=jenisKelamin;

    }
//    public v_daftarSapiBetina(String beratAwal, String jenisSapi, String jenisKelamin, String namaPeternak, String nomorSapi,String dataDiambil) {
//        this.beratAwal=beratAwal;
//        this.jenisSapi = jenisSapi;
//        this.namaPeternak = namaPeternak;
//        this.nomorSapi = nomorSapi;
//        this.jenisKelamin=jenisKelamin;
//
//    }

    public String getBeratAwal() {
        return beratAwal;
    }

    public void setBeratAwal(String beratAwal) {
        this.beratAwal = beratAwal;
    }

    public String getJenisSapi() {
        return jenisSapi;
    }

    public void setJenisSapi(String jenisSapi) {
        this.jenisSapi = jenisSapi;
    }
    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNamaPeternak() {
        return namaPeternak;
    }

    public void setNamaPeternak(String namaPeternak) {
        this.namaPeternak = namaPeternak;
    }

    public String getNomorSapi() {
        return nomorSapi;
    }

    public void setNomorSapi(String nomorSapi) {
        this.nomorSapi = nomorSapi;
    }


}
