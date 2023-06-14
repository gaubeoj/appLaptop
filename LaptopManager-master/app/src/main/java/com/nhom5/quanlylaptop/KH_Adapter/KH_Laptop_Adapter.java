package com.nhom5.quanlylaptop.KH_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.quanlylaptop.Activity.Info_Laptop_Activity;
import com.nhom5.quanlylaptop.DAO.LaptopRateDAO;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.Entity.LaptopRate;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class KH_Laptop_Adapter extends RecyclerView.Adapter<KH_Laptop_Adapter.AuthorViewHolder> {

    Context context;
    ArrayList<Laptop> listLap;
    String TAG = "KH_Laptop_Adapter_____";

    public KH_Laptop_Adapter(ArrayList<Laptop> listLap, Context context) {
        this.listLap = listLap;
        this.context = context;
    }

    @NonNull
    @Override
    public KH_Laptop_Adapter.AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup vGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_kh_laptop, vGroup, false);
        return new KH_Laptop_Adapter.AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KH_Laptop_Adapter.AuthorViewHolder author, @SuppressLint("RecyclerView") final int pos) {
        setRow(pos, author);

        author.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Info_Laptop_Activity.class);
                Laptop laptop = listLap.get(pos);
                if (laptop != null) {
                    final Bundle bundle = new Bundle();
                    bundle.putBinder("laptop", laptop);
                    Log.d(TAG, "onBindViewHolder: Laptop: " + laptop.toString());
                    intent.putExtras(bundle);
                    intent.putExtra("openFrom", "viewer");
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Load thông tin sản phẩm lỗi!\nXin vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listLap.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLaptop;
        TextView name, gia, ram;
        RatingBar ratingBar;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLaptop = itemView.findViewById(R.id.imageView_Laptop);
            name = itemView.findViewById(R.id.textView_TenLaptop);
            gia = itemView.findViewById(R.id.textView_GiaTien);
            ram = itemView.findViewById(R.id.textView_Ram);
            ratingBar = itemView.findViewById(R.id.ratingBar_DanhGia);
        }
    }

    public void setRow(int pos, @NonNull KH_Laptop_Adapter.AuthorViewHolder author) {
        Log.d(TAG, "setRow: " + pos);
        Laptop laptop = listLap.get(pos);
        Log.d(TAG, "setRow: Laptop: " + laptop.toString());


        ChangeType changeType = new ChangeType();
        Bitmap anhLap = changeType.byteToBitmap(laptop.getAnhLaptop());

        author.imgLaptop.setImageBitmap(anhLap);
        author.name.setText(laptop.getTenLaptop());
        author.gia.setText(laptop.getGiaTien());
        author.ram.setText(laptop.getThongSoKT());

        LaptopRateDAO laptopRateDAO = new LaptopRateDAO(context);
        ArrayList<LaptopRate> list = laptopRateDAO.selectLaptopRate(null, "maLaptop=?", new String[]{laptop.getMaLaptop()}, null);
        if (list.size() > 0) {
            float rating = 0;
            for (LaptopRate lapRate : list) {
                rating += lapRate.getRating();
            }
            rating = rating / list.size();
            author.ratingBar.setRating(changeType.getRatingFloat(rating));
        } else {
            author.ratingBar.setRating(0f);
        }
    }
}