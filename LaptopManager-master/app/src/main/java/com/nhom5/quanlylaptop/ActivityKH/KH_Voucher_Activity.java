package com.nhom5.quanlylaptop.ActivityKH;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.KH_Loader.KH_Voucher_Loader;
import com.nhom5.quanlylaptop.R;

import java.util.ArrayList;

public class KH_Voucher_Activity extends AppCompatActivity {

    String TAG = "KH_Voucher_Activity_____";
    Context context = this;
    String openFrom;
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    int pos;
    KhachHang khachHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kh_voucher);
        recyclerView = findViewById(R.id.recyclerView_KH_Voucher);
        linearLayout = findViewById(R.id.linearVoucherEmpty);

        getUser();
        getData();
        if (khachHang != null) {
            KH_Voucher_Loader kh_voucher_loader = new KH_Voucher_Loader(context, recyclerView, linearLayout, openFrom, pos);
            kh_voucher_loader.execute(khachHang.getMaKH());
        }
        useToolbar();
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

    private void getData(){
        Intent intent = getIntent();
        try {
            openFrom = intent.getStringExtra("openFrom");
            if (openFrom != null){
                if (openFrom.equals("ThanhToan")){
                    pos = intent.getIntExtra("posLap", -1);
                } else {
                    pos = -1;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText("Danh sách Voucher");
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}