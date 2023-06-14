package com.nhom5.quanlylaptop.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.nhom5.quanlylaptop.Database.QLLaptopDB;
import com.nhom5.quanlylaptop.Entity.HangLaptop;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class HangLaptopDAO {
    QLLaptopDB qlLaptopDB;
    SQLiteDatabase db;
    Context context;
    String TAG = "HangLaptopDAO_____";
    ChangeType changeType = new ChangeType();

    public HangLaptopDAO(Context context) {
        this.context = context;
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
    }

    public ArrayList selectHangLaptop(String[] columns, String selection, String[] selectionArgs, String orderBy) {
        ArrayList<HangLaptop> listHLP = new ArrayList<>();
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Cursor c = db.query("HangLaptop", columns, selection, selectionArgs, null, null, orderBy);
        Log.d(TAG, "selectHangLaptop: Cursor: " + c.toString());

        if (c.getCount() > 0) {
            Log.d(TAG, "selectHangLaptop: Cursor not null");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Log.d(TAG, "selectHangLaptop: Cursor not last");
                String maHangLap = c.getString(0);
                byte[] anhHang = c.getBlob(1);
                String tenHangLap = c.getString(2);
                HangLaptop newHangLaptop = new HangLaptop(maHangLap, tenHangLap, anhHang);
                Log.d(TAG, "selectHangLaptop: new HangLaptop: " + newHangLaptop.toString());

                listHLP.add(newHangLaptop);
                c.moveToNext();
            }
            c.close();
        } else {
            Log.d(TAG, "selectHangLaptop: Cursor null");
        }
        db.close();

        return listHLP;
    }

    public void insertHangLaptop(HangLaptop hangLaptop) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maHangLap", hangLaptop.getMaHangLap());
        values.put("anhHang", hangLaptop.getAnhHang());
        values.put("tenHangLap", hangLaptop.getTenHangLap());
        Log.d(TAG, "insertHangLaptop: HangLaptop: " + hangLaptop.toString());
        Log.d(TAG, "insertHangLaptop: Values: " + values);

        long ketqua = db.insert("HangLaptop", null, values);
        if (ketqua > 0) {
            Log.d(TAG, "insertHangLaptop: Thêm thành công"); 
        } else {
            Log.d(TAG, "insertHangLaptop: Thêm thất bại");  
        }
        db.close();
    }

    public void updateHangLaptop(HangLaptop hangLaptop) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maHangLap", hangLaptop.getMaHangLap());
        values.put("anhHang", hangLaptop.getAnhHang());
        values.put("tenHangLap", hangLaptop.getTenHangLap());
        Log.d(TAG, "updateHangLaptop: HangLaptop: " + hangLaptop.toString());
        Log.d(TAG, "updateHangLaptop: Values: " + values);

        long ketqua = db.update("HangLaptop", values, "maHangLap=?", new String[]{String.valueOf(hangLaptop.getMaHangLap())});
        if (ketqua > 0) {
            Log.d(TAG, "updateHangLaptop: Sửa thành công"); 
        } else {
            Log.d(TAG, "updateHangLaptop: Sửa thất bại");  
        }
        db.close();
    }

    public void deleteHangLaptop(HangLaptop hangLaptop){
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Log.d(TAG, "deleteHangLaptop: HangLaptop: " + hangLaptop.toString());

        long ketqua = db.delete("HangLaptop", "maHangLap=?", new String[]{String.valueOf(hangLaptop.getMaHangLap())});
        if (ketqua > 0) {
            Log.d(TAG, "deleteHangLaptop: Xóa thành công"); 
        } else {
            Log.d(TAG, "deleteHangLaptop: Xóa thất bại");  
        }
        db.close();
    }
}
