package com.nhom5.quanlylaptop.Entity;

import android.os.Binder;

import java.util.Arrays;

public class NhanVien extends Binder {
    private String maNV;
    private String hoNV;
    private String tenNV;
    private String gioiTinh;
    private String email;
    private String matKhau;
    private String queQuan;
    private String phone;
    private String roleNV;
    private int doanhSo;
    private int soSP;
    private byte[] avatar;

    public NhanVien(String maNV, String hoNV, String tenNV, String gioiTinh, String email, String matKhau, String queQuan, String phone, String roleNV, int doanhSo, int soSP, byte[] avatar) {
        this.maNV = maNV;
        this.hoNV = hoNV;
        this.tenNV = tenNV;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.matKhau = matKhau;
        this.queQuan = queQuan;
        this.doanhSo = doanhSo;
        this.phone = phone;
        this.soSP = soSP;
        this.avatar = avatar;
        this.roleNV = roleNV;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoNV() {
        return hoNV;
    }

    public void setHoNV(String hoNV) {
        this.hoNV = hoNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
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

    public int getDoanhSo() {
        return doanhSo;
    }

    public void setDoanhSo(int doanhSo) {
        this.doanhSo = doanhSo;
    }

    public int getSoSP() {
        return soSP;
    }

    public void setSoSP(int soSP) {
        this.soSP = soSP;
    }

    public String getRoleNV() {
        return roleNV;
    }

    public void setRoleNV(String roleNV) {
        this.roleNV = roleNV;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNV = '" + maNV + '\'' +
                ", hoNV = '" + hoNV + '\'' +
                ", tenNV = '" + tenNV + '\'' +
                ", gioiTinh = '" + gioiTinh + '\'' +
                ", email = '" + email + '\'' +
                ", matKhau = '" + matKhau + '\'' +
                ", queQuan = '" + queQuan + '\'' +
                ", phone = '" + phone + '\'' +
                ", roleNV = '" + roleNV + '\'' +
                ", avatar = " + avatar +
                '}';
    }
}
