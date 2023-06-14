package com.nhom5.quanlylaptop.KH_Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.quanlylaptop.ActivityKH.KH_DiaChi_Activity;
import com.nhom5.quanlylaptop.ActivityKH.KH_Voucher_Activity;
import com.nhom5.quanlylaptop.DAO.DiaChiDAO;
import com.nhom5.quanlylaptop.DAO.VoucherDAO;
import com.nhom5.quanlylaptop.Entity.DiaChi;
import com.nhom5.quanlylaptop.Entity.Voucher;
import com.nhom5.quanlylaptop.KH_Adapter.KH_DiaChi_Adapter;
import com.nhom5.quanlylaptop.KH_Adapter.KH_Voucher_Adapter;
import com.nhom5.quanlylaptop.R;

import java.util.ArrayList;

public class KH_DiaChi_Loader extends AsyncTask<String, Void, ArrayList<DiaChi>> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "KH_DiaChi_Loader_____";
    DiaChiDAO diaChiDAO;
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;
    public KH_DiaChi_Loader(Context context, RecyclerView reView) {
        this.context = context;
        this.reView = reView;
    }

    @Override
    protected ArrayList<DiaChi> doInBackground(String... strings) {
        diaChiDAO = new DiaChiDAO(context);
        String maKH = strings[0];

        return diaChiDAO.selectDiaChi(null, "maKH=?", new String[]{maKH}, null);
    }

    @Override
    protected void onPostExecute(ArrayList<DiaChi> listVou) {
        super.onPostExecute(listVou);

        if (reView != null) {
            setupReView(listVou, reView);
        }
    }

    private void setupReView(ArrayList<DiaChi> listDC, RecyclerView recyclerView) {
        Log.d(TAG, "setUpReView: true");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        KH_DiaChi_Adapter kh_diaChi_adapter = new KH_DiaChi_Adapter(listDC, context);
        recyclerView.setAdapter(kh_diaChi_adapter);
    }

}