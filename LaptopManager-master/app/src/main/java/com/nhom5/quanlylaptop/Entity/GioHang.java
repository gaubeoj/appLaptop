package com.nhom5.quanlylaptop.Entity;

import android.os.Binder;

public class GioHang extends Binder {
    private String maGio;
    private String maLaptop;
    private String maKH;
    private String ngayThem;
    private String maVou;
    private int soLuong;

    public GioHang(String maGio) {
        this.maGio = maGio;
    }

    public GioHang(String maGio, String maLaptop, String maKH, String ngayThem, String maVou, int soLuong) {
        this.maGio = maGio;
        this.maLaptop = maLaptop;
        this.maKH = maKH;
        this.ngayThem = ngayThem;
        this.soLuong = soLuong;
        this.maVou = maVou;
    }

    public String getMaGio() {
        return maGio;
    }

    public void setMaGio(String maGio) {
        this.maGio = maGio;
    }

    public String getMaLaptop() {
        return maLaptop;
    }

    public void setMaLaptop(String maLaptop) {
        this.maLaptop = maLaptop;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMaVou() {
        return maVou;
    }

    public void setmaVou(String maVou) {
        this.maVou = maVou;
    }

    public String getNgayThem() {
        return ngayThem;
    }

    public void setNgayThem(String ngayThem) {
        this.ngayThem = ngayThem;
    }

    @Override
    public String toString() {
        return "GioHang{" +
                "maGio = '" + maGio + '\'' +
                ", maLaptop = '" + maLaptop + '\'' +
                ", maKH = '" + maKH + '\'' +
                ", ngayThem = '" + ngayThem + '\'' +
                ", maVou = '" + maVou + '\'' +
                ", soLuong = '" + soLuong + '\'' +
                '}';
    }
}
