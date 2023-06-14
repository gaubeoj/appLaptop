package com.nhom5.quanlylaptop.FragmentNV_Admin;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.KH_Loader.All_ThongBao_Loader;
import com.nhom5.quanlylaptop.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NV_ThongBao_Fragment extends Fragment {

    NhanVien nhanVien;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nv_thongbao, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ThongBao);
        LinearLayout linearLayout = view.findViewById(R.id.loadingView);
        LinearLayout emptyLayout = view.findViewById(R.id.empty_ThongBao);
        TextView textView = view.findViewById(R.id.textView_Date_Notifi);
        Date currentTime = Calendar.getInstance().getTime();
        String date = new SimpleDateFormat("dd/MM/yyyy").format(currentTime);
        textView.setText(date);


        getUser();
        if (nhanVien != null) {
            All_ThongBao_Loader all_thongBao_loader = new All_ThongBao_Loader(getContext(),
                    recyclerView, linearLayout, emptyLayout, "nv");
            all_thongBao_loader.execute(nhanVien.getMaNV());
        }
        return view;
    }

    private void getUser() {
        SharedPreferences pref = getContext().getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            nhanVien = null;
        } else {
            String user = pref.getString("who", "");
            NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
            ArrayList<NhanVien> list = nhanVienDAO.selectNhanVien(null, "maNV=?", new String[]{user}, null);
            if (list.size() > 0){
                nhanVien = list.get(0);
            }
        }
    }
}