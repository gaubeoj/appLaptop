package com.nhom5.quanlylaptop.FragmentQuanLy;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.nhom5.quanlylaptop.R;

public class QL_Laptop_Fragment extends Fragment {

    FragmentManager fragmentManager;
    Tab_Laptop_Fragment tab_laptop_fragment;
    Tab_HangLaptop_Fragment tab_hangLaptop_fragment;
    TabLayout laptopTab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_laptop, container, false);
        fragmentManager = getChildFragmentManager();
        tab_laptop_fragment = (Tab_Laptop_Fragment) fragmentManager.findFragmentById(R.id.laptopFrag);
        tab_hangLaptop_fragment = (Tab_HangLaptop_Fragment) fragmentManager.findFragmentById(R.id.hangLaptopFrag);
        laptopTab = view.findViewById(R.id.laptopTab);
        laptopTab();
        return view;
    }

    public void laptopTab(){
        fragmentManager.beginTransaction().hide(tab_hangLaptop_fragment).commit();
        laptopTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        fragmentManager.beginTransaction().show(tab_laptop_fragment).commit();
                        fragmentManager.beginTransaction().hide(tab_hangLaptop_fragment).commit();
                        break;
                    case 1:
                        fragmentManager.beginTransaction().show(tab_hangLaptop_fragment).commit();
                        fragmentManager.beginTransaction().hide(tab_laptop_fragment).commit();
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