package com.nhom5.quanlylaptop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.KH_Adapter.LienHe_Adapter;
import com.nhom5.quanlylaptop.R;

import java.util.ArrayList;

public class LienHe_Activity extends AppCompatActivity {

    ArrayList<KhachHang> listAdmin = new ArrayList<>();
    RecyclerView recyclerView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        recyclerView = findViewById(R.id.recyclerView_LienHe);
        listAdmin.add(new KhachHang("", "Nguyễn Văn", "Quyết", "", "quyetnvph219@fpt.edu.vn", "", "", "0337575502", "", null));
        listAdmin.add(new KhachHang("", "Mai Văn", "Duy", "", "duynmph27211@fpt.edu.vn", "", "", "0335443223", "", null));
        listAdmin.add(new KhachHang("", "Vũ Trọng", "Hoàng Linh", "", "linhvthph27121@fpt.edu.vn", "", "", "0382148685", "", null));
        listAdmin.add(new KhachHang("", "Nguyễn Quang", "Huy", "", "huynqph27289@fpt.edu.vn", "", "", "0325345678", "", null));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        LienHe_Adapter lienHe_adapter = new LienHe_Adapter(context, listAdmin);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(lienHe_adapter);
        useToolbar();
    }

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText("Danh sách Admin");
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}