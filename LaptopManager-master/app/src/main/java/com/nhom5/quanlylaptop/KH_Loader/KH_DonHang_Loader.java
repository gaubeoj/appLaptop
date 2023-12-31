package com.nhom5.quanlylaptop.KH_Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.quanlylaptop.ActivityKH.KH_DonHang_Activity;
import com.nhom5.quanlylaptop.ActivityNV_Admin.NV_DonHang_Activity;
import com.nhom5.quanlylaptop.DAO.DonHangDAO;
import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.Entity.DonHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.KH_Adapter.KH_DonHang_Adapter;
import com.nhom5.quanlylaptop.NAV_Adapter.NV_DonHang_Adapter;
import com.nhom5.quanlylaptop.R;

import java.util.ArrayList;

public class KH_DonHang_Loader extends AsyncTask<String, Void, ArrayList<DonHang>> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "NV_DonHang_Loader_____";
    LaptopDAO laptopDAO;
    DonHangDAO donHangDAO;
    ArrayList<Laptop> listLap = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;
    @SuppressLint("StaticFieldLeak")
    LinearLayout loadingView, linearEmpty;
    String type;

    public KH_DonHang_Loader(Context context, RecyclerView reView, LinearLayout loadingView, LinearLayout linearEmpty, String type) {
        this.context = context;
        this.reView = reView;
        this.loadingView = loadingView;
        this.linearEmpty = linearEmpty;
        this.type = type;
    }

    @Override
    protected ArrayList<DonHang> doInBackground(String... strings) {
        laptopDAO = new LaptopDAO(context);
        donHangDAO = new DonHangDAO(context);
        String maKH = strings[0];
        listLap = laptopDAO.selectLaptop(null, null, null, null);

        if (type.equals("yep")){
            return donHangDAO.selectDonHang(null, "maKH=? and trangThai!=?", new String[]{maKH, "Chưa thanh toán"}, "ngayMua");
        }
        if (type.equals("none")){
            return donHangDAO.selectDonHang(null, "maKH=? and trangThai=?", new String[]{maKH, "Chưa thanh toán"}, "ngayMua");
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<DonHang> listDon) {
        super.onPostExecute(listDon);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loadingView != null && linearEmpty != null && reView != null) {
                    if (listDon != null) {
                        if (listDon.size() == 0) {
                            loadingView.setVisibility(View.GONE);
                            reView.setVisibility(View.GONE);
                            linearEmpty.setVisibility(View.VISIBLE);
                        } else {
                            loadingView.setVisibility(View.GONE);
                            reView.setVisibility(View.VISIBLE);
                            linearEmpty.setVisibility(View.GONE);
                            setupReView(listDon, reView);
                        }
                    }
                }
            }
        }, 1000);
    }

    private void setupReView(ArrayList<DonHang> listDon, RecyclerView recyclerView) {
        Log.d(TAG, "setUpReView: true");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        KH_DonHang_Adapter kh_donHang_adapter = new KH_DonHang_Adapter(listLap, listDon, context);
        recyclerView.setAdapter(kh_donHang_adapter);
    }

}
