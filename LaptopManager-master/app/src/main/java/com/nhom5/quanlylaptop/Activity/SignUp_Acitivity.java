package com.nhom5.quanlylaptop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

public class SignUp_Acitivity extends AppCompatActivity {

    TextView gotoSignInAct;
    TextInputLayout tilEmail, tilPass, tilConfirmPass;
    Context context = this;
    String TAG = "SignUp_Acitivity_____";
    String roleUser;
    AppCompatButton button_Register;
    KhachHangDAO khachHangDAO;
    NhanVienDAO nhanVienDAO;
    ChangeType changeType = new ChangeType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activity);
        gotoSignInAct = findViewById(R.id.go_to_SignInAct);
        tilEmail = findViewById(R.id.textInput_Email);
        tilPass = findViewById(R.id.textInput_Password);
        tilConfirmPass = findViewById(R.id.textInput_Confirm_Password);
        button_Register = findViewById(R.id.button_Register);
        khachHangDAO = new KhachHangDAO(context);
        nhanVienDAO = new NhanVienDAO(context);

        getDataIntent();
        goToSignIn();

        button_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = changeType.deleteSpaceText(tilEmail.getEditText().getText().toString());
                String password = changeType.deleteSpaceText(tilPass.getEditText().getText().toString());
                String confirm = changeType.deleteSpaceText(tilConfirmPass.getEditText().getText().toString());
                if (checkInput(email, password, confirm) == 1) {
                    KhachHang newKH = new KhachHang("", "No Data", "No Data", "No Data",
                            email, password, "No Data", "No Data", "false",
                            changeType.checkByteInput(changeType.bitmapToByte(BitmapFactory.decodeResource(getResources(), R.drawable.image_avatar))));
                    int insert = khachHangDAO.insertKhachHang(newKH);
                    if (insert == -1) {
                        Toast.makeText(context, "Đăng ký thất bại!\nEmail đã có người sử dụng!", Toast.LENGTH_SHORT).show();
                    } else {
                        tilEmail.getEditText().setText("");
                        tilPass.getEditText().setText("");
                        tilConfirmPass.getEditText().setText("");
                        tilEmail.getEditText().clearFocus();
                        tilPass.getEditText().clearFocus();
                        tilConfirmPass.getEditText().clearFocus();
                        Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public int checkInput(String email, String password, String confirm) {
        int check = 1;
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Định dạng email không hợp lệ!");
            tilEmail.setErrorEnabled(true);
            check = -1;
        } else {
            tilEmail.setError("");
            tilEmail.setErrorEnabled(false);
        }

        if (password.isEmpty()) {
            tilPass.setError("Mật khẩu không được bỏ trống!");
            tilPass.setErrorEnabled(true);
            check = -1;
        } else {
            tilPass.setError("");
            tilPass.setErrorEnabled(false);
        }

        if (confirm.isEmpty()) {
            tilConfirmPass.setError("Mật khẩu nhập lại không được bỏ trống!");
            tilConfirmPass.setErrorEnabled(true);
            check = -1;
        } else {
            tilConfirmPass.setError("");
            tilConfirmPass.setErrorEnabled(false);
        }

        if (!password.equals(confirm)) {
            tilConfirmPass.setError("Mật khẩu nhập lại không trùng!");
            tilConfirmPass.setErrorEnabled(true);
            check = -1;
        } else {
            tilConfirmPass.setError("");
            tilConfirmPass.setErrorEnabled(false);
        }
        return check;
    }

    private void getDataIntent() {
        Log.d(TAG, "getDataIntent: true");
        Intent intent = getIntent();
        if (intent != null) {
            Log.d(TAG, "getDataIntent: intent != null");
            roleUser = intent.getStringExtra("role");
        }
    }

    private void goToSignIn() {
        gotoSignInAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignIn_Activity.class);
                Log.d(TAG, "goToSignUp: roleUser: " + roleUser);
                intent.putExtra("role", roleUser);
                startActivity(intent);
                finish();
            }
        });
    }
}