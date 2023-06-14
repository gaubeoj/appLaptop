package com.nhom5.quanlylaptop.KH_Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class LienHe_Adapter extends RecyclerView.Adapter<LienHe_Adapter.AuthorViewHolder> {
    Context context;
    ArrayList<KhachHang> listAdmin;

    public LienHe_Adapter(Context context, ArrayList<KhachHang> listAdmin) {
        this.context = context;
        this.listAdmin = listAdmin;
    }

    @NonNull
    @Override
    public LienHe_Adapter.AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_lien_he, parent, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LienHe_Adapter.AuthorViewHolder holder, int position) {
        KhachHang khachHang = listAdmin.get(position);
        ChangeType changeType = new ChangeType();
        name.setText(changeType.fullNameKhachHang(khachHang));
        phone.setText(khachHang.getPhone());
        email.setText(khachHang.getEmail());
        lienhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + khachHang.getEmail()));
                    context.startActivity(Intent.createChooser(intent, "Choose an Email Client"));
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listAdmin.size();
    }

    ImageView avatar;
    TextView name, email, phone;
    LinearLayout lienhe;

    public class AuthorViewHolder extends RecyclerView.ViewHolder {
        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.imageView_Avatar);
            name = itemView.findViewById(R.id.textView_TenUser);
            email = itemView.findViewById(R.id.textView_Email);
            phone = itemView.findViewById(R.id.textView_SDT);
            lienhe = itemView.findViewById(R.id.linearlienhe);
        }
    }
}
