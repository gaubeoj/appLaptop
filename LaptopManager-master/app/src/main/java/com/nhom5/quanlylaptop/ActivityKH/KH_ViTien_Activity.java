package com.nhom5.quanlylaptop.ActivityKH;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom5.quanlylaptop.DAO.GiaoDichDAO;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.ThongBaoDAO;
import com.nhom5.quanlylaptop.DAO.ViTienDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.ThongBao;
import com.nhom5.quanlylaptop.Entity.ViTien;
import com.nhom5.quanlylaptop.KH_Loader.KH_GiaoDich_Loader;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class KH_ViTien_Activity extends AppCompatActivity {

    ArrayList<ViTien> listVi = new ArrayList<>();
    ArrayList<ViTien> listGD = new ArrayList<>();
    ViTienDAO viTienDAO;
    GiaoDichDAO giaoDichDAO;
    Context context = this;
    ChangeType changeType = new ChangeType();
    LinearLayout linearLayout;
    ViTien viTien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kh_vi_tien);
        linearLayout = findViewById(R.id.linearGiaoDichEmpty);
        viTienDAO = new ViTienDAO(context);
        giaoDichDAO = new GiaoDichDAO(context);
        listGD = giaoDichDAO.selectGiaoDich(null, null, null, null);

        getUser();
        if (viTien != null) {
            KH_GiaoDich_Loader kh_giaoDich_loader = new KH_GiaoDich_Loader(context, findViewById(R.id.recyclerView_GiaoDich), linearLayout);
            kh_giaoDich_loader.execute(viTien.getMaVi());
        }

        useToolbar();
        clickThanhToan();
        clickNapTien();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (viTien != null) {
            KH_GiaoDich_Loader kh_giaoDich_loader = new KH_GiaoDich_Loader(context, findViewById(R.id.recyclerView_GiaoDich), linearLayout);
            kh_giaoDich_loader.execute(viTien.getMaVi());
        }
    }

    private void getUser() {
        SharedPreferences pref = getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            viTien = null;
        } else {
            String user = pref.getString("who", "");
            listVi = viTienDAO.selectViTien(null, "maKH=?", new String[]{user}, null);
            if (listVi.size() > 0) {
                viTien = listVi.get(0);
            }
        }
    }

    private void clickThanhToan() {
        LinearLayout thanhToan = findViewById(R.id.onclick_ThanhToan);
        thanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, KH_DonHang_CTT_Activity.class));
            }
        });
    }

    private void clickNapTien() {
        LinearLayout napTien = findViewById(R.id.onclick_NapTien);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inft = ((Activity) context).getLayoutInflater();
        View view = inft.inflate(R.layout.dialog_naptien, null);
        AppCompatButton button = view.findViewById(R.id.button_Dialog);
        EditText editText_NapTien = view.findViewById(R.id.editText_NapTien);
        TextView textView_SoDu = view.findViewById(R.id.textView_SoDu);

        builder.setView(view);
        Dialog dialog = builder.create();
        napTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viTien != null){
                    textView_SoDu.setText(viTien.getSoTien());
                }
                dialog.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ed_Naptien = changeType.stringToStringMoney(editText_NapTien.getText().toString());
                        Date currentTime = Calendar.getInstance().getTime();
                        String date = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
                        KhachHangDAO khachHangDAO = new KhachHangDAO(context);
                        ThongBaoDAO thongBaoDAO = new ThongBaoDAO(context);
                        KhachHang khachHang = null;

                        ArrayList<KhachHang> listKH = khachHangDAO.selectKhachHang(null, "maKH=?", new String[]{viTien.getMaKH()}, null);
                        if (listKH.size() > 0){
                            khachHang = listKH.get(0);
                        }

                        if (!ed_Naptien.equals("0₫") && !ed_Naptien.equals("₫")){
                            if (ed_Naptien.length() < 12){
                                if (changeType.stringMoneyToInt(ed_Naptien) <= 50000000){
                                    ThongBao thongBaoKH = new ThongBao("TB", viTien.getMaKH(), "Nạp tiền",
                                            " Bạn đã gửi yêu cầu nạp " + ed_Naptien + " vào ví FPT Pay. Vui lòng chờ Admin phê duyệt", date);
                                    thongBaoDAO.insertThongBao(thongBaoKH, "kh");
                                    Toast.makeText(context, " Bạn đã gửi yêu cầu nạp " + ed_Naptien + " vào ví FPT Pay.\n Vui lòng chờ Admin phê duyệt!", Toast.LENGTH_SHORT).show();

                                    if (khachHang != null){
                                        ThongBao thongBaoAD = new ThongBao("TB", viTien.getMaKH(), "Yêu cầu nạp tiền " + viTien.getMaKH(),
                                                " Khách hàng " + changeType.fullNameKhachHang(khachHang) + " muốn nạp " + ed_Naptien + " vào ví FPT Pay.\n Phê duyệt ngay!", date);
                                        thongBaoDAO.insertThongBao(thongBaoAD, "ad");
                                    } else {
                                        ThongBao thongBaoAD = new ThongBao("TB", viTien.getMaKH(), "Yêu cầu nạp tiền " + viTien.getMaKH(),
                                                " Khách hàng No Data muốn nạp " + ed_Naptien + " vào ví FPT Pay.\n Phê duyệt ngay!", date);
                                        thongBaoDAO.insertThongBao(thongBaoAD, "ad");
                                    }
                                } else {
                                    Toast.makeText(KH_ViTien_Activity.this, "Số tiền nạp phải lớn hơn 0\nSố tiền nạp không quá 50 triệu", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(KH_ViTien_Activity.this, "Số tiền nạp phải lớn hơn 0\nSố tiền nạp không quá 50 triệu", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(KH_ViTien_Activity.this, "Số tiền nạp phải lớn hơn 0\nSố tiền nạp không quá 50 triệu", Toast.LENGTH_SHORT).show();
                        }

                        dialog.cancel();
                    }
                });
            }
        });
    }

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_ViTien));
        TextView soTien = findViewById(R.id.textView_SoTien);
        if (viTien != null) {
            soTien.setText("Số dư: " + viTien.getSoTien());
        }
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}