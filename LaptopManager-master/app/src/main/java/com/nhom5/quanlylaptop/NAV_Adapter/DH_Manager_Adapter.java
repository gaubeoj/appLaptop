package com.nhom5.quanlylaptop.NAV_Adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhom5.quanlylaptop.DAO.HangLaptopDAO;
import com.nhom5.quanlylaptop.Entity.HangLaptop;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.Entity.Voucher;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class DH_Manager_Adapter extends BaseAdapter {

    ArrayList<Laptop> listLap;
    ArrayList<NhanVien> listNV;
    ArrayList<KhachHang> listKH;
    ArrayList<Voucher> listVou;
    String TAG = "DH_Manager_Adapter_____";
    TextView stt, name, other;
    ChangeType changeType = new ChangeType();

    public DH_Manager_Adapter(ArrayList<Laptop> listLap, ArrayList<NhanVien> listNV, ArrayList<KhachHang> listKH, ArrayList<Voucher> listVou) {
        this.listLap = listLap;
        this.listNV = listNV;
        this.listKH = listKH;
        this.listVou = listVou;
    }

    @Override
    public int getCount() {
        if (listLap != null) {
            return listLap.size();
        }
        if (listKH != null) {
            return listKH.size();
        }
        if (listNV != null) {
            return listNV.size();
        }
        if (listVou != null) {
            return listVou.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int pos, View v, ViewGroup vGroup) {
        View row = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.cardview_item_donhang, vGroup, false);
        stt = row.findViewById(R.id.textView_STT);
        name = row.findViewById(R.id.textView_Ten);
        other = row.findViewById(R.id.textView_Other);

        setRow(pos);
        return row;
    }

    public void setRow(int pos) {
        if (listLap != null) {
            Laptop laptop = listLap.get(pos);
            stt.setText(String.valueOf(pos + 1));
            name.setText(laptop.getTenLaptop());
            other.setText(laptop.getGiaTien());
        }
        if (listKH != null) {
            KhachHang khachHang = listKH.get(pos);
            stt.setText(String.valueOf(pos + 1));
            name.setText(changeType.fullNameKhachHang(khachHang));
            other.setText(khachHang.getEmail());
        }
        if (listNV != null) {
            NhanVien nhanVien = listNV.get(pos);
            stt.setText(String.valueOf(pos + 1));
            name.setText(changeType.fullNameNhanVien(nhanVien));
            other.setText(nhanVien.getEmail());
        }
        if (listVou != null) {
            Voucher voucher = listVou.get(pos);
            stt.setText(String.valueOf(pos + 1));
            name.setText(voucher.getTenVoucher());
            other.setText(voucher.getGiamGia());
        }
    }
}
