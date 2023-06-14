package com.nhom5.quanlylaptop.ActivityKH;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom5.quanlylaptop.DAO.DiaChiDAO;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.Entity.DiaChi;
import com.nhom5.quanlylaptop.Entity.IdData;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.KH_Loader.KH_ThanhToan_Loader;
import com.nhom5.quanlylaptop.R;

import java.util.ArrayList;

public class KH_ThanhToan_Activity extends AppCompatActivity {

    TextView changeAddress, changeHTTT, textViewHinhThucTT;
    Laptop laptop;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    LinearLayout linearLayout;
    String TAG = "KH_ThanhToan_Activity_____", input = "";
    Context context = this;
    KhachHang khachHang;
    String maDC;
    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kh_thanh_toan);
        changeAddress = findViewById(R.id.textView_Change_Address);
        changeHTTT = findViewById(R.id.textView_Change_HTTT);
        textViewHinhThucTT = findViewById(R.id.textView_HinhThucTT);
        recyclerView = findViewById(R.id.recyclerView_DonHang);
        relativeLayout = findViewById(R.id.layoutView);
        linearLayout = findViewById(R.id.loadingView);

        getUserKH();
        getInput();
        doiDiaChi();
        doiHTTT();
        useToolbar();
        getSetDiaChi();

        if (khachHang != null){
            KH_ThanhToan_Loader kh_thanhToan_loader = new KH_ThanhToan_Loader(context, laptop, recyclerView, linearLayout, relativeLayout, KH_ThanhToan_Activity.this);
            kh_thanhToan_loader.execute(khachHang.getMaKH());
        }

        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
        if (pref != null) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("what", "none");
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserKH();
        getSetDiaChi();
        if (khachHang != null){
            KH_ThanhToan_Loader kh_thanhToan_loader = new KH_ThanhToan_Loader(context, laptop, recyclerView, linearLayout, relativeLayout, KH_ThanhToan_Activity.this);
            kh_thanhToan_loader.execute(khachHang.getMaKH());
        }

        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
        if (pref != null) {
            String infoWhat = pref.getString("what", "null");
            if (!infoWhat.equals("none")) {
                finish();
            }
        }
    }

    private void getUserKH() {
        SharedPreferences pref = context.getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            khachHang = null;
        } else {
            String user = pref.getString("who", "");
            KhachHangDAO khachHangDAO = new KhachHangDAO(context);
            ArrayList<KhachHang> list = khachHangDAO.selectKhachHang(null, "maKH=?", new String[]{user}, null);
            if (list.size() > 0) {
                khachHang = list.get(0);
            }
        }
    }

    private void doiDiaChi() {
        changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, KH_DiaChi_Activity.class);
                IdData.getInstance().setIdDC(maDC);
                startActivity(intent);
            }
        });
    }

    private void doiHTTT() {
        changeHTTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(KH_ThanhToan_Activity.this);
                builder.setIcon(R.drawable.icon_deal);
                builder.setTitle("Chọn hình thức thanh toán");
                builder.setCancelable(true);
                final String[] arrHTTT = getResources().getStringArray(R.array.httt_array);
                builder.setSingleChoiceItems(arrHTTT, pos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            pos = 0;
                            Toast.makeText(context, "Đã thay đổi hình thức thanh toán", Toast.LENGTH_SHORT).show();
                            textViewHinhThucTT.setText(arrHTTT[i]);
                        } else {
                            pos = 1;
                            Toast.makeText(context, "Đã thay đổi hình thức thanh toán", Toast.LENGTH_SHORT).show();
                            textViewHinhThucTT.setText(arrHTTT[i]);
                        }
                    }
                });

                builder.show();

            }
        });
    }

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_DonHang));
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getInput() {
        Intent intent = getIntent();
        try {
            input = intent.getStringExtra("input");
            if (input != null) {
                if (input.equals("MuaNgay")) {
                    laptop = (Laptop) intent.getExtras().getBinder("laptop");
                } else {
                    laptop = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSetDiaChi() {
        maDC = IdData.getInstance().getIdDC();
        Log.d(TAG, "getSetDiaChi: maDC: " + maDC);

        if (maDC != null) {
            DiaChiDAO diaChiDAO = new DiaChiDAO(context);
            ArrayList<DiaChi> list = diaChiDAO.selectDiaChi(null, "maDC=?", new String[]{maDC}, null);
            if (list.size() > 0) {
                DiaChi diaChi = list.get(0);
                TextView textView = findViewById(R.id.textView_DiaChi);
                textView.setText(diaChi.getTenNguoiNhan() + " - " + diaChi.getSDT() + "\n " + diaChi.getThanhPho()
                        + " - " + diaChi.getQuanHuyen() + " - " + diaChi.getXaPhuong());
            }
        }

    }

}