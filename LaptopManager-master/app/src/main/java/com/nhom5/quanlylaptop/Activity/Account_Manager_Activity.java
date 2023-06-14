package com.nhom5.quanlylaptop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.DAO.ThongBaoDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.Entity.ThongBao;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.PropertyPermission;

public class Account_Manager_Activity extends AppCompatActivity {

    AppCompatButton buttonUpdate;
    Context context = this;
    NhanVien nhanVien;
    KhachHang khachHang;
    String role;
    TextInputLayout til_Email, til_Ho, til_Ten, til_SDT, til_QueQuan, til_GioiTinh;
    Spinner genderSpinner;
    ChangeType changeType = new ChangeType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manager);
        genderSpinner = findViewById(R.id.spinner_Gender);
        buttonUpdate = findViewById(R.id.button_Update_Account);
        til_Email = findViewById(R.id.textInput_Email);
        til_Ho = findViewById(R.id.textInput_Ho);
        til_Ten = findViewById(R.id.textInput_Ten);
        til_SDT = findViewById(R.id.textInput_SDT);
        til_QueQuan = findViewById(R.id.textInput_QueQuan);
        til_GioiTinh = findViewById(R.id.textInput_GioiTinh);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        getRole();
        if (role.equals("kh")) {
            getUserKH();
            setTextInput();
        }
        if (role.equals("nv")) {
            getUserNV();
            setTextInput();
        }

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTextInput() == 1) {
                    if (role.equals("kh")) {
                        String email = changeType.deleteSpaceText(til_Email.getEditText().getText().toString());
                        String ho = changeType.deleteSpaceText(til_Ho.getEditText().getText().toString());
                        String ten = changeType.deleteSpaceText(til_Ten.getEditText().getText().toString());
                        String sdt = changeType.deleteSpaceText(til_SDT.getEditText().getText().toString());
                        String queQuan = changeType.deleteSpaceText(til_QueQuan.getEditText().getText().toString());
                        String gioiTinh = genderSpinner.getSelectedItem().toString();

                        KhachHang newKH = new KhachHang(khachHang.getMaKH(), ho, ten, gioiTinh, email,
                                khachHang.getMatKhau(), queQuan, sdt, khachHang.getHaveVi(), khachHang.getAvatar());
                        KhachHangDAO khachHangDAO = new KhachHangDAO(context);
                        khachHangDAO.updateKhachHang(newKH);
                        Date currentTime = Calendar.getInstance().getTime();
                        String date = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
                        ThongBaoDAO thongBaoDAO = new ThongBaoDAO(context);
                        ThongBao thongBao = new ThongBao("TB", khachHang.getMaKH(), "Thiết lập tài khoản",
                                " Bạn đã thay đổi thông tin cá nhân.\n Thông tin mới đã được cập nhật!", date);
                        thongBaoDAO.insertThongBao(thongBao, "kh");
                        finish();
                    }
                    if (role.equals("nv")) {
                        String email = changeType.deleteSpaceText(til_Email.getEditText().getText().toString());
                        String ho = changeType.deleteSpaceText(til_Ho.getEditText().getText().toString());
                        String ten = changeType.deleteSpaceText(til_Ten.getEditText().getText().toString());
                        String sdt = changeType.deleteSpaceText(til_SDT.getEditText().getText().toString());
                        String queQuan = changeType.deleteSpaceText(til_QueQuan.getEditText().getText().toString());
                        String gioiTinh = genderSpinner.getSelectedItem().toString();

                        NhanVien newNV = new NhanVien(nhanVien.getMaNV(), ho, ten, gioiTinh, email, nhanVien.getMatKhau()
                                , queQuan, sdt, nhanVien.getRoleNV(), nhanVien.getDoanhSo(), nhanVien.getSoSP(), nhanVien.getAvatar());
                        NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
                        nhanVienDAO.updateNhanVien(newNV);
                        Date currentTime = Calendar.getInstance().getTime();
                        String date = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
                        ThongBaoDAO thongBaoDAO = new ThongBaoDAO(context);
                        ThongBao thongBao = new ThongBao("TB", nhanVien.getMaNV(), "Thiết lập tài khoản",
                                " Bạn đã thay đổi thông tin cá nhân.\n Thông tin mới đã được cập nhật!", date);
                        thongBaoDAO.insertThongBao(thongBao, "nv");
                        finish();
                    }
                }
            }
        });

        useToolbar();
    }

    private int getTextInput() {
        String email = changeType.deleteSpaceText(til_Email.getEditText().getText().toString());
        String ho = changeType.deleteSpaceText(til_Ho.getEditText().getText().toString());
        String ten = changeType.deleteSpaceText(til_Ten.getEditText().getText().toString());
        String sdt = changeType.deleteSpaceText(til_SDT.getEditText().getText().toString());
        String queQuan = changeType.deleteSpaceText(til_QueQuan.getEditText().getText().toString());

        int check = 1;

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isEmpty()) {
            til_Email.setError("Định dạng email không hợp lệ!");
            til_Email.setErrorEnabled(true);
            check = -1;
        } else {
            til_Email.setError("");
            til_Email.setErrorEnabled(false);
        }

        if (ho.isEmpty()) {
            til_Ho.setError("Họ không được bỏ trống!");
            til_Ho.setErrorEnabled(true);
            check = -1;
        } else {
            til_Ho.setError("");
            til_Ho.setErrorEnabled(false);
        }

        if (ten.isEmpty()) {
            til_Ten.setError("Tên không được bỏ trống!");
            til_Ten.setErrorEnabled(true);
            check = -1;
        } else {
            til_Ten.setError("");
            til_Ten.setErrorEnabled(false);
        }

        if (genderSpinner.getSelectedItemPosition() == 0) {
            til_GioiTinh.setError("Giới tính phải được chọn!");
            til_GioiTinh.setErrorEnabled(true);
            check = -1;
        } else {
            til_Email.setError("");
            til_Email.setErrorEnabled(false);
        }

        if (!Patterns.PHONE.matcher(sdt).matches()) {
            til_SDT.setError("Định dạng số điện thoại không hợp lệ!");
            til_SDT.setErrorEnabled(true);
            check = -1;
        } else {
            til_SDT.setError("");
            til_SDT.setErrorEnabled(false);
        }

        if (queQuan.isEmpty()) {
            til_QueQuan.setError("Quê quán không được bỏ trống!");
            til_QueQuan.setErrorEnabled(true);
            check = -1;
        } else {
            til_QueQuan.setError("");
            til_QueQuan.setErrorEnabled(false);
        }

        return check;
    }

    private void setTextInput() {
        if (nhanVien != null) {
            til_Email.getEditText().setText(nhanVien.getEmail());
            til_Ho.getEditText().setText(nhanVien.getHoNV());
            til_Ten.getEditText().setText(nhanVien.getTenNV());
            til_SDT.getEditText().setText(nhanVien.getPhone());
            til_QueQuan.getEditText().setText(nhanVien.getQueQuan());
            if (nhanVien.getGioiTinh().equals("No Data")) {
                genderSpinner.setSelection(0);
            }
            if (nhanVien.getGioiTinh().equals("Nam")) {
                genderSpinner.setSelection(1);
            }
            if (nhanVien.getGioiTinh().equals("Nữ")) {
                genderSpinner.setSelection(2);
            }
            if (nhanVien.getGioiTinh().equals("Khác")) {
                genderSpinner.setSelection(3);
            }
        }
        if (khachHang != null) {
            til_Email.getEditText().setText(khachHang.getEmail());
            til_Ho.getEditText().setText(khachHang.getHoKH());
            til_Ten.getEditText().setText(khachHang.getTenKH());
            til_SDT.getEditText().setText(khachHang.getPhone());
            til_QueQuan.getEditText().setText(khachHang.getQueQuan());
            if (khachHang.getGioiTinh().equals("No Data")) {
                genderSpinner.setSelection(0);
            }
            if (khachHang.getGioiTinh().equals("Nam")) {
                genderSpinner.setSelection(1);
            }
            if (khachHang.getGioiTinh().equals("Nữ")) {
                genderSpinner.setSelection(2);
            }
            if (khachHang.getGioiTinh().equals("Khác")) {
                genderSpinner.setSelection(3);
            }
        }
    }

    private void getUserNV() {
        SharedPreferences pref = context.getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            nhanVien = null;
        } else {
            String user = pref.getString("who", "");
            NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
            ArrayList<NhanVien> list = nhanVienDAO.selectNhanVien(null, "maNV=?", new String[]{user}, null);
            if (list.size() > 0) {
                nhanVien = list.get(0);
            }
        }
    }

    private void getUserKH() {
        SharedPreferences pref = getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            khachHang = null;
        } else {
            String user = pref.getString("who", "");
            KhachHangDAO khachHangDAO = new KhachHangDAO(context);
            ArrayList<KhachHang> list = khachHangDAO.selectKhachHang(null, "maKH=?", new String[]{user}, null);
            if (list.size() > 0) {
                khachHang = list.get(0);
            }
        }
    }

    private void getRole() {
        Intent intent = getIntent();
        if (intent != null) {
            try {
                role = intent.getStringExtra("role");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText("Thông tin của bạn");
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
