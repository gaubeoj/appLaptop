package com.nhom5.quanlylaptop.NAV_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.quanlylaptop.Activity.ChiTiet_DonHang_Activity;
import com.nhom5.quanlylaptop.ActivityKH.KH_DanhGia_Activity;
import com.nhom5.quanlylaptop.ActivityNV_Admin.NV_DanhGia_Activity;
import com.nhom5.quanlylaptop.DAO.LaptopRateDAO;
import com.nhom5.quanlylaptop.Entity.DonHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.Entity.LaptopRate;
import com.nhom5.quanlylaptop.KH_Adapter.KH_DonHang_Adapter;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class NV_DonHang_Adapter extends RecyclerView.Adapter<NV_DonHang_Adapter.AuthorViewHolder> {

    Context context;
    ArrayList<Laptop> listLap;
    ArrayList<DonHang> listDon;
    LaptopRateDAO laptopRateDAO;
    ChangeType changeType = new ChangeType();
    String TAG = "NV_DonHang_Adapter_____";

    public NV_DonHang_Adapter(ArrayList<Laptop> listLap, ArrayList<DonHang> listDon, Context context) {
        this.listLap = listLap;
        this.listDon = listDon;
        this.context = context;
        laptopRateDAO = new LaptopRateDAO(context);
    }

    @NonNull
    @Override
    public NV_DonHang_Adapter.AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup vGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_nv_donhang_laptop, vGroup, false);
        return new NV_DonHang_Adapter.AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NV_DonHang_Adapter.AuthorViewHolder author, @SuppressLint("RecyclerView") final int pos) {
        DonHang donHang = setRow(pos, author);

        author.danhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (donHang != null) {
                    Intent intent = new Intent(context, NV_DanhGia_Activity.class);
                    final Bundle bundle = new Bundle();
                    bundle.putBinder("donhang", donHang);
                    Log.d(TAG, "onBindViewHolder: DonHang: " + donHang.toString());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Load thông tin sản phẩm lỗi!\nXin vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        author.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (donHang != null) {
                    Intent intent = new Intent(context, ChiTiet_DonHang_Activity.class);
                    final Bundle bundle = new Bundle();
                    bundle.putBinder("donhang", donHang);
                    Log.d(TAG, "onBindViewHolder: DonHang: " + donHang.toString());
                    intent.putExtras(bundle);
                    intent.putExtra("typeUser", "NV");
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Load thông tin sản phẩm lỗi!\nXin vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDon.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLaptop, imgTrangThai;
        TextView name, gia, soLuong, trangThai, tienDo;
        RatingBar ratingBar;
        Button danhGia;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLaptop = itemView.findViewById(R.id.imageView_Laptop);
            imgTrangThai = itemView.findViewById(R.id.imageView_TrangThai);
            name = itemView.findViewById(R.id.textView_TenLaptop);
            gia = itemView.findViewById(R.id.textView_GiaTien);
            soLuong = itemView.findViewById(R.id.textView_Soluong);
            danhGia = itemView.findViewById(R.id.button_Open_DanhGia);
            trangThai = itemView.findViewById(R.id.textView_TrangThai);
            tienDo = itemView.findViewById(R.id.textView_TienDo);
            ratingBar = itemView.findViewById(R.id.ratingBar_DanhGia);
        }
    }

    public DonHang setRow(int pos, @NonNull NV_DonHang_Adapter.AuthorViewHolder author) {
        Log.d(TAG, "setRow: " + pos);
        DonHang donHang = listDon.get(pos);
        Laptop laptop = new Laptop("No Data", "No Data", "No Data", "No Data", "0", 0, 0, new byte[]{});
        Log.d(TAG, "setRow: DonHang: " + donHang.toString());

        for (int i = 0; i < listLap.size(); i++) {
            Laptop getLap = listLap.get(i);
            if (donHang.getMaLaptop().equals(getLap.getMaLaptop())) {
                laptop = getLap;
            }
        }

        ChangeType changeType = new ChangeType();
        Bitmap anhLap = changeType.byteToBitmap(laptop.getAnhLaptop());

        author.imgLaptop.setImageBitmap(anhLap);
        author.name.setText(laptop.getTenLaptop());
        author.gia.setText(donHang.getThanhTien());
        author.soLuong.setText(String.valueOf(donHang.getSoLuong()));

        setDonHangDanhGia(author, donHang);
        return donHang;
    }

    private void setDonHangDanhGia(@NonNull NV_DonHang_Adapter.AuthorViewHolder author, DonHang donHang) {
        if (donHang.getTrangThai().equals("Hoàn thành")) {
            author.tienDo.setText("Hoàn thành");
            author.imgTrangThai.setImageResource(R.drawable.check_icon);
            author.imgTrangThai.setColorFilter(Color.parseColor("#4CAF50"));
            author.trangThai.setText("Đơn hàng giao thành công");
            author.trangThai.setTextColor(Color.parseColor("#C93852B0"));
        } else {
            author.tienDo.setText("Đang giao hàng");
            author.imgTrangThai.setImageResource(R.drawable.icon_delivery_dining);
            author.imgTrangThai.setColorFilter(Color.parseColor("#FF9800"));
            author.trangThai.setText("Đơn hàng đang được giao");
            author.trangThai.setTextColor(Color.parseColor("#FF9800"));
        }

        if (donHang.getIsDanhGia().equals("false")) {
            author.ratingBar.setRating(0f);
            author.ratingBar.setIsIndicator(true);
        } else {
            ArrayList<LaptopRate> list = laptopRateDAO.selectLaptopRate(null, "maRate=?", new String[]{donHang.getMaRate()}, null);
            LaptopRate laptopRate = null;
            if (list.size() > 0) {
                Log.d(TAG, "setLayout: yo");
                laptopRate = list.get(0);
                Log.d(TAG, "setLayout: Laptop rate: " + laptopRate.toString());
            }
            if (laptopRate != null) {
                author.ratingBar.setRating(changeType.getRatingFloat(laptopRate.getRating()));
                author.ratingBar.setIsIndicator(true);
            }
        }
    }
}