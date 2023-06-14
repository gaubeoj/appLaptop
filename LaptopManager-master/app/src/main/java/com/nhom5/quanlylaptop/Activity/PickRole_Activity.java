package com.nhom5.quanlylaptop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nhom5.quanlylaptop.R;

public class PickRole_Activity extends AppCompatActivity {

    AppCompatButton rollAdmin, rollNV, rollKH;
    String roleUser = "";
    String TAG = "PickRole_Activity_____";
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_role);
        rollAdmin = findViewById(R.id.role_Admin);
        rollNV = findViewById(R.id.role_NhanVien);
        rollKH = findViewById(R.id.role_KhachHang);

        rollAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: rollAdmin");
                Intent intent = new Intent(context, SignIn_Activity.class);
                roleUser = "admin";
                Log.d(TAG, "onClick: roleUser: " + roleUser);
                intent.putExtra("role", roleUser);
                startActivity(intent);
            }
        });

        rollNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: rollNV");
                Intent intent = new Intent(context, SignIn_Activity.class);
                roleUser = "nhanVien";
                Log.d(TAG, "onClick: roleUser: " + roleUser);
                intent.putExtra("role", roleUser);
                startActivity(intent);
            }
        });

        rollKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: rollKH");
                Intent intent = new Intent(context, SignIn_Activity.class);
                roleUser = "khachHang";
                Log.d(TAG, "onClick: roleUser: " + roleUser);
                intent.putExtra("role", roleUser);
                startActivity(intent);
            }
        });
    }
}
