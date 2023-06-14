package com.nhom5.quanlylaptop.Entity;


public class IdData {
    private static final IdData idData = new IdData();
    private String idDC;
    private String idVou;

    private IdData() {
    }

    public static IdData getInstance() {
        return idData;
    }

    public String getIdDC() {
        return idDC;
    }

    public void setIdDC(String idDC) {
        this.idDC = idDC;
    }

    public String getIdVou() {
        return idVou;
    }

    public void setIdVou(String idVou) {
        this.idVou = idVou;
    }
}
