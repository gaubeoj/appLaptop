package com.nhom5.quanlylaptop.ActivityKH;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nhom5.quanlylaptop.Activity.DiaChi_Manager_Activity;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.KH_Loader.KH_DiaChi_Loader;
import com.nhom5.quanlylaptop.R;

import java.util.ArrayList;

public class KH_DiaChi_Activity extends AppCompatActivity {

    String TAG = "KH_DiaChi_Activity_____";
    Context context = this;
    KhachHang khachHang;
    AppCompatButton button_AddDC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kh_dia_chi);

        getUser();
        if (khachHang != null){
            KH_DiaChi_Loader kh_diaChi_loader = new KH_DiaChi_Loader(context, findViewById(R.id.recyclerView_DiaChi));
            kh_diaChi_loader.execute(khachHang.getMaKH());
        }

        button_AddDC = findViewById(R.id.button_AddDC);
        button_AddDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DiaChi_Manager_Activity.class);
                intent.putExtra("typeDC" , 0);
                startActivity(intent);
            }
        });

        useToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUser();
        if (khachHang != null){
            KH_DiaChi_Loader kh_diaChi_loader = new KH_DiaChi_Loader(context, findViewById(R.id.recyclerView_DiaChi));
            kh_diaChi_loader.execute(khachHang.getMaKH());
        }
    }

    private void getUser(){
        SharedPreferences pref = getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            khachHang = null;
        } else {
            String user = pref.getString("who", "");
            KhachHangDAO khachHangDAO = new KhachHangDAO(context);
            ArrayList<KhachHang> list = khachHangDAO.selectKhachHang(null, "maKH=?", new String[]{user}, null);
            if (list.size() > 0){
                khachHang = list.get(0);
            }
        }
    }

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText("Danh sách Địa chỉ");
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}