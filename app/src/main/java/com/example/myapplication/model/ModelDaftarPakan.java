package com.example.myapplication.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ModelDaftarPakan {



    private String idStok;
    private String konsentrat;
    private String jagung;
    private String jerami;



    public ModelDaftarPakan() {

    }

    public ModelDaftarPakan(String idStok, String konsentrat, String jagung, String jerami) {
        this.idStok = idStok;
        this.konsentrat = konsentrat;
        this.jagung = jagung;
        this.jerami = jerami;

    }

    public String getIdStok() {
        return idStok;
    }

    public void setIdStok(String idStok) {
        this.idStok = idStok;
    }

    public String getKonsentrat() {
        return konsentrat;
    }

    public void setKonsentrat(String konsentrat) {
        this.konsentrat = konsentrat;
    }

    public String getJagung() {
        return jagung;
    }

    public void setJagung(String jagung) {
        this.jagung = jagung;
    }

    public String getJerami() {
        return jerami;
    }

    public void setJerami(String jerami) {
        this.jerami = jerami;
    }


}
