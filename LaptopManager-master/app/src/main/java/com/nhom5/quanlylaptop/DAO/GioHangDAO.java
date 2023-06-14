package com.nhom5.quanlylaptop.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.nhom5.quanlylaptop.Database.QLLaptopDB;
import com.nhom5.quanlylaptop.Entity.GioHang;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class GioHangDAO {
    QLLaptopDB qlLaptopDB;
    SQLiteDatabase db;
    Context context;
    String TAG = "GioHangDAO_____";
    ChangeType changeType = new ChangeType();

    public GioHangDAO(Context context) {
        this.context = context;
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
    }

    public ArrayList selectGioHang(String[] columns, String selection, String[] selectionArgs, String orderBy) {
        ArrayList<GioHang> listGio = new ArrayList<>();
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Cursor c = db.query("GioHang", columns, selection, selectionArgs, null, null, orderBy);
        Log.d(TAG, "selectGioHang: Cursor: " + c.toString());

        if (c.getCount() > 0) {
            Log.d(TAG, "selectGioHang: Cursor not null");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Log.d(TAG, "selectGioHang: Cursor not last");
                String maGio = c.getString(0)+"";
                String maLaptop = c.getString(1);
                String maKH = c.getString(2);
                int soLuong = 0;
                try {
                    soLuong = Integer.parseInt(c.getString(3));
                } catch (Exception e){
                    e.printStackTrace();
                }
                @SuppressLint("Range") String ngayThem = changeType.longDateToString(c.getLong(c.getColumnIndex("ngayThem")));
                String maVou = c.getString(5);
                GioHang newGio = new GioHang(maGio, maLaptop, maKH, ngayThem, maVou, soLuong);
                Log.d(TAG, "selectGioHang: new GioHang: " + newGio.toString());

                listGio.add(newGio);
                c.moveToNext();
            }
            c.close();
        } else {
            Log.d(TAG, "selectGioHang: Cursor null");
        }
        db.close();

        return listGio;
    }

    public void insertGioHang(GioHang gioHang) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maLaptop", gioHang.getMaLaptop());
        values.put("maKH", gioHang.getMaKH());
        values.put("maVou", gioHang.getMaVou());
        values.put("soLuong", gioHang.getSoLuong());
        values.put("ngayThem", changeType.stringToLongDate(gioHang.getNgayThem()));
        Log.d(TAG, "insertGioHang: GioHang: " + gioHang.toString());
        Log.d(TAG, "insertGioHang: Values: " + values);

        long ketqua = db.insert("GioHang", null, values);
        if (ketqua > 0) {
            Toast.makeText(context, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "insertGioHang: Thêm thành công"); 
        } else {
            Toast.makeText(context, "Lỗi thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "insertGioHang: Thêm thất bại");  
        }
        db.close();
    }

    public void updateGioHang(GioHang gioHang) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maGio", gioHang.getMaGio());
        values.put("maLaptop", gioHang.getMaLaptop());
        values.put("maKH", gioHang.getMaKH());
        values.put("maVou", gioHang.getMaVou());
        values.put("soLuong", gioHang.getSoLuong());
        values.put("ngayThem", gioHang.getNgayThem());
        Log.d(TAG, "updateGioHang: GioHang: " + gioHang.toString());
        Log.d(TAG, "updateGioHang: Values: " + values);

        long ketqua = db.update("GioHang", values, "maGio=?", new String[]{String.valueOf(gioHang.getMaGio())});
        if (ketqua > 0) {
            Log.d(TAG, "updateGioHang: Sửa thành công");
            Log.d(TAG, "insertGioHang: Thêm thành công");
        } else {
            Log.d(TAG, "updateGioHang: Sửa thất bại");
            Log.d(TAG, "insertGioHang: Thêm thất bại");
        }
        db.close();
    }

    public void deleteGioHang(GioHang gioHang){
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Log.d(TAG, "deleteGioHang: GioHang: " + gioHang.toString());

        long ketqua = db.delete("GioHang", "maGio=?", new String[]{String.valueOf(gioHang.getMaGio())});
        if (ketqua > 0) {
            Log.d(TAG, "deleteGioHang: Xóa thành công"); 
        } else {
            Log.d(TAG, "deleteGioHang: Xóa thất bại");  
        }
        db.close();
    }
}
