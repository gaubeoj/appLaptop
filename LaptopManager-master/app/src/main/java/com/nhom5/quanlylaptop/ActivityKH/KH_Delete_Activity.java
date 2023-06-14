package com.nhom5.quanlylaptop.ActivityKH;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom5.quanlylaptop.Activity.PickRole_Activity;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.ThongBaoDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.ThongBao;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;
import com.nhom5.quanlylaptop.Support.GetData;

import java.util.ArrayList;

public class KH_Delete_Activity extends AppCompatActivity {

    Context context = this;
    EditText editText_LyDoXoaTK, edittext_emailXoa;
    CheckBox checkBox;
    Button button_huyXoaTKKH, button_xoaTKKH;
    KhachHangDAO khachHangDAO;
    KhachHang khachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kh_delete);
        edittext_emailXoa = findViewById(R.id.edittext_emailXoa);
        editText_LyDoXoaTK = findViewById(R.id.editText_LyDoXoaTK);
        checkBox = findViewById(R.id.checkBox);
        button_huyXoaTKKH = findViewById(R.id.button_huyXoaTKKH);
        button_xoaTKKH = findViewById(R.id.button_xoaTKKH);
        khachHangDAO = new KhachHangDAO(context);

        getUserKH();
        useToolbar();
        if (khachHang != null) {
            edittext_emailXoa.setText(khachHang.getEmail());

            button_xoaTKKH.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editText_LyDoXoaTK.getText().toString().isEmpty()) {
                        Toast.makeText(context, "Lý do không được bỏ trống!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!checkBox.isChecked()) {
                        Toast.makeText(context, "Bạn phải click đồng ý với thỏa thuận!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    khachHangDAO.deleteKhachHang(khachHang);
                    Toast.makeText(context, "Tài khoản không còn tồn tại!", Toast.LENGTH_SHORT).show();

                    GetData getData = new GetData(context);
                    ChangeType changeType = new ChangeType();
                    ThongBaoDAO thongBaoDAO = new ThongBaoDAO(context);
                    ThongBao thongBaoAD = new ThongBao("TB", "nah", "Thông báo mới xóa tài khoản",
                            " Khách hàng " + changeType.fullNameKhachHang(khachHang) + " có mã khách hàng" + khachHang.getMaKH() + " đã xóa tài khoản của họ\n Lí do :" + editText_LyDoXoaTK.getText().toString(), getData.getNowDateSQL());
                    thongBaoDAO.insertThongBao(thongBaoAD, "ad");
                    startActivity(new Intent(context, PickRole_Activity.class));
                    finish();
                }
            });
        }

        button_huyXoaTKKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    private void getUserKH() {
        SharedPreferences pref = getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            khachHang = null;
        } else {
            String user = pref.getString("who", "");
            ArrayList<KhachHang> list = khachHangDAO.selectKhachHang(null, "maKH=?", new String[]{user}, null);
            if (list.size() > 0) {
                khachHang = list.get(0);
            }
        }
    }

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText("Xóa tài khoản");
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}