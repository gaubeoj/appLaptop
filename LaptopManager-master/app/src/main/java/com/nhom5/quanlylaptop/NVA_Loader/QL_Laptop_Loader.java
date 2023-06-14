package com.nhom5.quanlylaptop.NVA_Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.quanlylaptop.DAO.HangLaptopDAO;
import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.Entity.HangLaptop;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.FragmentQuanLy.Tab_Laptop_Fragment;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_Laptop_Adapter;
import com.nhom5.quanlylaptop.Support.GetData;

import java.util.ArrayList;

public class QL_Laptop_Loader extends AsyncTask<String, Void, ArrayList<Laptop>> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "LaptopLoader_____";
    LaptopDAO laptopDAO;
    ArrayList<HangLaptop> listHang = new ArrayList<>();
    HangLaptopDAO hangLaptopDAO;
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;
    @SuppressLint("StaticFieldLeak")
    LinearLayout loadingView, linearEmpty;
    @SuppressLint("StaticFieldLeak")
    RelativeLayout relativeLayout, layoutLaptop;
    Tab_Laptop_Fragment tab_laptop_fragment;

    public QL_Laptop_Loader(Context context, RecyclerView reView, LinearLayout loadingView, LinearLayout linearEmpty, RelativeLayout relativeLayout, RelativeLayout layoutLaptop, Tab_Laptop_Fragment tab_laptop_fragment) {
        this.context = context;
        this.reView = reView;
        this.loadingView = loadingView;
        this.linearEmpty = linearEmpty;
        this.relativeLayout = relativeLayout;
        this.tab_laptop_fragment = tab_laptop_fragment;
        this.layoutLaptop = layoutLaptop;
    }

    @Override
    protected ArrayList<Laptop> doInBackground(String... strings) {
        laptopDAO = new LaptopDAO(context);
        hangLaptopDAO = new HangLaptopDAO(context);
        String hangL = strings[0];
        ArrayList<Laptop> list = laptopDAO.selectLaptop(null, null, null, "maHangLap");
        if (list != null) {
            if (list.size() == 0) {
                GetData getData = new GetData(context);
                getData.addDemoLaptopDell();
                getData.addDemoLaptopHP();
                getData.addDemoLaptopAcer();
                getData.addDemoLaptopAsus();
                getData.addDemoLaptopMsi();
                getData.addDemoLaptopMac();
            }
        }

        if ("all".equals(hangL)) {
            return laptopDAO.selectLaptop(null, null, null, "maHangLap");
        } else {
            return laptopDAO.selectLaptop(null, "maHangLap=?", new String[]{hangL}, "maHangLap");
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Laptop> listLap) {
        super.onPostExecute(listLap);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loadingView != null && relativeLayout != null && linearEmpty != null && reView != null) {
                    if (listLap != null) {
                        if (listLap.size() == 0) {
                            relativeLayout.setVisibility(View.VISIBLE);
                            loadingView.setVisibility(View.GONE);
                            reView.setVisibility(View.GONE);
                            layoutLaptop.setVisibility(View.GONE);
                            linearEmpty.setVisibility(View.VISIBLE);
                        } else {
                            relativeLayout.setVisibility(View.VISIBLE);
                            loadingView.setVisibility(View.GONE);
                            reView.setVisibility(View.VISIBLE);
                            linearEmpty.setVisibility(View.GONE);
                            layoutLaptop.setVisibility(View.VISIBLE);
                            setupReView(listLap, reView);
                        }
                    }
                }
            }
        }, 1000);
    }

    private void setupReView(ArrayList<Laptop> listLap, RecyclerView recyclerView) {
        listHang = hangLaptopDAO.selectHangLaptop(null, null, null, null);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        QL_Laptop_Adapter ql_laptop_adapter = new QL_Laptop_Adapter(listLap, listHang, context, tab_laptop_fragment);
        recyclerView.setAdapter(ql_laptop_adapter);
    }

}
