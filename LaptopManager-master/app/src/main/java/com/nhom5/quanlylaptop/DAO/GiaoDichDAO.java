package com.nhom5.quanlylaptop.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.nhom5.quanlylaptop.Database.QLLaptopDB;
import com.nhom5.quanlylaptop.Entity.DiaChi;
import com.nhom5.quanlylaptop.Entity.GiaoDich;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class GiaoDichDAO {
    QLLaptopDB qlLaptopDB;
    SQLiteDatabase db;
    Context context;
    String TAG = "GiaoDichDAO_____";
    ChangeType changeType = new ChangeType();

    public GiaoDichDAO(Context context) {
        this.context = context;
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
    }

    public ArrayList selectGiaoDich(String[] columns, String selection, String[] selectionArgs, String orderBy) {
        ArrayList<GiaoDich> listGD = new ArrayList<>();
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Cursor c = db.query("GiaoDich", columns, selection, selectionArgs, null, null, orderBy);
        Log.d(TAG, "selectGiaoDich: Cursor: " + c.toString());

        if (c.getCount() > 0) {
            Log.d(TAG, "selectGiaoDich: Cursor not null");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Log.d(TAG, "selectGiaoDich: Cursor not last");
                String maGD = c.getString(0)+"";
                String maVi = c.getString(1);
                String title = c.getString(2);
                String chiTiet = c.getString(3);
                String soTien = c.getString(4);
                @SuppressLint("Range") String ngayGD = changeType.longDateToString(c.getLong(c.getColumnIndex("ngayGD")));
                GiaoDich newGD = new GiaoDich(maGD, maVi, title, chiTiet, soTien, ngayGD);
                Log.d(TAG, "selectGiaoDich: new GiaoDich: " + newGD.toString());

                listGD.add(newGD);
                c.moveToNext();
            }
            c.close();
        } else {
            Log.d(TAG, "selectGiaoDich: Cursor null");
        }
        db.close();

        return listGD;
    }

    public void insertGiaoDich(GiaoDich giaoDich) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maVi", giaoDich.getMaVi());
        values.put("title", giaoDich.getTitle());
        values.put("chiTiet", giaoDich.getChiTiet());
        values.put("soTien", giaoDich.getSoTien());
        values.put("ngayGD", changeType.stringToLongDate(giaoDich.getNgayGD()));
        Log.d(TAG, "insertGiaoDich: GiaoDich: " + giaoDich.toString());
        Log.d(TAG, "insertGiaoDich: Values: " + values);

        long ketqua = db.insert("GiaoDich", null, values);
        if (ketqua > 0) {
            Log.d(TAG, "insertGiaoDich: Thêm thành công");
        } else {
            Log.d(TAG, "insertGiaoDich: Thêm thất bại");
        }
        db.close();
    }

    public void updateGiaoDich(GiaoDich giaoDich) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maGD", giaoDich.getMaGD());
        values.put("maVi", giaoDich.getMaVi());
        values.put("title", giaoDich.getTitle());
        values.put("chiTiet", giaoDich.getChiTiet());
        values.put("soTien", giaoDich.getSoTien());
        values.put("ngayGD", changeType.stringToLongDate(giaoDich.getNgayGD()));
        Log.d(TAG, "updateGiaoDich: GiaoDich: " + giaoDich.toString());
        Log.d(TAG, "updateGiaoDich: Values: " + values);

        long ketqua = db.update("GiaoDich", values, "maGD=?", new String[]{String.valueOf(giaoDich.getMaGD())});
        if (ketqua > 0) {
            Log.d(TAG, "updateGiaoDich: Sửa thành công");
        } else {
            Log.d(TAG, "updateGiaoDich: Sửa thất bại");
        }
        db.close();
    }

    public void deleteGiaoDich(GiaoDich giaoDich){
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Log.d(TAG, "deleteGiaoDich: GiaoDich: " + giaoDich.toString());

        long ketqua = db.delete("GiaoDich", "maGD=?", new String[]{String.valueOf(giaoDich.getMaGD())});
        if (ketqua > 0) {
            Log.d(TAG, "deleteGiaoDich: Xóa thành công");
        } else {
            Log.d(TAG, "deleteGiaoDich: Xóa thất bại");
        }
        db.close();
    }
}
