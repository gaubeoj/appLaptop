package com.nhom5.quanlylaptop.FragmentKH;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nhom5.quanlylaptop.ActivityKH.KH_ThanhToan_Activity;
import com.nhom5.quanlylaptop.DAO.GioHangDAO;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.UseVoucherDAO;
import com.nhom5.quanlylaptop.Entity.GioHang;
import com.nhom5.quanlylaptop.Entity.IdData;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.UseVoucher;
import com.nhom5.quanlylaptop.KH_Loader.KH_GioHang_Loader;
import com.nhom5.quanlylaptop.R;

import java.util.ArrayList;

public class KH_GioHang_Fragment extends Fragment {

    LinearLayout emptyGHLayout;
    RelativeLayout viewGHLayout;
    LinearLayout loadingLayout;
    AppCompatButton buttonPayNow;
    RecyclerView recyclerView;
    String TAG = "KH_GioHang_Fragment_____";
    KhachHang khachHang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kh_gio_hang, container, false);
        emptyGHLayout = view.findViewById(R.id.empty_GioHang);
        viewGHLayout = view.findViewById(R.id.view_Gio_Hang);
        loadingLayout = view.findViewById(R.id.loadingView);
        recyclerView = view.findViewById(R.id.recyclerView_GioHang);
        buttonPayNow = view.findViewById(R.id.button_PayNow);

        getUser();
        if (khachHang != null){
            setLayout();
            KH_GioHang_Loader kh_gioHang_loader = new KH_GioHang_Loader(KH_GioHang_Fragment.this, getContext(), recyclerView, loadingLayout, viewGHLayout);
            kh_gioHang_loader.execute(khachHang.getMaKH());
        }

        buttonPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KH_ThanhToan_Activity.class);
                intent.putExtra("input", "GioHang");
                IdData.getInstance().setIdDC("");
                IdData.getInstance().setIdVou("");
                UseVoucherDAO useVoucherDAO = new UseVoucherDAO(getContext());
                ArrayList<UseVoucher> listUS = useVoucherDAO.selectUseVoucher(null, "maKH=?", new String[]{khachHang.getMaKH()}, null);
                if (listUS.size() > 0) {
                    for (UseVoucher use : listUS) {
                        if (use.getIsUsed().equals("truen't")) {
                            use.setIsUsed("false");
                            useVoucherDAO.updateUseVoucher(use);
                        }
                    }
                }
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUser();
        if (khachHang != null){
            setLayout();
            KH_GioHang_Loader kh_gioHang_loader = new KH_GioHang_Loader(KH_GioHang_Fragment.this, getContext(), recyclerView, loadingLayout, viewGHLayout);
            kh_gioHang_loader.execute(khachHang.getMaKH());
        }
    }

    private void setLayout(){
        GioHangDAO gioHangDAO = new GioHangDAO(getContext());
        ArrayList<GioHang> listGio = gioHangDAO.selectGioHang(null, "maKH=?", new String[]{khachHang.getMaKH()}, null);
        if (listGio != null){
            if (listGio.size() == 0){
                Log.d(TAG, "onCreateView: Giỏ Hàng null");
                viewGHLayout.setVisibility(View.GONE);
                loadingLayout.setVisibility(View.GONE);
                emptyGHLayout.setVisibility(View.VISIBLE);
            } else {
                Log.d(TAG, "onCreateView: Giỏ Hàng not null: " + listGio.size());
                emptyGHLayout.setVisibility(View.GONE);
                loadingLayout.setVisibility(View.VISIBLE);
                viewGHLayout.setVisibility(View.GONE);
            }
        }
    }

    private void getUser(){
        SharedPreferences pref = getContext().getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            khachHang = null;
        } else {
            String user = pref.getString("who", "");
            KhachHangDAO khachHangDAO = new KhachHangDAO(getContext());
            ArrayList<KhachHang> list = khachHangDAO.selectKhachHang(null, "maKH=?", new String[]{user}, null);
            if (list.size() > 0){
                khachHang = list.get(0);
            }
        }
    }
}