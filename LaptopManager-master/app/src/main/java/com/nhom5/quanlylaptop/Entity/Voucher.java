package com.nhom5.quanlylaptop.Entity;

import android.os.Binder;

public class Voucher extends Binder {
    private String maVoucher;
    private String tenVoucher;
    private String giamGia;
    private String ngayBD;
    private String ngayKT;

    public Voucher(String maVoucher, String tenVoucher, String giamGia, String ngayBD, String ngayKT) {
        this.maVoucher = maVoucher;
        this.tenVoucher = tenVoucher;
        this.giamGia = giamGia;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public String getTenVoucher() {
        return tenVoucher;
    }

    public void setTenVoucher(String tenVoucher) {
        this.tenVoucher = tenVoucher;
    }

    public String getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(String giamGia) {
        this.giamGia = giamGia;
    }

    public String getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(String ngayBD) {
        this.ngayBD = ngayBD;
    }

    public String getNgayKT() {
        return ngayKT;
    }

    public void setNgayKT(String ngayKT) {
        this.ngayKT = ngayKT;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "maVoucher = '" + maVoucher + '\'' +
                ", tenVoucher = '" + tenVoucher + '\'' +
                ", giamGia = '" + giamGia + '\'' +
                ", ngayBD = '" + ngayBD + '\'' +
                ", ngayKT = '" + ngayKT + '\'' +
                '}';
    }
}
