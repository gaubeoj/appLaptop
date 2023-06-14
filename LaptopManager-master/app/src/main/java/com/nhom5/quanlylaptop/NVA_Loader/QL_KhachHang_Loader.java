package com.nhom5.quanlylaptop.NVA_Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.ViTienDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.ViTien;
import com.nhom5.quanlylaptop.FragmentQuanLy.QL_KhachHang_Fragment;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_KhachHang_Adapter;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class QL_KhachHang_Loader extends AsyncTask<String, Void, ArrayList<KhachHang>> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "QL_KhachHang_Loader_____";
    KhachHangDAO khachHangDAO;
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;
    @SuppressLint("StaticFieldLeak")
    TextView countKH;
    @SuppressLint("StaticFieldLeak")
    LinearLayout loadingView, linearEmpty;
    @SuppressLint("StaticFieldLeak")
    RelativeLayout relativeLayout;

    public QL_KhachHang_Loader(Context context, RecyclerView reView, TextView countKH, LinearLayout loadingView, LinearLayout linearEmpty, RelativeLayout relativeLayout) {
        this.countKH = countKH;
        this.reView = reView;
        this.context = context;
        this.loadingView = loadingView;
        this.relativeLayout = relativeLayout;
        this.linearEmpty = linearEmpty;
    }

    @Override
    protected ArrayList<KhachHang> doInBackground(String... strings) {
        khachHangDAO = new KhachHangDAO(context);
        ArrayList<KhachHang> list = khachHangDAO.selectKhachHang(null, null, null, null);
//        if (list != null) {
//            if (list.size() == 0) {
//                addDemoKH();
//            }
//        }

        return khachHangDAO.selectKhachHang(null, null, null, null);
    }

    @Override
    protected void onPostExecute(ArrayList<KhachHang> listKH) {
        super.onPostExecute(listKH);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loadingView != null && relativeLayout != null && linearEmpty != null && reView != null && countKH != null) {
                    if (listKH != null) {
                        if (listKH.size() == 0) {
                            relativeLayout.setVisibility(View.VISIBLE);
                            loadingView.setVisibility(View.GONE);
                            reView.setVisibility(View.GONE);
                            linearEmpty.setVisibility(View.VISIBLE);
                            countKH.setText("0");
                        } else {
                            relativeLayout.setVisibility(View.VISIBLE);
                            loadingView.setVisibility(View.GONE);
                            reView.setVisibility(View.VISIBLE);
                            linearEmpty.setVisibility(View.GONE);
                            setupReView(listKH, reView);
                        }
                    }
                }
            }
        }, 1000);
    }

    private void setupReView(ArrayList<KhachHang> listKH, RecyclerView recyclerView) {
        Log.d(TAG, "setUpReView: true");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        QL_KhachHang_Adapter ql_khachHang_adapter = new QL_KhachHang_Adapter(listKH, context, countKH);
        recyclerView.setAdapter(ql_khachHang_adapter);
        setCountKH(listKH);
    }

    private void setCountKH(ArrayList<KhachHang> listKH) {
        Log.d(TAG, "setCountKH: countKh " + listKH.size());
        countKH.setText(String.valueOf(listKH.size()));
    }

    private void addDemoKH() {
        ChangeType changeType = new ChangeType();
        ViTienDAO viTienDAO = new ViTienDAO(context);

        KhachHang kh0 = new KhachHang("1", "Tom", "Cruise", "Nam", "tom@gmail.com",
                "tom1234", "Hoa Kỳ", "003071962", "true",
                changeType.checkByteInput(changeType.bitmapToByte(BitmapFactory.decodeResource(context.getResources(), R.drawable.tom_cruise))));
        khachHangDAO.insertKhachHang(kh0);
        ViTien vi1 = new ViTien("1", "1", changeType.stringToStringMoney("62000000"), "MBBank");
        viTienDAO.insertViTien(vi1);

        KhachHang kh3 = new KhachHang("2", "Nguyễn", "Thanh Tùng", "Nam", "tung@gmail.com",
                "sontung", "Việt Nam", "05071994", "true",
                changeType.checkByteInput(changeType.bitmapToByte(BitmapFactory.decodeResource(context.getResources(), R.drawable.son_tung))));
        khachHangDAO.insertKhachHang(kh3);
        ViTien vi4 = new ViTien("2", "2", changeType.stringToStringMoney("94000000"), "MBBank");
        viTienDAO.insertViTien(vi4);

        KhachHang kh4 = new KhachHang("3", "Nguyễn", "Xuân Bắc", "Nam", "xb@gmail.com",
                "xuanbac", "Việt Nam", "021081976", "false",
                changeType.checkByteInput(changeType.bitmapToByte(BitmapFactory.decodeResource(context.getResources(), R.drawable.xuan_bac))));
        khachHangDAO.insertKhachHang(kh4);
    }

}

