package com.nhom5.quanlylaptop.ActivityKH;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.nhom5.quanlylaptop.Activity.LienHe_Activity;
import com.nhom5.quanlylaptop.Activity.PickRole_Activity;
import com.nhom5.quanlylaptop.CreateWallet.TaoVi_Activity;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.FragmentKH.KH_Account_Fragment;
import com.nhom5.quanlylaptop.FragmentKH.KH_GioHang_Fragment;
import com.nhom5.quanlylaptop.FragmentKH.KH_Home_Fragment;
import com.nhom5.quanlylaptop.FragmentKH.KH_ThongBao_Fragment;
import com.nhom5.quanlylaptop.FragmentLaptop.LaptopAcerFragment;
import com.nhom5.quanlylaptop.FragmentLaptop.LaptopAsusFragment;
import com.nhom5.quanlylaptop.FragmentLaptop.LaptopDellFragment;
import com.nhom5.quanlylaptop.FragmentLaptop.LaptopHPFragment;
import com.nhom5.quanlylaptop.FragmentLaptop.LaptopMsiFragment;
import com.nhom5.quanlylaptop.FragmentLaptop.MacBookFragment;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class Main_KH_Navi_Activity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NavigationView naviView;
    String TAG = "Main_KH_Navi_Activity_____";
    DrawerLayout drawerLayout;
    int itemNaviDr;
    Context context = this;
    KhachHang khachHang;
    ChangeType changeType = new ChangeType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_kh_navi);
        bottomNavigationView = findViewById(R.id.bottomNavi);
        naviView = findViewById(R.id.naviView);
        drawerLayout = findViewById(R.id.drawerLayout);

        getUser();
        useToolbar("");
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
            if (infoWhat.equals("gio")) {
                setOnResumeNavi(8);
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

    private void useToolbar(String title) {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Account));
        ImageButton open = findViewById(R.id.imageButton_Open_Drawer);
        ImageView imageView = findViewById(R.id.imageView_Avatar);
        TextView titleView = findViewById(R.id.textView_Title_Toolbar_Acc);
        titleView.setText(title);
        getUser();
        if (khachHang != null) {
            imageView.setImageBitmap(changeType.byteToBitmap(khachHang.getAvatar()));
        }
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

    private void setViewNaviDrawer() {
        naviView.getMenu().getItem(0).setChecked(true);
        naviView.getMenu().getItem(0).setCheckable(true);
        naviView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                FragmentManager manager = getSupportFragmentManager();
                if (id == R.id.item_navi_drawer_kh_trangChu) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 0 - home");
                    KH_Home_Fragment kh_home_fragment = new KH_Home_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, kh_home_fragment).commit();
                    itemNaviDr = 0;
                    useToolbar("");
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_kh_home).setCheckable(true);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_kh_home).setChecked(true);
                }
                if (id == R.id.item_navi_drawer_kh_thongBao) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 1 - thông báo");
                    KH_ThongBao_Fragment kh_thongBao_fragment = new KH_ThongBao_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, kh_thongBao_fragment).commit();
                    itemNaviDr = 1;
                    useToolbar("Thông Báo");
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_kh_noti).setCheckable(true);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_kh_noti).setChecked(true);
                }
                if (id == R.id.item_navi_drawer_kh_lTDell) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 2 - laptop dell");
                    LaptopDellFragment laptopDellFragment = new LaptopDellFragment();
                    manager.beginTransaction().replace(R.id.frLayout, laptopDellFragment).commit();
                    itemNaviDr = 2;
                    useToolbar("");
                    bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_kh_lTHP) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 3 - laptop hp");
                    LaptopHPFragment laptopHPFragment = new LaptopHPFragment();
                    manager.beginTransaction().replace(R.id.frLayout, laptopHPFragment).commit();
                    itemNaviDr = 4;
                    useToolbar("");
                    bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_kh_lTAsus) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 4 - laptop asus");
                    LaptopAsusFragment laptopAsusFragment = new LaptopAsusFragment();
                    manager.beginTransaction().replace(R.id.frLayout, laptopAsusFragment).commit();
                    itemNaviDr = 5;
                    useToolbar("");
                    bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_kh_lTAcer) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 5 - laptop acer");
                    LaptopAcerFragment laptopAcerFragment = new LaptopAcerFragment();
                    manager.beginTransaction().replace(R.id.frLayout, laptopAcerFragment).commit();
                    itemNaviDr = 6;
                    useToolbar("");
                    bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_kh_lTMsi) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 6 - laptop msi");
                    LaptopMsiFragment laptopMsiFragment = new LaptopMsiFragment();
                    manager.beginTransaction().replace(R.id.frLayout, laptopMsiFragment).commit();
                    itemNaviDr = 7;
                    useToolbar("");
                    bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_kh_mac) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 6 - laptop samsung");
                    MacBookFragment macBookFragment = new MacBookFragment();
                    manager.beginTransaction().replace(R.id.frLayout, macBookFragment).commit();
                    itemNaviDr = 8;
                    useToolbar("");
                    bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_kh_gioHang) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 7 - giỏ hàng");
                    KH_GioHang_Fragment kh_gioHang_fragment = new KH_GioHang_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, kh_gioHang_fragment).commit();
                    itemNaviDr = 9;
                    useToolbar("Giỏ Hàng");
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_kh_gioHang).setCheckable(true);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_kh_gioHang).setChecked(true);
                }
                if (id == R.id.item_navi_drawer_kh_DH_Damua) {
                    item.setCheckable(true);
                    itemNaviDr = 10;
                    Log.d(TAG, "onNavigationItemSelected: 8 - dh đã mua");
                    startActivity(new Intent(context, KH_DonHang_Activity.class));
                }
                if (id == R.id.item_navi_drawer_kh_vi) {
                    item.setCheckable(true);
                    itemNaviDr = 11;
                    if (khachHang != null) {
                        Intent intent;
                        if (khachHang.getHaveVi().equals("false")) {
                            intent = new Intent(context, TaoVi_Activity.class);
                        } else {
                            intent = new Intent(context, KH_ViTien_Activity.class);
                        }
                        startActivity(intent);
                    }
                }
                if (id == R.id.item_navi_drawer_kh_lienHe) {
                    itemNaviDr = 12;
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 8 - liên hệ");
                    startActivity(new Intent(context, LienHe_Activity.class));
                }
                if (id == R.id.item_navi_drawer_admin_Logout) {
                    itemNaviDr = 13;
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
        FragmentManager manager = getSupportFragmentManager();
        KH_Home_Fragment kh_home_fragment = new KH_Home_Fragment();
        manager.beginTransaction().replace(R.id.frLayout, kh_home_fragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int i = item.getItemId();
                if (i == R.id.item_navi_bottom_kh_home) {
                    KH_Home_Fragment kh_home_fragment = new KH_Home_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, kh_home_fragment).commit();
                    naviView.getMenu().getItem(0).setChecked(true);
                    naviView.getMenu().getItem(0).setCheckable(true);
                    itemNaviDr = 0;
                    useToolbar("");
                    getSupportActionBar().show();
                }
                if (i == R.id.item_navi_bottom_kh_noti) {
                    KH_ThongBao_Fragment kh_thongBao_fragment = new KH_ThongBao_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, kh_thongBao_fragment).commit();
                    naviView.getMenu().getItem(1).setChecked(true);
                    naviView.getMenu().getItem(1).setCheckable(true);
                    itemNaviDr = 1;
                    useToolbar("Thông Báo");
                    getSupportActionBar().show();
                }
                if (i == R.id.item_navi_bottom_kh_gioHang) {
                    KH_GioHang_Fragment kh_gioHang_fragment = new KH_GioHang_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, kh_gioHang_fragment).commit();
                    naviView.getMenu().getItem(9).setChecked(true);
                    naviView.getMenu().getItem(9).setCheckable(true);
                    itemNaviDr = 9;
                    useToolbar("Giỏ Hàng");
                    getSupportActionBar().show();
                }
                if (i == R.id.item_navi_bottom_kh_acc) {
                    KH_Account_Fragment kh_account_fragment = new KH_Account_Fragment();
                    manager.beginTransaction().replace(R.id.frLayout, kh_account_fragment).commit();
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
            useToolbar("");
            KH_Home_Fragment kh_home_fragment = new KH_Home_Fragment();
            manager.beginTransaction().replace(R.id.frLayout, kh_home_fragment).commit();
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_kh_home).setCheckable(true);
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_kh_home).setChecked(true);
            naviView.getMenu().getItem(0).setChecked(true);
            naviView.getMenu().getItem(0).setCheckable(true);
            getSupportActionBar().show();
        }
        if (frag == 1) {
            useToolbar("Thông Báo");
            KH_ThongBao_Fragment kh_thongBao_fragment = new KH_ThongBao_Fragment();
            manager.beginTransaction().replace(R.id.frLayout, kh_thongBao_fragment).commit();
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_kh_noti).setCheckable(true);
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_kh_noti).setChecked(true);
            naviView.getMenu().getItem(1).setChecked(true);
            naviView.getMenu().getItem(1).setCheckable(true);
            getSupportActionBar().show();
        }
        if (frag == 8) {
            useToolbar("Giỏ Hàng");
            KH_GioHang_Fragment kh_gioHang_fragment = new KH_GioHang_Fragment();
            manager.beginTransaction().replace(R.id.frLayout, kh_gioHang_fragment).commit();
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_kh_gioHang).setCheckable(true);
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_kh_gioHang).setChecked(true);
            naviView.getMenu().getItem(9).setChecked(true);
            naviView.getMenu().getItem(9).setCheckable(true);
            getSupportActionBar().show();
        }
    }
}