package com.nhom5.quanlylaptop.Entity;

public class UseVoucher {
    private String maUse;
    private String maVoucher;
    private String maKH;
    private String isUsed;

    public UseVoucher(String maUse, String maVoucher, String maKH, String isUsed) {
        this.maUse = maUse;
        this.maVoucher = maVoucher;
        this.maKH = maKH;
        this.isUsed = isUsed;
    }

    public String getMaUse() {
        return maUse;
    }

    public void setMaUse(String maUse) {
        this.maUse = maUse;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    @Override
    public String toString() {
        return "UseVoucher{" +
                "maUse = '" + maUse + '\'' +
                ", maVoucher = '" + maVoucher + '\'' +
                ", maKH = '" + maKH + '\'' +
                ", isUsed = '" + isUsed + '\'' +
                '}';
    }
}
