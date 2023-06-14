package com.nhom5.quanlylaptop.FragmentQuanLy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.nhom5.quanlylaptop.R;

public class QL_ThongKe_Fragment extends Fragment {

    FragmentManager fragmentManager;
    Tab_DoanhThu_Fragment tab_doanhThu_fragment;
    Tab_ThongKe_Fragment tab_thongKe_fragment;
    TabLayout laptopTab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_thong_ke, container, false);
        fragmentManager = getChildFragmentManager();
        tab_doanhThu_fragment = (Tab_DoanhThu_Fragment) fragmentManager.findFragmentById(R.id.doanhThuFrag);
        tab_thongKe_fragment = (Tab_ThongKe_Fragment) fragmentManager.findFragmentById(R.id.thongKeFrag);
        laptopTab = view.findViewById(R.id.thongKeTab);
        thongKeTab();
        return view;
    }

    public void thongKeTab(){
        fragmentManager.beginTransaction().hide(tab_thongKe_fragment).commit();
        laptopTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        fragmentManager.beginTransaction().show(tab_doanhThu_fragment).commit();
                        fragmentManager.beginTransaction().hide(tab_thongKe_fragment).commit();
                        break;
                    case 1:
                        fragmentManager.beginTransaction().show(tab_thongKe_fragment).commit();
                        fragmentManager.beginTransaction().hide(tab_doanhThu_fragment).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}