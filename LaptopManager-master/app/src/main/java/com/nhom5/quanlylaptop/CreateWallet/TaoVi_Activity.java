package com.nhom5.quanlylaptop.CreateWallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nhom5.quanlylaptop.ActivityKH.KH_ViTien_Activity;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.ThongBaoDAO;
import com.nhom5.quanlylaptop.DAO.ViTienDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.ThongBao;
import com.nhom5.quanlylaptop.Entity.ViTien;
import com.nhom5.quanlylaptop.PagerAdapter.TaoVi_PagerAdapter;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TaoVi_Activity extends AppCompatActivity {

    ViewPager viewPager;
    Button nextFrag, prevFrag;
    TaoVi_PagerAdapter pagerAdapter;
    KhachHang khachHang;
    Context context = this;
    ChangeType changeType = new ChangeType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_vi);
        viewPager = findViewById(R.id.viewPager);
        nextFrag = findViewById(R.id.button_Next);
        prevFrag = findViewById(R.id.button_Prev);

        getUser();
        useToolbar("Tạo ví FPT Pay");
        setUpView();
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

    private void useToolbar(String title) {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText(title);
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpView(){
        prevFrag.setVisibility(View.GONE);
        ViTienDAO viTienDAO = new ViTienDAO(context);
        KhachHangDAO khachHangDAO = new KhachHangDAO(context);

        prevFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() > 0){
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                }
            }
        });

        nextFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() < 2){
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    Intent intent = new Intent(context, KH_ViTien_Activity.class);
                    if (khachHang != null){
                        khachHangDAO.updateKhachHang(new KhachHang(khachHang.getMaKH(), khachHang.getHoKH(),
                                khachHang.getTenKH(), khachHang.getGioiTinh(), khachHang.getEmail(),
                                khachHang.getMatKhau(), khachHang.getQueQuan(), khachHang.getPhone(),
                                "true", khachHang.getAvatar()));
                        viTienDAO.insertViTien(new ViTien(khachHang.getMaKH(), khachHang.getMaKH(),
                                changeType.stringToStringMoney("0"), "null"));

                        Date currentTime = Calendar.getInstance().getTime();
                        String date = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
                        ThongBaoDAO thongBaoDAO = new ThongBaoDAO(context);
                        ThongBao thongBao = new ThongBao("TB", khachHang.getMaKH(), "Thiết lập tài khoản",
                                " Bạn đã thành công liên kết ví.\n Mong Ví điện tử FPT Pay sẽ hỗ trợ thật tốt cho bạn khi thanh toán.!", date);
                        thongBaoDAO.insertThongBao(thongBao, "kh");
                        finish();
                    }
                    startActivity(intent);
                    finish();
                }
            }
        });

        pagerAdapter = new TaoVi_PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    prevFrag.setVisibility(View.GONE);
                    nextFrag.setVisibility(View.VISIBLE);
                    useToolbar("Tạo ví FPT Pay");
                } else if (position == 1) {
                    prevFrag.setVisibility(View.VISIBLE);
                    nextFrag.setVisibility(View.VISIBLE);
                    useToolbar("Tài khoản liên kết");
                } else if (position == 2) {
                    prevFrag.setVisibility(View.VISIBLE);
                    nextFrag.setVisibility(View.VISIBLE);
                    useToolbar("Chọn ngân hàng liên kết");
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
