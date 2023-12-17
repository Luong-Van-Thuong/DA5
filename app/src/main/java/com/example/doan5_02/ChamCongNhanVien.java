package com.example.doan5_02;

public class ChamCongNhanVien {
    private int id;
    private String name;
    private String thoiGian;
    private String ngayThangNam;

    public ChamCongNhanVien(int id, String name, String thoiGian, String ngayThangNam) {
        this.id = id;
        this.name = name;
        this.thoiGian = thoiGian;
        this.ngayThangNam = ngayThangNam;
    }

    public ChamCongNhanVien() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getNgayThangNam() {
        return ngayThangNam;
    }

    public void setNgayThangNam(String ngayThangNam) {
        this.ngayThangNam = ngayThangNam;
    }
}
