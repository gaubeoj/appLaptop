package com.nhom5.quanlylaptop.FragmentKH;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.KH_Adapter.KH_Laptop_Adapter;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class KH_Home_Fragment extends Fragment {

    LaptopDAO laptopDAO;
    KhachHang khachHang;
    ChangeType changeType = new ChangeType();
    ImageView imageView;
    RecyclerView recyclerView;
    ImageButton imageButton;
    EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kh_home, container, false);
        imageView = view.findViewById(R.id.imageView_Home);
        recyclerView = view.findViewById(R.id.recyclerView_Laptop_Search);
        imageButton = view.findViewById(R.id.imageButton_Search);
        editText = view.findViewById(R.id.editText_Search);
        laptopDAO = new LaptopDAO(getContext());
        ArrayList<Laptop> list = laptopDAO.selectLaptop(null, null, null, null);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Laptop> listSearch = new ArrayList<>();
                String input = editText.getText().toString();
                for (Laptop laptop : list) {
                    if (laptop.getTenLaptop().matches(".*?" + input + ".*") && !input.equals("")){
                        listSearch.add(laptop);
                    }
                }
                if (listSearch.size() > 0){
                    imageView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    KH_Laptop_Adapter kh_laptop_adapter = new KH_Laptop_Adapter(listSearch, getContext());
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(kh_laptop_adapter);
                } else {
                    Toast.makeText(getContext(), "Không tìm thấy Laptop nào!", Toast.LENGTH_SHORT).show();
                    imageView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });

        getUser();
        if (khachHang != null) {
            TextView textView = view.findViewById(R.id.textView_TenUser);
            textView.setText("Xin chào, " + changeType.fullNameKhachHang(khachHang));
        }
        return view;
    }

    private void getUser() {
        SharedPreferences pref = getContext().getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            khachHang = null;
        } else {
            String user = pref.getString("who", "");
            KhachHangDAO khachHangDAO = new KhachHangDAO(getContext());
            ArrayList<KhachHang> list = khachHangDAO.selectKhachHang(null, "maKH=?", new String[]{user}, null);
            if (list.size() > 0) {
                khachHang = list.get(0);
            }
        }
    }

}