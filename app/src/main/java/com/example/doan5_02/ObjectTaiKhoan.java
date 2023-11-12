package com.example.doan5_02;

import androidx.annotation.NonNull;

public class ObjectTaiKhoan {
    private int id;
    private String tenTaiKhoan;
    private String matKhau;

    public ObjectTaiKhoan(int id, String tenTaiKhoan, String matKhau) {
        this.id = id;
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
    }

    public ObjectTaiKhoan() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    @NonNull
    @Override
    public String toString() {
        return "objectTaiKhoan{" + "id= " + id + '\''
                + ", Tai Khoan= " + tenTaiKhoan + '\''
                + ", Mat Khau= " + matKhau + '}';
    }
}
