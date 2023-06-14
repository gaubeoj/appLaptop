package com.nhom5.quanlylaptop.KH_Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.quanlylaptop.ActivityKH.KH_ThanhToan_Activity;
import com.nhom5.quanlylaptop.DAO.GioHangDAO;
import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.DAO.VoucherDAO;
import com.nhom5.quanlylaptop.Entity.GioHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.Entity.Voucher;
import com.nhom5.quanlylaptop.FragmentQuanLy.QL_Voucher_Fragment;
import com.nhom5.quanlylaptop.KH_Adapter.KH_ThanhToan_Adapter;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_Voucher_Adapter;
import com.nhom5.quanlylaptop.R;

import java.util.ArrayList;

public class KH_ThanhToan_Loader extends AsyncTask<String, Void, ArrayList<GioHang>> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "KH_ThanhToan_Loader_____";
    GioHangDAO gioHangDAO;
    LaptopDAO laptopDAO;
    VoucherDAO voucherDAO;
    ArrayList<Laptop> listLap = new ArrayList<>();
    ArrayList<Voucher> listVou = new ArrayList<>();
    ArrayList<GioHang> listGio = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;
    @SuppressLint("StaticFieldLeak")
    LinearLayout loadingView;
    @SuppressLint("StaticFieldLeak")
    RelativeLayout relativeLayout;
    Laptop laptop;
    @SuppressLint("StaticFieldLeak")
    KH_ThanhToan_Activity khThanhToanActivity;

    public KH_ThanhToan_Loader(Context context, Laptop laptop, RecyclerView reView,
                               LinearLayout loadingView, RelativeLayout relativeLayout,
                               KH_ThanhToan_Activity khThanhToanActivity) {
        this.context = context;
        this.laptop = laptop;
        this.reView = reView;
        this.loadingView = loadingView;
        this.relativeLayout = relativeLayout;
        this.khThanhToanActivity = khThanhToanActivity;
    }

    @Override
    protected ArrayList<GioHang> doInBackground(String... strings) {
        gioHangDAO = new GioHangDAO(context);
        laptopDAO = new LaptopDAO(context);
        voucherDAO = new VoucherDAO(context);
        String maKH = strings[0];

        if (laptop == null){
            listLap = laptopDAO.selectLaptop(null, null, null, null);
            listGio =  gioHangDAO.selectGioHang(null, "maKH=?", new String[]{maKH}, null);
            listVou = voucherDAO.selectVoucher(null, null, null, null);
        } else {
            listLap.add(laptop);
            listGio.add(new GioHang("No Data", laptop.getMaLaptop(), "No Data", "No Data",
                    "Null", 1));
            listVou = voucherDAO.selectVoucher(null, null, null, null);
        }
        return listGio;
    }

    @Override
    protected void onPostExecute(ArrayList<GioHang> listGio) {
        super.onPostExecute(listGio);

        if (loadingView != null && relativeLayout != null && reView != null){
            loadingView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
            setupReView(listGio, reView);
        }
    }

    private void setupReView(ArrayList<GioHang> listGio, RecyclerView recyclerView) {
        Log.d(TAG, "setUpReView: true");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        KH_ThanhToan_Adapter kh_thanhToan_adapter = new KH_ThanhToan_Adapter(listLap, listGio, listVou, context, khThanhToanActivity);
        recyclerView.setAdapter(kh_thanhToan_adapter);
    }

}
