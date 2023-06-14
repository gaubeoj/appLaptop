package com.nhom5.quanlylaptop.ActivityKH;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.KH_Loader.KH_DonHang_Loader;
import com.nhom5.quanlylaptop.R;

import java.util.ArrayList;

public class KH_DonHang_CTT_Activity extends AppCompatActivity {

    Context context;
    String TAG = "KH_DonHang_Activity_____";
    RecyclerView recyclerView;
    LinearLayout linearLayout, linearDonHangEmpty;
    KhachHang khachHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kh_don_hang_ctt);
        context = this;
        linearLayout = findViewById(R.id.loadingView);
        recyclerView = findViewById(R.id.recyclerView_KH_DonHang);
        linearDonHangEmpty = findViewById(R.id.linearDonHangEmpty);

        getUser();
        if (khachHang != null) {
            KH_DonHang_Loader kh_donHang_loader = new KH_DonHang_Loader(context, recyclerView, linearLayout, linearDonHangEmpty, "none");
            kh_donHang_loader.execute(khachHang.getMaKH());
        }
        useToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUser();
        if (khachHang != null) {
            KH_DonHang_Loader kh_donHang_loader = new KH_DonHang_Loader(context, recyclerView, linearLayout, linearDonHangEmpty, "none");
            kh_donHang_loader.execute(khachHang.getMaKH());
        }
    }

    private void getUser() {
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

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText("Đơn hàng chờ thanh toán");
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}