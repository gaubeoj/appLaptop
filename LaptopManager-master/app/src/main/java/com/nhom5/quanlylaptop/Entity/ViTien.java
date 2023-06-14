package com.nhom5.quanlylaptop.Entity;

import android.os.Binder;

public class ViTien extends Binder {
    private String maVi;
    private String maKH;
    private String soTien;
    private String nganHang;

    public ViTien(String maVi, String maKH, String soTien, String nganHang) {
        this.maVi = maVi;
        this.maKH = maKH;
        this.soTien = soTien;
        this.nganHang = nganHang;
    }

    public String getMaVi() {
        return maVi;
    }

    public void setMaVi(String maVi) {
        this.maVi = maVi;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getSoTien() {
        return soTien;
    }

    public void setSoTien(String soTien) {
        this.soTien = soTien;
    }

    public String getNganHang() {
        return nganHang;
    }

    public void setNganHang(String nganHang) {
        this.nganHang = nganHang;
    }

    @Override
    public String toString() {
        return "ViTien{" +
                "maVi = '" + maVi + '\'' +
                ", maKH = '" + maKH + '\'' +
                ", soTien = '" + soTien + '\'' +
                ", nganHang = '" + nganHang + '\'' +
                '}';
    }
}
