package com.nhom5.quanlylaptop.ActivityKH;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom5.quanlylaptop.Activity.Info_Laptop_Activity;
import com.nhom5.quanlylaptop.DAO.DonHangDAO;
import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.DAO.LaptopRateDAO;
import com.nhom5.quanlylaptop.Entity.DonHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.Entity.LaptopRate;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class KH_DanhGia_Activity extends AppCompatActivity {

    Context context = this;
    DonHang donHang = null;
    String TAG = "KH_DanhGia_Activity_____";
    EditText reviewInput;
    Laptop laptop;
    LaptopRateDAO laptopRateDAO;
    DonHangDAO donHangDAO;
    LaptopRate laptopRate;
    ChangeType changeType = new ChangeType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kh_danh_gia);
        reviewInput = findViewById(R.id.editText_DanhGia);
        laptopRateDAO = new LaptopRateDAO(context);
        donHangDAO = new DonHangDAO(context);
        useToolbar();
        getInfoDonHang();
        setLaptopView();
        setLayout();

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
        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
        if (pref != null) {
            String infoWhat = pref.getString("what", "null");
            if (!infoWhat.equals("none")) {
                finish();
            }
        }
    }

    private void setLayout() {
        RatingBar ratingBar = findViewById(R.id.ratingBar_DanhGia);
        EditText editTextDanhGia = findViewById(R.id.editText_DanhGia);
        Button buttonDanhGia = findViewById(R.id.button_DanhGia);
        laptopRateDAO.selectLaptopRate(null, null, null, null);
        if (donHang.getIsDanhGia().equals("false")) {
            setReviewText();
            ratingBar.setRating(0f);

            buttonDanhGia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!editTextDanhGia.getText().equals("")){
                        laptopRate = new LaptopRate(donHang.getMaDH(), laptop.getMaLaptop(), editTextDanhGia.getText().toString(), ratingBar.getRating());
                        laptopRateDAO.insertLaptopRate(laptopRate);
                        donHang.setIsDanhGia("true");
                        donHang.setMaRate(donHang.getMaDH());
                        donHangDAO.updateDonHang(donHang);
                        finish();
                    } else {
                        Toast.makeText(context, "Hãy nhập cảm nghĩ của bạn trước khi hoàn thành nhé!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            ArrayList<LaptopRate> list = laptopRateDAO.selectLaptopRate(null, "maRate=?", new String[]{donHang.getMaRate()}, null);
            if (list.size() > 0) {
                Log.d(TAG, "setLayout: yo");
                laptopRate = list.get(0);
                Log.d(TAG, "setLayout: Laptop rate: " + laptopRate.toString());
            }
            if (laptopRate != null) {
                ratingBar.setRating(changeType.getRatingFloat(laptopRate.getRating()));
                ratingBar.setIsIndicator(true);
                editTextDanhGia.setText(laptopRate.getDanhGia());
                editTextDanhGia.setEnabled(false);

                buttonDanhGia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        }
    }

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText("Đánh giá Sản phẩm");
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getInfoDonHang() {
        Intent intent = getIntent();
        if (intent != null) {
            try {
                donHang = (DonHang) intent.getExtras().getBinder("donhang");
                Log.d(TAG, "getInfoLaptop: DonHang: " + donHang.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setLaptopView() {
        ImageView imageLaptop = findViewById(R.id.imageView_Laptop);
        TextView name = findViewById(R.id.textView_TenLaptop);
        TextView soLuong = findViewById(R.id.textView_Soluong);
        TextView giaTien = findViewById(R.id.textView_GiaTien);
        LinearLayout onclickLaptop = findViewById(R.id.onclick_Laptop);
        laptop = new Laptop("No Data", "No Data", "No Data", "No Data", "0", 0, 0, new byte[]{});
        Log.d(TAG, "setRow: DonHang: " + donHang.toString());
        LaptopDAO laptopDAO = new LaptopDAO(context);
        ArrayList<Laptop> listLap = laptopDAO.selectLaptop(null, null, null, null);

        for (int i = 0; i < listLap.size(); i++) {
            Laptop getLap = listLap.get(i);
            if (donHang.getMaLaptop().equals(getLap.getMaLaptop())) {
                laptop = getLap;
            }
        }

        ChangeType changeType = new ChangeType();
        Bitmap anhLap = changeType.byteToBitmap(laptop.getAnhLaptop());

        imageLaptop.setImageBitmap(anhLap);
        name.setText(laptop.getTenLaptop());
        giaTien.setText(donHang.getThanhTien());
        soLuong.setText(String.valueOf(donHang.getSoLuong()));

        onclickLaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Info_Laptop_Activity.class);
                if (laptop != null) {
                    final Bundle bundle = new Bundle();
                    bundle.putBinder("laptop", laptop);
                    Log.d(TAG, "onBindViewHolder: Laptop: " + laptop.toString());
                    intent.putExtras(bundle);
                    intent.putExtra("openFrom", "other");
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Load thông tin sản phẩm lỗi!\nXin vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setReviewText() {
        AppCompatButton review1 = findViewById(R.id.button_Hint_DanhGia1);
        AppCompatButton review2 = findViewById(R.id.button_Hint_DanhGia2);
        AppCompatButton review3 = findViewById(R.id.button_Hint_DanhGia3);
        AppCompatButton review4 = findViewById(R.id.button_Hint_DanhGia4);

        review1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewInput.setText(R.string.ch_t_l_ng_s_n_ph_m_tuy_t_v_i);
            }
        });

        review2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewInput.setText(R.string.r_t_ng_ti_n);
            }
        });

        review3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewInput.setText(R.string.shop_ph_c_v_t_t);
            }
        });

        review4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewInput.setText(R.string.th_i_gian_giao_h_ng_r_t_nhanh);
            }
        });
    }
}