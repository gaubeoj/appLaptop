package com.nhom5.quanlylaptop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.R;

import java.util.ArrayList;

public class HDSD_Activity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    Button prev, next;
    Context context = this;
    String role;
    int index = 0;
    int[] imageKH =
            new int[]{R.drawable.hdkh1, R.drawable.hdkh2, R.drawable.hdkh3, R.drawable.hdkh4, R.drawable.hdkh5,
                    R.drawable.hdkh6, R.drawable.hdkh7, R.drawable.hdkh8, R.drawable.hdkh9, R.drawable.hdkh10};
    int[] imageNV =
            new int[]{R.drawable.hdnv1, R.drawable.hdnv2, R.drawable.hdnv3, R.drawable.hdnv4, R.drawable.hdnv5,
                    R.drawable.hdnv6, R.drawable.hdnv7, R.drawable.hdnv8, R.drawable.hdnv9, R.drawable.hdnv10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdsd);
        imageView = findViewById(R.id.imageView_HDSD);
        textView = findViewById(R.id.textView_Page);
        prev = findViewById(R.id.button_Prev);
        next = findViewById(R.id.button_Next);

        getUser();
        if (role.equals("kh")) {
            textView.setText("1/10");
            imageView.setImageResource(imageKH[index]);
            prevImage(imageKH);
            nextImage(imageKH);
        }
        if (role.equals("nv")) {
            textView.setText("1/10");
            imageView.setImageResource(imageNV[index]);
            prevImage(imageNV);
            nextImage(imageNV);
        }
        useToolbar();
    }

    private void prevImage(int[] image) {
        next.setVisibility(View.VISIBLE);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index < image.length - 1) {
                    index++;
                    textView.setText((index + 1) + "/10");
                    imageView.setImageResource(image[index]);
                    next.setVisibility(View.VISIBLE);
                    prev.setVisibility(View.VISIBLE);
                } else {
                    next.setVisibility(View.GONE);
                }
            }
        });
    }

    private void nextImage(int[] image) {
        prev.setVisibility(View.GONE);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index > 0) {
                    index--;
                    textView.setText((index + 1) + "/10");
                    imageView.setImageResource(image[index]);
                    prev.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                } else {
                    prev.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getUser() {
        SharedPreferences pref = context.getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            role = "null";
        } else {
            role = pref.getString("role", "");
        }
    }

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText("Hướng dẫn sử dụng");
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}