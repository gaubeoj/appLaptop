package com.nhom5.quanlylaptop.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.nhom5.quanlylaptop.Database.QLLaptopDB;
import com.nhom5.quanlylaptop.Entity.LaptopRate;
import com.nhom5.quanlylaptop.Entity.Voucher;

import java.util.ArrayList;

public class LaptopRateDAO {
    QLLaptopDB qlLaptopDB;
    SQLiteDatabase db;
    Context context;
    String TAG = "LaptopRateDAO_____";

    public LaptopRateDAO(Context context) {
        this.context = context;
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
    }

    public ArrayList selectLaptopRate(String[] columns, String selection, String[] selectionArgs, String orderBy) {
        ArrayList<LaptopRate> listVou = new ArrayList<>();
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Cursor c = db.query("LaptopRate", columns, selection, selectionArgs, null, null, orderBy);
        Log.d(TAG, "selectLaptopRate: Cursor: " + c.toString());

        if (c.getCount() > 0) {
            Log.d(TAG, "selectLaptopRate: Cursor not null");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Log.d(TAG, "selectLaptopRate: Cursor not last");
                String maRate = c.getString(0)+"";
                String maLaptop = c.getString(1);
                String danhGia = c.getString(2);
                float rating = 0;
                try {
                    rating = Float.parseFloat(c.getString(3));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LaptopRate newLR = new LaptopRate(maRate, maLaptop, danhGia, rating);
                Log.d(TAG, "selectLaptopRate: new LaptopRate: " + newLR.toString());

                listVou.add(newLR);
                c.moveToNext();
            }
            c.close();
        } else {
            Log.d(TAG, "selectLaptopRate: Cursor null");
        }
        db.close();

        return listVou;
    }

    public void insertLaptopRate(LaptopRate laptopRate) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maRate", laptopRate.getMaRate());
        values.put("maLaptop", laptopRate.getMaLaptop());
        values.put("danhGia", laptopRate.getDanhGia());
        values.put("rating", String.valueOf(laptopRate.getRating()));
        Log.d(TAG, "insertLaptopRate: LaptopRate: " + laptopRate.toString());
        Log.d(TAG, "insertLaptopRate: Values: " + values);

        long ketqua = db.insert("LaptopRate", null, values);
        if (ketqua > 0) {
            Log.d(TAG, "insertLaptopRate: Thêm thành công"); 
        } else {
            Log.d(TAG, "insertLaptopRate: Thêm thất bại");  
        }
        db.close();
    }

    public void updateLaptopRate(LaptopRate laptopRate) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maRate", laptopRate.getMaRate());
        values.put("maLaptop", laptopRate.getMaLaptop());
        values.put("danhGia", laptopRate.getDanhGia());
        values.put("rating", String.valueOf(laptopRate.getRating()));
        Log.d(TAG, "updateLaptopRate: LaptopRate: " + laptopRate.toString());
        Log.d(TAG, "updateLaptopRate: Values: " + values);

        long ketqua = db.update("LaptopRate", values, "maRate=?", new String[]{String.valueOf(laptopRate.getMaRate())});
        if (ketqua > 0) {
            Log.d(TAG, "updateLaptopRate: Sửa thành công"); 
        } else {
            Log.d(TAG, "updateLaptopRate: Sửa thất bại");  
        }
        db.close();
    }

    public void deleteLaptopRate(LaptopRate laptopRate) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Log.d(TAG, "deleteLaptopRate: LaptopRate: " + laptopRate.toString());

        long ketqua = db.delete("LaptopRate", "maRate=?", new String[]{String.valueOf(laptopRate.getMaRate())});
        if (ketqua > 0) {
            Log.d(TAG, "deleteLaptopRate: Xóa thành công"); 
        } else {
            Log.d(TAG, "deleteLaptopRate: Xóa thất bại");  
        }
        db.close();
    }
}
