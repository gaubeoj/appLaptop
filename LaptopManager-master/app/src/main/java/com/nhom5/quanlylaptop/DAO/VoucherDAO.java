package com.nhom5.quanlylaptop.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.nhom5.quanlylaptop.Database.QLLaptopDB;
import com.nhom5.quanlylaptop.Entity.Voucher;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class VoucherDAO {
    QLLaptopDB qlLaptopDB;
    SQLiteDatabase db;
    Context context;
    String TAG = "VoucherDAO_____";
    ChangeType changeType = new ChangeType();

    public VoucherDAO(Context context) {
        this.context = context;
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
    }

    public ArrayList selectVoucher(String[] columns, String selection, String[] selectionArgs, String orderBy) {
        ArrayList<Voucher> listVou = new ArrayList<>();
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Cursor c = db.query("Voucher", columns, selection, selectionArgs, null, null, orderBy);
        Log.d(TAG, "selectVoucher: Cursor: " + c.toString());

        if (c.getCount() > 0) {
            Log.d(TAG, "selectVoucher: Cursor not null");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Log.d(TAG, "selectVoucher: Cursor not last");
                String maVoucher = c.getString(0)+"";
                String tenVoucher = c.getString(1);
                String giamGia = c.getString(2);
                @SuppressLint("Range") String ngayBD = changeType.longDateToString(c.getLong(c.getColumnIndex("ngayBD")));
                @SuppressLint("Range") String ngayKT = changeType.longDateToString(c.getLong(c.getColumnIndex("ngayKT")));
                Voucher newVoucher = new Voucher(maVoucher, tenVoucher, giamGia, ngayBD, ngayKT);
                Log.d(TAG, "selectVoucher: new Voucher: " + newVoucher.toString());

                listVou.add(newVoucher);
                c.moveToNext();
            }
            c.close();
        } else {
            Log.d(TAG, "selectVoucher: Cursor null");
        }
        db.close();

        return listVou;
    }

    public int insertVoucher(Voucher voucher) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenVoucher", voucher.getTenVoucher());
        values.put("giamGia", voucher.getGiamGia());
        values.put("ngayBD", changeType.stringToLongDate(voucher.getNgayBD()));
        values.put("ngayKT", changeType.stringToLongDate(voucher.getNgayKT()));
        Log.d(TAG, "insertVoucher: Voucher: " + voucher.toString());
        Log.d(TAG, "insertVoucher: Values: " + values);

        long ketqua = db.insert("Voucher", null, values);
        db.close();
        if (ketqua > 0) {
            Log.d(TAG, "insertVoucher: Thêm thành công");
            return 1;
        } else {
            Log.d(TAG, "insertVoucher: Thêm thất bại");
            return -1;
        }
    }

    public void updateVoucher(Voucher voucher) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maVoucher", voucher.getMaVoucher());
        values.put("tenVoucher", voucher.getTenVoucher());
        values.put("giamGia", voucher.getGiamGia());
        values.put("ngayBD", changeType.stringToLongDate(voucher.getNgayBD()));
        values.put("ngayKT", changeType.stringToLongDate(voucher.getNgayKT()));
        Log.d(TAG, "updateVoucher: Voucher: " + voucher.toString());
        Log.d(TAG, "updateVoucher: Values: " + values);

        long ketqua = db.update("Voucher", values, "maVoucher=?", new String[]{String.valueOf(voucher.getMaVoucher())});
        if (ketqua > 0) {
            Log.d(TAG, "updateVoucher: Sửa thành công"); 
        } else {
            Log.d(TAG, "updateVoucher: Sửa thất bại");  
        }
        db.close();
    }

    public void deleteVoucher(Voucher voucher){
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Log.d(TAG, "deleteVoucher: Voucher: " + voucher.toString());

        long ketqua = db.delete("Voucher", "maVoucher=?", new String[]{String.valueOf(voucher.getMaVoucher())});
        if (ketqua > 0) {
            Log.d(TAG, "deleteVoucher: Xóa thành công"); 
        } else {
            Log.d(TAG, "deleteVoucher: Xóa thất bại");  
        }
        db.close();
    }
}
