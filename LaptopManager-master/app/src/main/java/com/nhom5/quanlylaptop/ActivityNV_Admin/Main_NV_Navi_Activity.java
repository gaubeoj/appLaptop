package com.nhom5.quanlylaptop.ActivityNV_Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.nhom5.quanlylaptop.Activity.LienHe_Activity;
import com.nhom5.quanlylaptop.Activity.PickRole_Activity;
import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;
import com.nhom5.quanlylaptop.FragmentNV_Admin.NVA_Home_Fragment;
import com.nhom5.quanlylaptop.FragmentNV_Admin.NV_Account_Fragment;
import com.nhom5.quanlylaptop.FragmentNV_Admin.NV_ThongBao_Fragment;
import com.nhom5.quanlylaptop.FragmentQuanLy.QL_DonHang_Fragment;
import com.nhom5.quanlylaptop.FragmentQuanLy.QL_KhachHang_Fragment;
import com.nhom5.quanlylaptop.FragmentQuanLy.QL_Laptop_Fragment;
import com.nhom5.quanlylaptop.FragmentQuanLy.QL_ThongKe_Fragment;
import com.nhom5.quanlylaptop.FragmentQuanLy.QL_Voucher_Fragment;

import java.util.ArrayList;

public class Main_NV_Navi_Activity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NavigationView naviView;
    String TAG = "Main_NV_Navi_Activity_____";
    DrawerLayout drawerLayout;
    int itemNaviDr;
    Context context = this;
    NhanVien nhanVien;
    ChangeType changeType = new ChangeType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nv_navi);
        bottomNavigationView = findViewById(R.id.bottomNavi);
        naviView = findViewById(R.id.naviView);
        drawerLayout = findViewById(R.id.drawerLayout);

        getUser();
        useToolbar("", 0);
        setViewNaviBottom();
        setViewNaviDrawer();

        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
        if (pref != null) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("what", "none");
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
        if (pref != null) {
            String infoWhat = pref.getString("what", "none");
            if (infoWhat.equals("home")) {
                setOnResumeNavi(0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("what", "none");
                editor.apply();
            }
            if (infoWhat.equals("noti")) {
                setOnResumeNavi(1);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("what", "none");
                editor.apply();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void useToolbar(String title, int type) {
        setSupportActionBar(findViewById(R.id.toolbar_Account));
        if (type == 0) {
            layoutAccount(title);
        } else {
            layoutSearch(title);
        }
        ImageButton open = findViewById(R.id.imageButton_Open_Drawer);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    private void getUser() {
        SharedPreferences pref = getSharedPreferences("Who_Login", MODE_PRIVATE);
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

    private void layoutAccount(String title) {
        LinearLayout layoutAcc = findViewById(R.id.layout_Account);
        LinearLayout layoutSearch = findViewById(R.id.layout_Search);
        TextView titleView = findViewById(R.id.textView_Title_Toolbar_Acc);
        ImageView imageView = findViewById(R.id.imageView_Avatar);

        getUser();
        layoutAcc.setVisibility(View.VISIBLE);
        layoutSearch.setVisibility(View.GONE);
        titleView.setText(title);
        if (nhanVien != null) {
            imageView.setImageBitmap(changeType.byteToBitmap(nhanVien.getAvatar()));
        }
    }

    private void layoutSearch(String title) {
        LinearLayout layoutAcc = findViewById(R.id.layout_Account);
        LinearLayout layoutSearch = findViewById(R.id.layout_Search);
        TextView titleView = findViewById(R.id.textView_Title_Toolbar_Search);

        layoutAcc.setVisibility(View.GONE);
        layoutSearch.setVisibility(View.VISIBLE);
        titleView.setText(title);
    }

    private void setViewNaviDrawer() {
        naviView.getMenu().getItem(0).setChecked(true);
        naviView.getMenu().getItem(0).setCheckable(true);
        naviView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                FragmentManager manager = getSupportFragmentManager();
                if (id == R.id.item_navi_drawer_nv_trangChu) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 0 - home");
                    NVA_Home_Fragment nva_home_fragment = new NVA_Home_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, nva_home_fragment).commit();
                    itemNaviDr = 0;
                    useToolbar("", 0);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_nv_home).setCheckable(true);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_nv_home).setChecked(true);
                }
                if (id == R.id.item_navi_drawer_nv_noti) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 1 - fptshop");
                    NV_ThongBao_Fragment nv_thongBao_fragment = new NV_ThongBao_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, nv_thongBao_fragment).commit();
                    itemNaviDr = 1;
                    useToolbar("Thông Báo", 0);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_nv_noti).setCheckable(true);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_nv_noti).setChecked(true);
                }
                if (id == R.id.item_navi_drawer_nv_Laptop) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 2 - laptop");
                    QL_Laptop_Fragment ql_laptop_fragment = new QL_Laptop_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, ql_laptop_fragment).commit();
                    itemNaviDr = 3;
                    useToolbar("QLý Laptop", 1);
                    bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_nv_DonHang) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 3 - đơn hàng");
                    QL_DonHang_Fragment ql_donHang_fragment = new QL_DonHang_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, ql_donHang_fragment).commit();
                    itemNaviDr = 4;
                    useToolbar("QLý Đơn Hàng", 0);
                    bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_nv_KhachHang) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 4 - khách hàng");
                    QL_KhachHang_Fragment ql_khachHang_fragment = new QL_KhachHang_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, ql_khachHang_fragment).commit();
                    itemNaviDr = 5;
                    useToolbar("QLý Khách Hàng", 1);
                    bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_nv_Voucher) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 5 - voucher");
                    QL_Voucher_Fragment ql_voucher_fragment = new QL_Voucher_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, ql_voucher_fragment).commit();
                    itemNaviDr = 6;
                    useToolbar("QLý Voucher", 1);
                    bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_nv_ThongKe) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 6 - thống kê");
                    QL_ThongKe_Fragment ql_thongKe_fragment = new QL_ThongKe_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, ql_thongKe_fragment).commit();
                    itemNaviDr = 7;
                    useToolbar("Doanh Thu\nThống Kê", 0);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_nv_thongKe).setCheckable(true);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_nv_thongKe).setChecked(true);
                }
                if (id == R.id.item_navi_drawer_kh_DH_Damua) {
                    item.setCheckable(true);
                    itemNaviDr = 8;
                    Log.d(TAG, "onNavigationItemSelected: 7 - dh đã mua");
                    startActivity(new Intent(context, NV_DonHang_Activity.class));
                }
                if (id == R.id.item_navi_drawer_nv_lienHe) {
                    itemNaviDr = 9;
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 8 - liên hệ");
                    startActivity(new Intent(context, LienHe_Activity.class));
                }
                if (id == R.id.item_navi_drawer_admin_Logout) {
                    itemNaviDr = 10;
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 8 - log out");
                    SharedPreferences pref = getSharedPreferences("Who_Login", MODE_PRIVATE);
                    if (pref != null) {
                        if (pref.getString("isLogin", "").equals("true")){
                            finish();
                            startActivity(new Intent(context, PickRole_Activity.class));
                        } else {
                            finish();
                        }
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("isLogin", "false");
                        editor.apply();
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                }, 500);
                return true;
            }
        });
        Log.d(TAG, "onNavigationItemSelected: itemNavi: " + itemNaviDr);
        getSupportActionBar().show();
    }

    private void setViewNaviBottom() {
        FragmentManager manager= getSupportFragmentManager();
        NVA_Home_Fragment nva_home_fragment = new NVA_Home_Fragment();
        manager.beginTransaction().replace(R.id.frLayout, nva_home_fragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int i = item.getItemId();
                if (i == R.id.item_navi_bottom_nv_home) {
                    NVA_Home_Fragment nva_home_fragment = new NVA_Home_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, nva_home_fragment).commit();
                    naviView.getMenu().getItem(0).setChecked(true);
                    naviView.getMenu().getItem(0).setCheckable(true);
                    itemNaviDr = 0;
                    useToolbar("", 0);
                    getSupportActionBar().show();
                }
                if (i == R.id.item_navi_bottom_nv_noti) {
                    NV_ThongBao_Fragment nv_thongBao_fragment = new NV_ThongBao_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, nv_thongBao_fragment).commit();
                    naviView.getMenu().getItem(1).setChecked(true);
                    naviView.getMenu().getItem(1).setCheckable(true);
                    itemNaviDr = 1;
                    useToolbar("Thông Báo", 0);
                    getSupportActionBar().show();
                }
                if (i == R.id.item_navi_bottom_nv_thongKe) {
                    QL_ThongKe_Fragment ql_thongKe_fragment = new QL_ThongKe_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, ql_thongKe_fragment).commit();
                    naviView.getMenu().getItem(7).setChecked(true);
                    naviView.getMenu().getItem(7).setCheckable(true);
                    itemNaviDr = 7;
                    useToolbar("Doanh Thu\nThống Kê", 0);
                    getSupportActionBar().show();
                }
                if (i == R.id.item_navi_bottom_nv_acc) {
                    NV_Account_Fragment nv_account_fragment = new NV_Account_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, nv_account_fragment).commit();
                    Log.d(TAG, "onNavigationItemSelected: itemNavi: " + itemNaviDr);
                    naviView.getMenu().getItem(itemNaviDr).setChecked(false);
                    naviView.getMenu().getItem(itemNaviDr).setCheckable(false);
                    getSupportActionBar().hide();
                }
                return true;
            }
        });
    }

    private void setOnResumeNavi(int frag) {
        FragmentManager manager = getSupportFragmentManager();
        itemNaviDr = frag;
        if (frag == 0) {
            useToolbar("", 0);
            NVA_Home_Fragment nva_home_fragment = new NVA_Home_Fragment();
            manager.beginTransaction().replace(R.id.frLayout, nva_home_fragment).commit();
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_nv_home).setCheckable(true);
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_nv_home).setChecked(true);
            naviView.getMenu().getItem(0).setChecked(true);
            naviView.getMenu().getItem(0).setCheckable(true);
            getSupportActionBar().show();
        }
        if (frag == 1) {
            useToolbar("Thông Báo", 0);
            NV_ThongBao_Fragment nv_thongBao_fragment = new NV_ThongBao_Fragment();
            manager.beginTransaction().replace(R.id.frLayout, nv_thongBao_fragment).commit();
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_nv_noti).setCheckable(true);
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_nv_noti).setChecked(true);
            naviView.getMenu().getItem(1).setChecked(true);
            naviView.getMenu().getItem(1).setCheckable(true);
            getSupportActionBar().show();
        }
    }
}