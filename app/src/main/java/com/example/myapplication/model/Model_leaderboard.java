package com.example.myapplication.model;

public class Model_leaderboard {
    private String nama, poin, poinB, desc, UID;

    public Model_leaderboard() {
    }

    public Model_leaderboard(String nama, String poin, String poinB, String desc, String UID) {
        this.nama = nama;
        this.poin = poin;
        this.poinB=poinB;
        this.desc = desc;
        this.UID = UID;
    }

    public String getPoinB() {
        return poinB;
    }

    public String getNama() {
        return nama;
    }

    public String getPoin() {
        return poin;
    }

    public String getDesc() {
        return desc;
    }

    public String getUID() {
        return UID;
    }
}
