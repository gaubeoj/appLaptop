package com.nhom5.quanlylaptop.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nhom5.quanlylaptop.Database.QLLaptopDB;
import com.nhom5.quanlylaptop.Entity.DonHang;
import com.nhom5.quanlylaptop.Entity.ViTien;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class ViTienDAO {
    QLLaptopDB qlLaptopDB;
    SQLiteDatabase db;
    Context context;
    String TAG = "ViTienDAO_____";
    ChangeType changeType = new ChangeType();

    public ViTienDAO(Context context) {
        this.context = context;
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
    }

    public ArrayList selectViTien(String[] columns, String selection, String[] selectionArgs, String orderBy) {
        ArrayList<ViTien> listVi = new ArrayList<>();
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Cursor c = db.query("ViTien", columns, selection, selectionArgs, null, null, orderBy);
        Log.d(TAG, "selectViTien: Cursor: " + c.toString());

        if (c.getCount() > 0) {
            Log.d(TAG, "selectViTien: Cursor not null");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Log.d(TAG, "selectViTien: Cursor not last");
                String maVi = c.getString(0)+"";
                String maKH = c.getString(1);
                String soTien = c.getString(2);
                String nganHang = c.getString(3);
                ViTien newVi = new ViTien(maVi, maKH, soTien, nganHang);
                Log.d(TAG, "selectViTien: new ViTien: " + newVi.toString());

                listVi.add(newVi);
                c.moveToNext();
            }
            c.close();
        } else {
            Log.d(TAG, "selectViTien: Cursor null");
        }
        db.close();

        return listVi;
    }

    public void insertViTien(ViTien viTien) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maVi", viTien.getMaVi());
        values.put("maKH", viTien.getMaKH());
        values.put("soTien", viTien.getSoTien());
        values.put("nganHang", viTien.getNganHang());
        Log.d(TAG, "insertViTien: ViTien: " + viTien.toString());
        Log.d(TAG, "insertViTien: Values: " + values);

        long ketqua = db.insert("ViTien", null, values);
        if (ketqua > 0) {
            Log.d(TAG, "insertViTien: Thêm thành công");
        } else {
            Log.d(TAG, "insertViTien: Thêm thất bại");
        }
        db.close();
    }

    public int updateViTien(ViTien viTien) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maVi", viTien.getMaVi());
        values.put("maKH", viTien.getMaKH());
        values.put("soTien", viTien.getSoTien());
        values.put("nganHang", viTien.getNganHang());
        Log.d(TAG, "updateViTien: ViTien: " + viTien.toString());
        Log.d(TAG, "updateViTien: Values: " + values);

        long ketqua = db.update("ViTien", values, "maVi=?", new String[]{String.valueOf(viTien.getMaVi())});
        db.close();
        if (ketqua > 0) {
            Log.d(TAG, "updateViTien: Sửa thành công");
            return 1;
        } else {
            Log.d(TAG, "updateViTien: Sửa thất bại");
            return -1;
        }
    }

    public void deleteViTien(ViTien viTien){
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Log.d(TAG, "deleteViTien: ViTien: " + viTien.toString());

        long ketqua = db.delete("ViTien", "maVi=?", new String[]{String.valueOf(viTien.getMaVi())});
        if (ketqua > 0) {
            Log.d(TAG, "deleteViTien: Xóa thành công");
        } else {
            Log.d(TAG, "deleteViTien: Xóa thất bại");
        }
        db.close();
    }
}
