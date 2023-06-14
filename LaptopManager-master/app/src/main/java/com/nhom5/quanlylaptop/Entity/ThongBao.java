package com.nhom5.quanlylaptop.Entity;

import android.os.Binder;

public class ThongBao extends Binder {
    private String maTB;
    private String id;
    private String title;
    private String chiTiet;
    private String ngayTB;

    public ThongBao(String maTB, String id, String title, String chiTiet, String ngayTB) {
        this.maTB = maTB;
        this.id = id;
        this.title = title;
        this.chiTiet = chiTiet;
        this.ngayTB = ngayTB;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNgayTB() {
        return ngayTB;
    }

    public void setNgayTB(String ngayTB) {
        this.ngayTB = ngayTB;
    }

    @Override
    public String toString() {
        return "ThongBao{" +
                "maTB = '" + maTB + '\'' +
                ", id = '" + id + '\'' +
                ", title = '" + title + '\'' +
                ", chiTiet = '" + chiTiet + '\'' +
                ", ngayTB = '" + ngayTB + '\'' +
                '}';
    }
}
