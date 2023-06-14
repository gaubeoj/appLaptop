package com.nhom5.quanlylaptop.ActivityNV_Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.NVA_Loader.QL_DonHang_Loader;
import com.nhom5.quanlylaptop.R;

import java.util.ArrayList;

public class NVA_DonDat_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView count;
    RelativeLayout relativeLayout;
    LinearLayout linearLayout, linearDonHangEmpty;
    Context context = this;
    NhanVien nhanVien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nva_don_dat);
        recyclerView = findViewById(R.id.recyclerView_NVA_DonDat);
        count = findViewById(R.id.textView_Soluong);
        relativeLayout = findViewById(R.id.layoutView);
        linearLayout = findViewById(R.id.loadingView);
        linearDonHangEmpty = findViewById(R.id.linearDonHangEmpty);

        useToolbar();
        getUser();
        if (nhanVien != null){
            if (nhanVien.getRoleNV().equals("Bán hàng Online")) {
                QL_DonHang_Loader ql_donHang_loader = new QL_DonHang_Loader(context, recyclerView, count, linearLayout, linearDonHangEmpty, relativeLayout);
                ql_donHang_loader.execute("NVD");
            }
        } else {
            QL_DonHang_Loader ql_donHang_loader = new QL_DonHang_Loader(context, recyclerView, count, linearLayout, linearDonHangEmpty, relativeLayout);
            ql_donHang_loader.execute("NVD");
        }
    }

    private void getUser() {
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

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText("Quản lý đơn đặt hàng");
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUser();
        if (nhanVien != null){
            if (nhanVien.getRoleNV().equals("Bán hàng Online")) {
                QL_DonHang_Loader ql_donHang_loader = new QL_DonHang_Loader(context, recyclerView, count, linearLayout, linearDonHangEmpty, relativeLayout);
                ql_donHang_loader.execute("NVD");
            }
        } else {
            QL_DonHang_Loader ql_donHang_loader = new QL_DonHang_Loader(context, recyclerView, count, linearLayout, linearDonHangEmpty, relativeLayout);
            ql_donHang_loader.execute("NVD");
        }
    }
}