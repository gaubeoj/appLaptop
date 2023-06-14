package com.nhom5.quanlylaptop.KH_Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.FragmentLaptop.LaptopAcerFragment;
import com.nhom5.quanlylaptop.FragmentLaptop.LaptopAsusFragment;
import com.nhom5.quanlylaptop.FragmentLaptop.LaptopDellFragment;
import com.nhom5.quanlylaptop.FragmentLaptop.LaptopHPFragment;
import com.nhom5.quanlylaptop.FragmentLaptop.LaptopMsiFragment;
import com.nhom5.quanlylaptop.FragmentLaptop.MacBookFragment;
import com.nhom5.quanlylaptop.KH_Adapter.KH_Laptop_Adapter;
import com.nhom5.quanlylaptop.R;
import java.util.ArrayList;

public class KH_Laptop_Loader extends AsyncTask<String, Void, ArrayList<Laptop>> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "LaptopLoader_____";
    LaptopDAO laptopDAO;
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;

    public KH_Laptop_Loader(Context context, RecyclerView reView) {
        this.context = context;
        this.reView = reView;
    }

    @Override
    protected ArrayList<Laptop> doInBackground(String... strings) {

        laptopDAO = new LaptopDAO(context);
        String maHangLap = strings[0];

        return laptopDAO.selectLaptop(null, "maHangLap=?", new String[]{maHangLap}, null);
    }

    @Override
    protected void onPostExecute(ArrayList<Laptop> listLap) {
        super.onPostExecute(listLap);
        laptopDAO = new LaptopDAO(context);

        if (reView != null){
            setupReView(listLap, reView);
        }

    }

    private void setupReView(ArrayList<Laptop> listLap, RecyclerView recyclerView){
        KH_Laptop_Adapter kh_laptop_adapter = new KH_Laptop_Adapter(listLap, context);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(kh_laptop_adapter);
    }

}
