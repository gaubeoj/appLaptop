package com.nhom5.quanlylaptop.Entity;

import android.os.Binder;

public class LaptopRate extends Binder {
    private String maRate;
    private String maLaptop;
    private String danhGia;
    private float rating;

    public LaptopRate(String maRate, String maLaptop, String danhGia, float rating) {
        this.maRate = maRate;
        this.maLaptop = maLaptop;
        this.danhGia = danhGia;
        this.rating = rating;
    }

    public String getMaRate() {
        return maRate;
    }

    public void setMaRate(String maRate) {
        this.maRate = maRate;
    }

    public String getMaLaptop() {
        return maLaptop;
    }

    public void setMaLaptop(String maLaptop) {
        this.maLaptop = maLaptop;
    }

    public String getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(String danhGia) {
        this.danhGia = danhGia;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "LaptopRate{" +
                "maRate = '" + maRate + '\'' +
                ", maLaptop = '" + maLaptop + '\'' +
                ", danhGia = '" + danhGia + '\'' +
                ", rating = " + rating +
                '}';
    }
}
