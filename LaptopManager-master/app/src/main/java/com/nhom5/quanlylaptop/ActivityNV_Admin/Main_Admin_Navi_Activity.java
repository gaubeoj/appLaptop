package com.nhom5.quanlylaptop.ActivityNV_Admin;

import androidx.annotation.NonNull;
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

import com.nhom5.quanlylaptop.Activity.PickRole_Activity;
import com.nhom5.quanlylaptop.FragmentNV_Admin.Add_Staff_Fragment;
import com.nhom5.quanlylaptop.FragmentNV_Admin.Admin_ThongBao_Fragment;
import com.nhom5.quanlylaptop.FragmentNV_Admin.NVA_Home_Fragment;
import com.nhom5.quanlylaptop.FragmentQuanLy.QL_DonHang_Fragment;
import com.nhom5.quanlylaptop.FragmentQuanLy.QL_KhachHang_Fragment;
import com.nhom5.quanlylaptop.FragmentQuanLy.QL_Laptop_Fragment;
import com.nhom5.quanlylaptop.FragmentQuanLy.QL_NhanVien_Fragment;
import com.nhom5.quanlylaptop.FragmentQuanLy.QL_ThongKe_Fragment;
import com.nhom5.quanlylaptop.FragmentQuanLy.QL_Voucher_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.nhom5.quanlylaptop.R;

public class Main_Admin_Navi_Activity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NavigationView naviView;
    String TAG = "Main_Admin_Navi_Activity_____";
    DrawerLayout drawerLayout;
    int itemNaviDr;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_navi);
        bottomNavigationView = findViewById(R.id.bottomNavi);
        naviView = findViewById(R.id.naviView);
        drawerLayout = findViewById(R.id.drawerLayout);
        setViewNaviBottom();
        setViewNaviDrawer();
        useToolbar("", 0);

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

    private void layoutAccount(String title) {
        LinearLayout layoutAcc = findViewById(R.id.layout_Account);
        LinearLayout layoutSearch = findViewById(R.id.layout_Search);
        TextView titleView = findViewById(R.id.textView_Title_Toolbar_Acc);
        ImageView imageView = findViewById(R.id.imageView_Avatar);

        layoutAcc.setVisibility(View.VISIBLE);
        layoutSearch.setVisibility(View.GONE);
        titleView.setText(title);
        imageView.setImageResource(R.drawable.admin_avatar);
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
                if (id == R.id.item_navi_drawer_admin_trangChu) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 0 - home");
                    NVA_Home_Fragment nva_home_fragment = new NVA_Home_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, nva_home_fragment).commit();
                    itemNaviDr = 0;
                    useToolbar("", 0);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_home).setCheckable(true);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_home).setChecked(true);
                }
                if (id == R.id.item_navi_drawer_admin_noti) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 1 - fptshop");
                    Admin_ThongBao_Fragment admin_thongBao_fragment = new Admin_ThongBao_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, admin_thongBao_fragment).commit();
                    itemNaviDr = 1;
                    useToolbar("Thông Báo", 0);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_noti).setCheckable(true);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_noti).setChecked(true);
                }
                if (id == R.id.item_navi_drawer_admin_Laptop) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 2 - laptop");
                    QL_Laptop_Fragment ql_laptop_fragment = new QL_Laptop_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, ql_laptop_fragment).commit();
                    itemNaviDr = 3;
                    useToolbar("QLý Laptop", 1);
                    bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_admin_DonHang) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 3 - đơn hàng");
                    QL_DonHang_Fragment ql_donHang_fragment = new QL_DonHang_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, ql_donHang_fragment).commit();
                    itemNaviDr = 4;
                    useToolbar("QLý Đơn Hàng", 0);
                    bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_admin_KhachHang) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 4 - khách hàng");
                    QL_KhachHang_Fragment ql_khachHang_fragment = new QL_KhachHang_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, ql_khachHang_fragment).commit();
                    itemNaviDr = 5;
                    useToolbar("QLý Khách Hàng", 1);
                    bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_admin_Voucher) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 5 - voucher");
                    QL_Voucher_Fragment ql_voucher_fragment = new QL_Voucher_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, ql_voucher_fragment).commit();
                    itemNaviDr = 6;
                    useToolbar("QLý Voucher", 0);
                    bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_admin_NhanVien) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 6 - nhân viên");
                    QL_NhanVien_Fragment ql_nhanVien_fragment = new QL_NhanVien_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, ql_nhanVien_fragment).commit();
                    itemNaviDr = 7;
                    useToolbar("QLý Nhân Viên", 1);
                    bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_admin_ThongKe) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 7 - thống kê");
                    QL_ThongKe_Fragment ql_thongKe_fragment = new QL_ThongKe_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, ql_thongKe_fragment).commit();
                    itemNaviDr = 8;
                    useToolbar("Doanh Thu\nThống Kê", 0);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_thongKe).setCheckable(true);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_thongKe).setChecked(true);
                }
                if (id == R.id.item_navi_drawer_admin_Them_NV) {
                    itemNaviDr = 9;
                    item.setCheckable(true);
                    Add_Staff_Fragment add_staff_fragment  = new Add_Staff_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, add_staff_fragment).commit();
                    Log.d(TAG, "onNavigationItemSelected: itemNavi: " + itemNaviDr);
                    naviView.getMenu().getItem(itemNaviDr).setChecked(false);
                    naviView.getMenu().getItem(itemNaviDr).setCheckable(false);
                    useToolbar("Thêm Nhân Viên", 0);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_add_staff).setCheckable(true);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_add_staff).setChecked(true);
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
    }

    private void setViewNaviBottom() {
        FragmentManager manager = getSupportFragmentManager();
        NVA_Home_Fragment nva_home_fragment = new NVA_Home_Fragment();
        manager.beginTransaction().replace(R.id.frLayout, nva_home_fragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int i = item.getItemId();
                if (i == R.id.item_navi_bottom_admin_home) {
                    NVA_Home_Fragment nva_home_fragment = new NVA_Home_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, nva_home_fragment).commit();
                    naviView.getMenu().getItem(0).setChecked(true);
                    naviView.getMenu().getItem(0).setCheckable(true);
                    itemNaviDr = 0;
                    useToolbar("", 0);
                }
                if (i == R.id.item_navi_bottom_admin_noti) {
                    Admin_ThongBao_Fragment admin_thongBao_fragment = new Admin_ThongBao_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, admin_thongBao_fragment).commit();
                    naviView.getMenu().getItem(1).setChecked(true);
                    naviView.getMenu().getItem(1).setCheckable(true);
                    itemNaviDr = 1;
                    useToolbar("Thông Báo", 0);
                }
                if (i == R.id.item_navi_bottom_admin_thongKe) {
                    QL_ThongKe_Fragment ql_thongKe_fragment = new QL_ThongKe_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, ql_thongKe_fragment).commit();
                    naviView.getMenu().getItem(8).setChecked(true);
                    naviView.getMenu().getItem(8).setCheckable(true);
                    itemNaviDr = 8;
                    useToolbar("Doanh Thu\nThống Kê", 0);
                }
                if (i == R.id.item_navi_bottom_add_staff) {
                    Add_Staff_Fragment add_staff_fragment  = new Add_Staff_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, add_staff_fragment).commit();
                    naviView.getMenu().getItem(9).setChecked(true);
                    naviView.getMenu().getItem(9).setCheckable(true);
                    useToolbar("Thêm Nhân Viên", 0);
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
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_home).setCheckable(true);
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_home).setChecked(true);
            naviView.getMenu().getItem(0).setChecked(true);
            naviView.getMenu().getItem(0).setCheckable(true);
            getSupportActionBar().show();
        }
        if (frag == 1) {
            useToolbar("Thông Báo", 0);
            Admin_ThongBao_Fragment admin_thongBao_fragment = new Admin_ThongBao_Fragment();
            manager.beginTransaction().replace(R.id.frLayout, admin_thongBao_fragment).commit();
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_noti).setCheckable(true);
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_noti).setChecked(true);
            naviView.getMenu().getItem(1).setChecked(true);
            naviView.getMenu().getItem(1).setCheckable(true);
            getSupportActionBar().show();
        }
    }
}