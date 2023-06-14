package com.nhom5.quanlylaptop.Entity;

import android.os.Binder;

public class DonHang extends Binder {
    private String maDH;
    private String maNV;
    private String maKH;
    private String maLaptop;
    private String maVoucher;
    private String maRate;
    private String diaChi;
    private String ngayMua;
    private String pthThanhToan;
    private String trangThai;
    private String isDanhGia;
    private String thanhTien;
    private int soLuong;

    public DonHang(String maDH, String maNV, String maKH, String maLaptop, String maVoucher, String maRate, String diaChi,
                   String ngayMua, String pthThanhToan, String trangThai, String isDanhGia, String thanhTien, int soLuong) {
        this.maDH = maDH;
        this.maNV = maNV;
        this.maKH = maKH;
        this.maLaptop = maLaptop;
        this.maVoucher = maVoucher;
        this.maRate = maRate;
        this.diaChi = diaChi;
        this.ngayMua = ngayMua;
        this.pthThanhToan = pthThanhToan;
        this.trangThai = trangThai;
        this.isDanhGia = isDanhGia;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaLaptop() {
        return maLaptop;
    }

    public void setMaLaptop(String maLaptop) {
        this.maLaptop = maLaptop;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public String getMaRate() {
        return maRate;
    }

    public void setMaRate(String maRate) {
        this.maRate = maRate;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public String getLoaiThanhToan() {
        return pthThanhToan;
    }

    public void setLoaiThanhToan(String pthThanhToan) {
        this.pthThanhToan = pthThanhToan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getIsDanhGia() {
        return isDanhGia;
    }

    public void setIsDanhGia(String isDanhGia) {
        this.isDanhGia = isDanhGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(String thanhTien) {
        this.thanhTien = thanhTien;
    }

    @Override
    public String toString() {
        return "DonHang{" +
                "maDH = '" + maDH + '\'' +
                ", maNV = '" + maNV + '\'' +
                ", maKH = '" + maKH + '\'' +
                ", maLaptop = '" + maLaptop + '\'' +
                ", maVoucher = '" + maVoucher + '\'' +
                ", maRate = '" + maRate + '\'' +
                ", diaChi = '" + diaChi + '\'' +
                ", ngayMua = '" + ngayMua + '\'' +
                ", pthThanhToan = '" + pthThanhToan + '\'' +
                ", trangThai = '" + trangThai + '\'' +
                ", isDanhGia = '" + isDanhGia + '\'' +
                ", thanhTien = '" + thanhTien + '\'' +
                ", soLuong = " + soLuong +
                '}';
    }
}
