package com.example.doan5_02;

public class SqlDataNguoiDung {

    private int idGetToVs;
    private String nameGetToVS;

    public SqlDataNguoiDung() {
    }

    public SqlDataNguoiDung(int idGetToVs, String nameGetToVS) {
        this.idGetToVs = idGetToVs;
        this.nameGetToVS = nameGetToVS;
    }

    public String getNameGetToVS() {
        return nameGetToVS;
    }

    public void setNameGetToVS(String nameGetToVS) {
        this.nameGetToVS = nameGetToVS;
    }

    public int getIdGetToVs() {
        return idGetToVs;
    }

    public void setIdGetToVs(int idGetToVs) {
        this.idGetToVs = idGetToVs;
    }
}
