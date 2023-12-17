package com.example.doan5_02;

public class ObjectDataNguoiDung {
    private int id;
    private String tenTaiKhoan;

    public ObjectDataNguoiDung(int id, String tenTaiKhoan) {
        this.id = id;
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public ObjectDataNguoiDung() {
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

    @Override
    public String toString() {
        return "ObjectDataNguoiDung{" +
                "id=" + id +
                ", tenTaiKhoan='" + tenTaiKhoan + '\'' +
                '}';
    }
}
