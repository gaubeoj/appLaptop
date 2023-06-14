package com.nhom5.quanlylaptop.NVA_Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.DAO.ViTienDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.Entity.ViTien;
import com.nhom5.quanlylaptop.FragmentQuanLy.QL_NhanVien_Fragment;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_NhanVien_Adapter;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class QL_NhanVien_Loader extends AsyncTask<String, Void, ArrayList<NhanVien>> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "QL_NhanVien_Loader_____";
    NhanVienDAO nhanVienDAO;
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;
    @SuppressLint("StaticFieldLeak")
    TextView countNV;
    @SuppressLint("StaticFieldLeak")
    LinearLayout loadingView, linearEmpty;
    @SuppressLint("StaticFieldLeak")
    RelativeLayout relativeLayout;
    String roleNV;

    public QL_NhanVien_Loader(Context context, RecyclerView reView, TextView countNV, LinearLayout loadingView, LinearLayout linearEmpty, RelativeLayout relativeLayout) {
        this.context = context;
        this.reView = reView;
        this.countNV = countNV;
        this.loadingView = loadingView;
        this.linearEmpty = linearEmpty;
        this.relativeLayout = relativeLayout;
    }

    @Override
    protected ArrayList<NhanVien> doInBackground(String... strings) {
        nhanVienDAO = new NhanVienDAO(context);
        ArrayList<KhachHang> list = nhanVienDAO.selectNhanVien(null, null, null, null);
        if (list != null) {
            if (list.size() == 0) {
                addDemoNV();
            }
        }

        String role = strings[0];
        if ("all".equals(role)) {
            roleNV = "all";
            return nhanVienDAO.selectNhanVien(null, null, null, null);
        } else if ("saleol".equals(role)) {
            roleNV = "saleol";
            return nhanVienDAO.selectNhanVien(null, "roleNV=?", new String[]{"Bán hàng Online"}, null);
        } else if ("saleof".equals(role)) {
            roleNV = "saleof";
            return nhanVienDAO.selectNhanVien(null, "roleNV=?", new String[]{"Bán hàng Ofline"}, null);
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<NhanVien> listNV) {
        super.onPostExecute(listNV);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loadingView != null && relativeLayout != null && linearEmpty != null && reView != null && countNV != null) {
                    if (listNV != null) {
                        if (listNV.size() == 0) {
                            relativeLayout.setVisibility(View.VISIBLE);
                            loadingView.setVisibility(View.GONE);
                            reView.setVisibility(View.GONE);
                            linearEmpty.setVisibility(View.VISIBLE);
                            countNV.setText(String.valueOf(0));
                        } else {
                            relativeLayout.setVisibility(View.VISIBLE);
                            loadingView.setVisibility(View.GONE);
                            reView.setVisibility(View.VISIBLE);
                            linearEmpty.setVisibility(View.GONE);
                            setupReView(listNV, reView);
                        }
                    }
                }
            }
        }, 1000);
    }

    private void setupReView(ArrayList<NhanVien> listNV, RecyclerView recyclerView) {
        Log.d(TAG, "setUpReView: true");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        QL_NhanVien_Adapter ql_nhanVien_adapter = new QL_NhanVien_Adapter(listNV, context, countNV);
        recyclerView.setAdapter(ql_nhanVien_adapter);
        setCountKH(listNV);
    }

    private void setCountKH(ArrayList<NhanVien> listNV) {
        countNV.setText(String.valueOf(listNV.size()));
    }

    private void addDemoNV() {
        ChangeType changeType = new ChangeType();

        NhanVien nv0 = new NhanVien("0", "Leonardo", "DiCaprio", "Nam", "leo@gmail.com",
                "leonardo", "Hoa Kỳ", "011111974", "Bán hàng Online", 0, 0,
                changeType.checkByteInput(changeType.bitmapToByte(BitmapFactory.decodeResource(context.getResources(), R.drawable.leonardo_dicaprio))));
        nhanVienDAO.insertNhanVien(nv0);

        NhanVien nv1 = new NhanVien("1", "Hoàng", "Thái Vũ", "Nam", "vu@gmail.com",
                "vu1995", "Việt Nam", "003101995", "Bán hàng Online", 0, 0,
                changeType.checkByteInput(changeType.bitmapToByte(BitmapFactory.decodeResource(context.getResources(), R.drawable.vu))));
        nhanVienDAO.insertNhanVien(nv1);

        NhanVien nv4 = new NhanVien("4", "Nguyễn", "Công Lý", "Nam", "congly@gmail.com",
                "congly", "Việt Nam", "016101973", "Bán hàng Ofline", 0, 0,
                changeType.checkByteInput(changeType.bitmapToByte(BitmapFactory.decodeResource(context.getResources(), R.drawable.cong_ly))));
        nhanVienDAO.insertNhanVien(nv4);
    }
}

