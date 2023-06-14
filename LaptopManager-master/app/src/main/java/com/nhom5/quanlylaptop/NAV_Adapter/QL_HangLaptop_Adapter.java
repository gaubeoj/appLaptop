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
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;


public class QL_HangLaptop_Adapter extends BaseAdapter {

    ArrayList<HangLaptop> listHang;
    HangLaptopDAO hangLaptopDAO;
    String TAG = "QL_HangLaptop_Adapter_____";
    ImageView img;
    TextView name;

    public QL_HangLaptop_Adapter(ArrayList<HangLaptop> listHang) {
        this.listHang = listHang;
    }

    @Override
    public int getCount() {
        int count = listHang.size();
        Log.d(TAG, "getCount: count: " + count);
        return count;
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
        View row = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.cardview_nva_hanglaptop, vGroup, false);
        img = row.findViewById(R.id.imageView_HangLaptop);
        name = row.findViewById(R.id.textView_TenHang_Laptop);

        hangLaptopDAO = new HangLaptopDAO(vGroup.getContext());
        setRow(pos);

        return row;
    }

    public void setRow(int pos) {
        Log.d(TAG, "setRow: " + pos);
        HangLaptop hangLaptop = listHang.get(pos);
//        byte[] avatar = bitmapToByte(bitmap);
//        avatar = checkByteInput(avatar);
        Log.d(TAG, "setRow: Hãng Laptop: " + hangLaptop.toString());

        ChangeType changeType = new ChangeType();
        Bitmap ava = changeType.byteToBitmap(hangLaptop.getAnhHang());

        img.setImageBitmap(ava);
        name.setText(hangLaptop.getTenHangLap());
    }
}
