package com.nhom5.quanlylaptop.KH_Loader;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.quanlylaptop.DAO.GioHangDAO;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.DAO.VoucherDAO;
import com.nhom5.quanlylaptop.Entity.GioHang;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.Entity.Voucher;
import com.nhom5.quanlylaptop.FragmentKH.KH_GioHang_Fragment;
import com.nhom5.quanlylaptop.FragmentQuanLy.QL_Voucher_Fragment;
import com.nhom5.quanlylaptop.KH_Adapter.KH_GioHang_Adapter;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_Voucher_Adapter;
import com.nhom5.quanlylaptop.R;

import java.util.ArrayList;

public class KH_GioHang_Loader extends AsyncTask<String, Void, ArrayList<GioHang>> {
    @SuppressLint("StaticFieldLeak")
    KH_GioHang_Fragment khGioHangFragment;
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "KH_GioHang_Loader_____";
    LaptopDAO laptopDAO;
    GioHangDAO gioHangDAO;
    ArrayList<Laptop> listLap = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;
    @SuppressLint("StaticFieldLeak")
    LinearLayout loadingView;
    @SuppressLint("StaticFieldLeak")
    RelativeLayout relativeLayout;

    public KH_GioHang_Loader(KH_GioHang_Fragment khGioHangFragment, Context context, RecyclerView reView, LinearLayout loadingView, RelativeLayout relativeLayout) {
        this.khGioHangFragment = khGioHangFragment;
        this.context = context;
        this.reView = reView;
        this.loadingView = loadingView;
        this.relativeLayout = relativeLayout;
    }

    @Override
    protected ArrayList<GioHang> doInBackground(String... strings) {
        laptopDAO = new LaptopDAO(context);
        gioHangDAO = new GioHangDAO(context);
        String maKH = strings[0];

        listLap = laptopDAO.selectLaptop(null, null, null, null);
        ArrayList<GioHang> list = gioHangDAO.selectGioHang(null, "maKH=?", new String[]{maKH}, null);
        for (int i = 0; i < list.size(); i++){
            GioHang gioHang = list.get(i);
            GioHang resetGH = new GioHang(gioHang.getMaGio(), gioHang.getMaLaptop(), gioHang.getMaKH(),
                    gioHang.getNgayThem(), "Null", gioHang.getSoLuong());
            gioHangDAO.updateGioHang(resetGH);
        }

        return gioHangDAO.selectGioHang(null, "maKH=?", new String[]{maKH}, null);
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
        KH_GioHang_Adapter kh_gioHang_adapter = new KH_GioHang_Adapter(listLap, listGio, context, khGioHangFragment);
        recyclerView.setAdapter(kh_gioHang_adapter);
    }
}
