package com.nhom5.quanlylaptop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class ForgotPass_Activity extends AppCompatActivity {

    TextInputLayout til_Email;
    EditText edt_email;
    Button button_Forget;
    TextView button_signInact;
    String TAG = "ForgotPass_Activity_____";
    String roleUser = "";
    Context context = this;
    String CHANNEL_ID = "ID_chanel001";
    String channel_name = "Kênh notify 001";
    String channel_description = "Mô tả về chanel";
    ArrayList<KhachHang> list;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        button_signInact = findViewById(R.id.go_to_SignInAct);
        til_Email = findViewById(R.id.til_Email);
        edt_email = findViewById(R.id.edt_Email);
        button_Forget = findViewById(R.id.button_Forget);
        getRole();
        clicksignIn();
        createNotificationChannel();
        SharedPreferences pref = getSharedPreferences("Who_Login", MODE_PRIVATE);
        button_Forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate() > 0) {
                    if (pref != null) {
                        String user = pref.getString("who", "");
                        Log.d(TAG, user);
                        if (user.equals("khachHang")) {
                            getPassUser();
                        } else {
                            getPassStaff();
                        }
                    }
                }
            }
        });
    }

    private void clicksignIn() {
        button_signInact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPass_Activity.this, SignIn_Activity.class);
                Log.d(TAG, "goToSignUp: roleUser: " + roleUser);
                intent.putExtra("role", roleUser);
                startActivity(intent);
                finish();
                startActivity(intent);
            }
        });
    }

    private void getPassUser() {
        KhachHangDAO khachHangDAO = new KhachHangDAO(context);
        list = khachHangDAO.selectKhachHang(null, null, null, null);
        if (list.size() > 0) {
            ChangeType changeType = new ChangeType();
            int check = 0;
            for (int i = 0; i < list.size(); i++) {
                KhachHang khachHang = list.get(i);
                if (changeType.deleteSpaceText(edt_email.getText().toString()).equals(khachHang.getEmail())) {
                    showMyNotify(1, "Mật khẩu cũ", "Mật khẩu của bạn là: " + khachHang.getMatKhau());
                    Log.d(TAG, khachHang.getMatKhau());
                    check = 1;
                }
            }
            if (check == 1) {
                til_Email.setError("");
                til_Email.setErrorEnabled(false);
            } else {
                til_Email.setError("Email không tồn tại");
                til_Email.setErrorEnabled(true);
            }
        }
    }

    private void getPassStaff() {
        NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
        ArrayList<NhanVien> list = nhanVienDAO.selectNhanVien(null, null, null, null);
        if (list.size() > 0) {
            int check = 0;
            for (int i = 0; i < list.size(); i++) {
                NhanVien nhanVien = list.get(i);
                if (edt_email.getText().toString().equals(nhanVien.getEmail())) {
                    showMyNotify(1, "Mật khẩu cũ", "Mật khẩu của bạn là: " + nhanVien.getMatKhau());
                    Log.d(TAG, nhanVien.getMatKhau());
                    check = 1;
                }
            }
            if (check == 1) {
                til_Email.setError("");
                til_Email.setErrorEnabled(false);
            } else {
                til_Email.setError("Email không tồn tại");
                til_Email.setErrorEnabled(true);
            }
        }
    }

    void showMyNotify(int notificationId, String _title, String _content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_lock_power_off)
                .setContentTitle(_title)
                .setContentText(_content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, builder.build());
    }

    void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = channel_name;
            String description = channel_description;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public int validate() {
        int check = 1;
        ChangeType changeType = new ChangeType();
        String email = changeType.deleteSpaceText(til_Email.getEditText().getText().toString());
        if (email.isEmpty()) {
            til_Email.setError("Email không được bỏ trống!");
            til_Email.setErrorEnabled(true);
            check = -1;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            til_Email.setError("Định dạng email không hợp lệ!");
            til_Email.setErrorEnabled(true);
            check = -1;
        } else {
            til_Email.setError("");
            til_Email.setErrorEnabled(false);
        }

        return check;
    }

    private void getRole() {
        Intent intent = getIntent();
        if (intent != null) {
            try {
                role = intent.getStringExtra("role");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}