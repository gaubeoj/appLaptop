package com.nhom5.quanlylaptop.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.nhom5.quanlylaptop.Database.QLLaptopDB;
import com.nhom5.quanlylaptop.Entity.DonHang;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class DonHangDAO {
    QLLaptopDB qlLaptopDB;
    SQLiteDatabase db;
    Context context;
    String TAG = "DonHangDAO_____";
    ChangeType changeType = new ChangeType();

    public DonHangDAO(Context context) {
        this.context = context;
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
    }

    public ArrayList selectDonHang(String[] columns, String selection, String[] selectionArgs, String orderBy) {
        ArrayList<DonHang> listDH = new ArrayList<>();
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Cursor c = db.query("DonHang", columns, selection, selectionArgs, null, null, orderBy);
        Log.d(TAG, "selectDonHang: Cursor: " + c.toString());

        if (c.getCount() > 0) {
            Log.d(TAG, "selectDonHang: Cursor not null");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Log.d(TAG, "selectDonHang: Cursor not last");
                String maDH = c.getString(0) + "";
                String maNV = c.getString(1);
                String maKH = c.getString(2);
                String maLaptop = c.getString(3);
                String maVoucher = c.getString(4);
                String maRate = c.getString(5);
                int soLuong = 0;
                try {
                    soLuong = Integer.parseInt(c.getString(6));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String diaChi = c.getString(7);
                @SuppressLint("Range") String ngayMua = changeType.longDateToString(c.getLong(c.getColumnIndex("ngayMua")));
                String pthThanhToan = c.getString(9);
                String trangThai = c.getString(10);
                String isDanhGia = c.getString(11);
                String thanhTien = c.getString(12);
                DonHang newDH = new DonHang(maDH, maNV, maKH, maLaptop, maVoucher, maRate, diaChi,
                        ngayMua, pthThanhToan, trangThai, isDanhGia, thanhTien, soLuong);
                Log.d(TAG, "selectDonHang: new DonHang: " + newDH.toString());

                listDH.add(newDH);
                c.moveToNext();
            }
            c.close();
        } else {
            Log.d(TAG, "selectDonHang: Cursor null");
        }
        db.close();

        return listDH;
    }

    public int insertDonHang(DonHang donHang) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maNV", donHang.getMaNV());
        values.put("maKH", donHang.getMaKH());
        values.put("maLaptop", donHang.getMaLaptop());
        values.put("maVoucher", donHang.getMaVoucher());
        values.put("maRate", donHang.getMaRate());
        values.put("diaChi", donHang.getDiaChi());
        values.put("ngayMua", changeType.stringToLongDate(donHang.getNgayMua()));
        values.put("pthThanhToan", donHang.getLoaiThanhToan());
        values.put("trangThai", donHang.getTrangThai());
        values.put("isDanhGia", donHang.getIsDanhGia());
        values.put("thanhTien", donHang.getThanhTien());
        values.put("soLuong", donHang.getSoLuong());
        Log.d(TAG, "insertDonHang: DonHang: " + donHang.toString());
        Log.d(TAG, "insertDonHang: Values: " + values);

        long ketqua = db.insert("DonHang", null, values);
        db.close();
        if (ketqua > 0) {
            Log.d(TAG, "insertDonHang: Thêm thành công");
            return 1;
        } else {
            Log.d(TAG, "insertDonHang: Thêm thất bại");
            return -1;
        }
    }

    public int updateDonHang(DonHang donHang) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maDH", donHang.getMaDH());
        values.put("maNV", donHang.getMaNV());
        values.put("maKH", donHang.getMaKH());
        values.put("maLaptop", donHang.getMaLaptop());
        values.put("maVoucher", donHang.getMaVoucher());
        values.put("maRate", donHang.getMaRate());
        values.put("diaChi", donHang.getDiaChi());
        values.put("ngayMua", changeType.stringToLongDate(donHang.getNgayMua()));
        values.put("pthThanhToan", donHang.getLoaiThanhToan());
        values.put("trangThai", donHang.getTrangThai());
        values.put("isDanhGia", donHang.getIsDanhGia());
        values.put("thanhTien", donHang.getThanhTien());
        values.put("soLuong", donHang.getSoLuong());
        Log.d(TAG, "updateDonHang: DonHang: " + donHang.toString());
        Log.d(TAG, "updateDonHang: Values: " + values);

        long ketqua = db.update("DonHang", values, "maDH=?", new String[]{String.valueOf(donHang.getMaDH())});
        db.close();
        if (ketqua > 0) {
            Log.d(TAG, "updateDonHang: Sửa thành công");
            return 1;
        } else {
            Log.d(TAG, "updateDonHang: Sửa thất bại");
            return -1;
        }
    }

    public int deleteDonHang(DonHang donHang) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Log.d(TAG, "deleteDonHang: DonHang: " + donHang.toString());

        long ketqua = db.delete("DonHang", "maDH=?", new String[]{String.valueOf(donHang.getMaDH())});
        db.close();
        if (ketqua > 0) {
            Log.d(TAG, "deleteDonHang: Xóa thành công");
            return 1;
        } else {
            Log.d(TAG, "deleteDonHang: Xóa thất bại");
            return -1;
        }
    }

}
