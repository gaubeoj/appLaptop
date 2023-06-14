package com.nhom5.quanlylaptop.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nhom5.quanlylaptop.Database.QLLaptopDB;
import com.nhom5.quanlylaptop.Entity.DiaChi;
import com.nhom5.quanlylaptop.Entity.DonHang;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class DiaChiDAO {
    QLLaptopDB qlLaptopDB;
    SQLiteDatabase db;
    Context context;
    String TAG = "DiaChiDAO_____";
    ChangeType changeType = new ChangeType();

    public DiaChiDAO(Context context) {
        this.context = context;
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
    }

    public ArrayList selectDiaChi(String[] columns, String selection, String[] selectionArgs, String orderBy) {
        ArrayList<DiaChi> listDC = new ArrayList<>();
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Cursor c = db.query("DiaChi", columns, selection, selectionArgs, null, null, orderBy);
        Log.d(TAG, "selectDiaChi: Cursor: " + c.toString());

        if (c.getCount() > 0) {
            Log.d(TAG, "selectDiaChi: Cursor not null");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Log.d(TAG, "selectDiaChi: Cursor not last");
                String maDC = c.getString(0)+"";
                String maKH = c.getString(1);
                String tenNguoiNhan = c.getString(2);
                String SDT = c.getString(3);
                String diaChi = c.getString(4);
                String thanhPho = c.getString(5);
                String quanHuyen = c.getString(6);
                String xaPhuong = c.getString(7);
                DiaChi newDC = new DiaChi(maDC, maKH, tenNguoiNhan, SDT, diaChi, thanhPho, quanHuyen, xaPhuong);
                Log.d(TAG, "selectDiaChi: new DiaChi: " + newDC.toString());

                listDC.add(newDC);
                c.moveToNext();
            }
            c.close();
        } else {
            Log.d(TAG, "selectDiaChi: Cursor null");
        }
        db.close();

        return listDC;
    }

    public void insertDiaChi(DiaChi diaChi) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maKH", diaChi.getMaKH());
        values.put("tenNguoiNhan", diaChi.getTenNguoiNhan());
        values.put("phone", diaChi.getSDT());
        values.put("diaChi", diaChi.getDiaChi());
        values.put("thanhPho", diaChi.getThanhPho());
        values.put("quanHuyen", diaChi.getQuanHuyen());
        values.put("phuongXa", diaChi.getXaPhuong());
        Log.d(TAG, "insertDiaChi: DiaChi: " + diaChi.toString());
        Log.d(TAG, "insertDiaChi: Values: " + values);

        long ketqua = db.insert("DiaChi", null, values);
        if (ketqua > 0) {
            Log.d(TAG, "insertDiaChi: Thêm thành công");
        } else {
            Log.d(TAG, "insertDiaChi: Thêm thất bại");
        }
        db.close();
    }

    public void updateDiaChi(DiaChi diaChi) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maDC", diaChi.getMaDC());
        values.put("maKH", diaChi.getMaKH());
        values.put("tenNguoiNhan", diaChi.getTenNguoiNhan());
        values.put("phone", diaChi.getSDT());
        values.put("diaChi", diaChi.getDiaChi());
        values.put("thanhPho", diaChi.getThanhPho());
        values.put("quanHuyen", diaChi.getQuanHuyen());
        values.put("phuongXa", diaChi.getXaPhuong());
        Log.d(TAG, "updateDiaChi: DiaChi: " + diaChi.toString());
        Log.d(TAG, "updateDiaChi: Values: " + values);

        long ketqua = db.update("DiaChi", values, "maDC=?", new String[]{String.valueOf(diaChi.getMaDC())});
        if (ketqua > 0) {
            Log.d(TAG, "updateDonHang: Sửa thành công");
        } else {
            Log.d(TAG, "updateDonHang: Sửa thất bại");
        }
        db.close();
    }

    public void deleteDiaChi(DiaChi diaChi){
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Log.d(TAG, "deleteDiaChi: DiaChi: " + diaChi.toString());

        long ketqua = db.delete("DiaChi", "maDC=?", new String[]{String.valueOf(diaChi.getMaDC())});
        if (ketqua > 0) {
            Log.d(TAG, "deleteDiaChi: Xóa thành công");
        } else {
            Log.d(TAG, "deleteDiaChi: Xóa thất bại");
        }
        db.close();
    }
}
