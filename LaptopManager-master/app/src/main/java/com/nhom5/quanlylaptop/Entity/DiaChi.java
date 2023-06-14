package com.nhom5.quanlylaptop.Entity;

import android.os.Binder;

public class DiaChi extends Binder {
    private String maDC;
    private String maKH;
    private String tenNguoiNhan;
    private String SDT;
    private String diaChi;
    private String thanhPho;
    private String quanHuyen;
    private String xaPhuong;

    public DiaChi(String maDC, String maKH, String tenNguoiNhan, String SDT, String diaChi, String thanhPho, String quanHuyen, String xaPhuong) {
        this.maDC = maDC;
        this.maKH = maKH;
        this.tenNguoiNhan = tenNguoiNhan;
        this.SDT = SDT;
        this.diaChi = diaChi;
        this.thanhPho = thanhPho;
        this.quanHuyen = quanHuyen;
        this.xaPhuong = xaPhuong;
    }

    public String getMaDC() {
        return maDC;
    }

    public void setMaDC(String maDC) {
        this.maDC = maDC;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getThanhPho() {
        return thanhPho;
    }

    public void setThanhPho(String thanhPho) {
        this.thanhPho = thanhPho;
    }

    public String getQuanHuyen() {
        return quanHuyen;
    }

    public void setQuanHuyen(String quanHuyen) {
        this.quanHuyen = quanHuyen;
    }

    public String getXaPhuong() {
        return xaPhuong;
    }

    public void setXaPhuong(String xaPhuong) {
        this.xaPhuong = xaPhuong;
    }

    @Override
    public String toString() {
        return "DiaChi{" +
                "maDC = '" + maDC + '\'' +
                ", maKH = '" + maKH + '\'' +
                ", tenNguoiNhan = '" + tenNguoiNhan + '\'' +
                ", SDT = '" + SDT + '\'' +
                ", diaChi = '" + diaChi + '\'' +
                ", thanhPho = '" + thanhPho + '\'' +
                ", quanHuyen = '" + quanHuyen + '\'' +
                ", xaPhuong = '" + xaPhuong + '\'' +
                '}';
    }
}
