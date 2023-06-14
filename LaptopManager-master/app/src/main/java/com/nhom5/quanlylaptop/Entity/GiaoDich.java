package com.nhom5.quanlylaptop.Entity;

import android.os.Binder;

public class GiaoDich extends Binder {
    private String maGD;
    private String maVi;
    private String title;
    private String chiTiet;
    private String soTien;
    private String ngayGD;

    public GiaoDich(String maGD, String maVi, String title, String chiTiet, String soTien, String ngayGD) {
        this.maGD = maGD;
        this.maVi = maVi;
        this.title = title;
        this.chiTiet = chiTiet;
        this.soTien = soTien;
        this.ngayGD = ngayGD;
    }

    public String getMaGD() {
        return maGD;
    }

    public void setMaGD(String maGD) {
        this.maGD = maGD;
    }

    public String getMaVi() {
        return maVi;
    }

    public void setMaVi(String maVi) {
        this.maVi = maVi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(String chiTiet) {
        this.chiTiet = chiTiet;
    }

    public String getSoTien() {
        return soTien;
    }

    public void setSoTien(String soTien) {
        this.soTien = soTien;
    }

    public String getNgayGD() {
        return ngayGD;
    }

    public void setNgayGD(String ngayGD) {
        this.ngayGD = ngayGD;
    }

    @Override
    public String toString() {
        return "GiaoDich{" +
                "maGD = '" + maGD + '\'' +
                ", maVi = '" + maVi + '\'' +
                ", title = '" + title + '\'' +
                ", chiTiet = '" + chiTiet + '\'' +
                ", soTien = '" + soTien + '\'' +
                ", ngayGD = '" + ngayGD + '\'' +
                '}';
    }
}
