package com.nhom5.quanlylaptop.Entity;

import android.os.Binder;

import java.util.Arrays;

public class KhachHang extends Binder {
    private String maKH;
    private String hoKH;
    private String tenKH;
    private String gioiTinh;
    private String email;
    private String matKhau;
    private String queQuan;
    private String phone;
    private String haveVi;
    private byte[] avatar;

    public KhachHang(String maKH, String hoKH, String tenKH, String gioiTinh, String email, String matKhau, String queQuan, String phone, String haveVi, byte[] avatar) {
        this.maKH = maKH;
        this.hoKH = hoKH;
        this.tenKH = tenKH;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.matKhau = matKhau;
        this.queQuan = queQuan;
        this.phone = phone;
        this.haveVi = haveVi;
        this.avatar = avatar;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoKH() {
        return hoKH;
    }

    public void setHoKH(String hoKH) {
        this.hoKH = hoKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHaveVi() {
        return haveVi;
    }

    public void setHaveVi(String haveVi) {
        this.haveVi = haveVi;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "maKH = '" + maKH + '\'' +
                ", hoKH = '" + hoKH + '\'' +
                ", tenKH = '" + tenKH + '\'' +
                ", gioiTinh = '" + gioiTinh + '\'' +
                ", email = '" + email + '\'' +
                ", matKhau = '" + matKhau + '\'' +
                ", queQuan = '" + queQuan + '\'' +
                ", phone = '" + phone + '\'' +
                ", haveVi = '" + haveVi + '\'' +
                ", avatar = " +  avatar +
                '}';
    }
}
