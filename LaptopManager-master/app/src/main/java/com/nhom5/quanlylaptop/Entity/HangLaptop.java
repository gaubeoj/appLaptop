package com.nhom5.quanlylaptop.Entity;

import android.os.Binder;

import java.util.Arrays;

public class HangLaptop extends Binder {
    private String maHangLap;
    private String tenHangLap;
    private byte[] anhHang;

    public HangLaptop(String maHangLap, String tenHangLap, byte[] anhHang) {
        this.maHangLap = maHangLap;
        this.tenHangLap = tenHangLap;
        this.anhHang = anhHang;
    }

    public String getMaHangLap() {
        return maHangLap;
    }

    public void setMaHangLap(String maHangLap) {
        this.maHangLap = maHangLap;
    }

    public String getTenHangLap() {
        return tenHangLap;
    }

    public void setTenHangLap(String tenHangLap) {
        this.tenHangLap = tenHangLap;
    }

    public byte[] getAnhHang() {
        return anhHang;
    }

    public void setAnhHang(byte[] anhHang) {
        this.anhHang = anhHang;
    }

    @Override
    public String toString() {
        return "HangLaptop{" +
                "maHangLap = '" + maHangLap + '\'' +
                ", tenHangLap = '" + tenHangLap + '\'' +
                ", anhHang = " + anhHang +
                " : " + Arrays.toString(anhHang) +
                '}';
    }
}
