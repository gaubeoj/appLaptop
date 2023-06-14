package com.nhom5.quanlylaptop.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.nhom5.quanlylaptop.Database.QLLaptopDB;
import com.nhom5.quanlylaptop.Entity.NhanVien;

import java.util.ArrayList;

public class NhanVienDAO {
    QLLaptopDB qlLaptopDB;
    SQLiteDatabase db;
    Context context;
    String TAG = "NhanVienDAO_____";

    public NhanVienDAO(Context context) {
        this.context = context;
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
    }

    public ArrayList selectNhanVien(String[] columns, String selection, String[] selectionArgs, String orderBy) {
        ArrayList<NhanVien> listNV = new ArrayList<>();
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Cursor c = db.query("NhanVien", columns, selection, selectionArgs, null, null, orderBy);
        Log.d(TAG, "selectNhanVien: Cursor: " + c.toString());

        if (c.getCount() > 0) {
            Log.d(TAG, "selectNhanVien: Cursor not null");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Log.d(TAG, "selectNhanVien: Cursor not last");
                String maNV = c.getString(0)+"";
                byte[] avatar = c.getBlob(1);
                String hoNV = c.getString(2);
                String tenNV = c.getString(3);
                String gioiTinh = c.getString(4);
                String email = c.getString(5);
                String matKhau = c.getString(6);
                String queQuan = c.getString(7);
                String phone = c.getString(8);
                int doanhSo = 0;
                try {
                    doanhSo = Integer.parseInt(c.getString(9));
                } catch (Exception e){
                    e.printStackTrace();
                }
                int soSP = 0;
                try {
                    soSP = Integer.parseInt(c.getString(10));
                } catch (Exception e){
                    e.printStackTrace();
                }
                String roleNV = c.getString(11);
                NhanVien newNV = new NhanVien(maNV, hoNV, tenNV, gioiTinh, email, matKhau, queQuan, phone, roleNV, doanhSo, soSP, avatar);
                Log.d(TAG, "selectNhanVien: new NhanVien: " + newNV.toString());

                listNV.add(newNV);
                c.moveToNext();
            }
            c.close();
        } else {
            Log.d(TAG, "selectNhanVien: Cursor null");
        }
        db.close();

        return listNV;
    }

    public int insertNhanVien(NhanVien nhanVien) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("avatar", nhanVien.getAvatar());
        values.put("hoNV", nhanVien.getHoNV());
        values.put("tenNV", nhanVien.getTenNV());
        values.put("gioiTinh", nhanVien.getGioiTinh());
        values.put("email", nhanVien.getEmail());
        values.put("matKhau", nhanVien.getMatKhau());
        values.put("queQuan", nhanVien.getQueQuan());
        values.put("phone", nhanVien.getPhone());
        values.put("doanhSo", nhanVien.getDoanhSo());
        values.put("soSP", nhanVien.getSoSP());
        values.put("roleNV", nhanVien.getRoleNV());
        Log.d(TAG, "insertNhanVien: NhanVien: " + nhanVien.toString());
        Log.d(TAG, "insertNhanVien: Values: " + values);

        long ketqua = db.insert("NhanVien", null, values);
        db.close();
        if (ketqua > 0) {
            Log.d(TAG, "insertNhanVien: Thêm thành công");
            return 1;
        } else {
            Log.d(TAG, "insertNhanVien: Thêm thất bại");
            return -1;
        }
    }

    public int updateNhanVien(NhanVien nhanVien) {
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maNV", nhanVien.getMaNV());
        values.put("avatar", nhanVien.getAvatar());
        values.put("hoNV", nhanVien.getHoNV());
        values.put("tenNV", nhanVien.getTenNV());
        values.put("gioiTinh", nhanVien.getGioiTinh());
        values.put("email", nhanVien.getEmail());
        values.put("matKhau", nhanVien.getMatKhau());
        values.put("queQuan", nhanVien.getQueQuan());
        values.put("phone", nhanVien.getPhone());
        values.put("doanhSo", nhanVien.getDoanhSo());
        values.put("soSP", nhanVien.getSoSP());
        values.put("roleNV", nhanVien.getRoleNV());
        Log.d(TAG, "insertNhanVien: NhanVien: " + nhanVien.toString());
        Log.d(TAG, "insertNhanVien: Values: " + values);

        long ketqua = db.update("NhanVien", values, "maNV=?", new String[]{String.valueOf(nhanVien.getMaNV())});
        db.close();
        if (ketqua > 0) {
            Log.d(TAG, "updateVoucher: Sửa thành công");
            return 1;
        } else {
            Log.d(TAG, "updateVoucher: Sửa thất bại");
            return -1;
        }
    }

    public void deleteNhanVien(NhanVien nhanVien){
        qlLaptopDB = new QLLaptopDB(context);
        db = qlLaptopDB.getWritableDatabase();
        Log.d(TAG, "deleteNhanVien: NhanVien: " + nhanVien.toString());

        long ketqua = db.delete("NhanVien", "maNV=?", new String[]{String.valueOf(nhanVien.getMaNV())});
        if (ketqua > 0) {
            Log.d(TAG, "deleteNhanVien: Xóa thành công"); 
        } else {
            Log.d(TAG, "deleteNhanVien: Xóa thất bại");  
        }
        db.close();
    }
}
