package com.nhom5.quanlylaptop.KH_Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.quanlylaptop.ActivityKH.KH_Voucher_Activity;
import com.nhom5.quanlylaptop.DAO.GioHangDAO;
import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.DAO.VoucherDAO;
import com.nhom5.quanlylaptop.Entity.GioHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.Entity.Voucher;
import com.nhom5.quanlylaptop.FragmentQuanLy.QL_Voucher_Fragment;
import com.nhom5.quanlylaptop.KH_Adapter.KH_Voucher_Adapter;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_Voucher_Adapter;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("StaticFieldLeak")
public class KH_Voucher_Loader extends AsyncTask<String, Void, ArrayList<Voucher>> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "KH_Voucher_Loader_____";
    VoucherDAO voucherDAO;
    GioHangDAO gioHangDAO;
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;
    GioHang gioHang;
    String openFrom;
    LinearLayout linearLayout;
    ChangeType changeType = new ChangeType();
    int pos;

    public KH_Voucher_Loader(Context context, RecyclerView reView, LinearLayout linearLayout, String openFrom, int pos) {
        this.context = context;
        this.reView = reView;
        this.linearLayout = linearLayout;
        this.openFrom = openFrom;
        this.pos = pos;
    }

    @Override
    protected ArrayList<Voucher> doInBackground(String... strings) {
        voucherDAO = new VoucherDAO(context);

        String maKH = strings[0];
        if (openFrom.equals("ThanhToan")){
            if (pos != -1){
                gioHangDAO = new GioHangDAO(context);
                ArrayList<GioHang> list = gioHangDAO.selectGioHang(null, "maKH=?", new String[]{maKH}, null);
                gioHang = list.get(pos);
            } else {
                gioHang = new GioHang("Null");
            }
        } else {
            gioHang = null;
        }

        String dateS = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        try {
            String[] time = {String.valueOf(new SimpleDateFormat("yyyy-MM-dd").parse(dateS).getTime()),
                    String.valueOf(new SimpleDateFormat("yyyy-MM-dd").parse(dateS).getTime())};
            return voucherDAO.selectVoucher(null, "ngayKT>? and ngayBD<=?", time, "ngayBD");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Voucher> listVou) {
        super.onPostExecute(listVou);

        if (reView != null && listVou != null && linearLayout != null){
            if (listVou.size() > 0){
                linearLayout.setVisibility(View.GONE);
                setupReView(listVou, reView);
            } else {
                linearLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setupReView(ArrayList<Voucher> listVou, RecyclerView recyclerView) {
        Log.d(TAG, "setUpReView: true");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        KH_Voucher_Adapter kh_voucher_adapter = new KH_Voucher_Adapter(listVou, context, gioHang);
        recyclerView.setAdapter(kh_voucher_adapter);
    }

}
