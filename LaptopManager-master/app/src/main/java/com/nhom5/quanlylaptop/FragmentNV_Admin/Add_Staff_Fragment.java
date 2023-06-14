package com.nhom5.quanlylaptop.FragmentNV_Admin;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class Add_Staff_Fragment extends Fragment {

    ChangeType changeType = new ChangeType();
    NhanVienDAO nhanVienDAO;
    Spinner spinnerRole;
    ArrayList<NhanVien> listNV = new ArrayList<NhanVien>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_staff, container, false);
        spinnerRole = view.findViewById(R.id.spinner_Role);
        nhanVienDAO = new NhanVienDAO(getContext());
        listNV = nhanVienDAO.selectNhanVien(null, null, null, null);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.role_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);
        addStaff(view);
        return view;
    }

    private void addStaff(View view) {
        TextInputLayout textInput_LastName = view.findViewById(R.id.textInput_LastName);
        TextInputLayout textInput_FirstName = view.findViewById(R.id.textInput_FirstName);
        TextInputLayout textInput_Email = view.findViewById(R.id.textInput_Email);
        TextInputLayout textInput_SDT = view.findViewById(R.id.textInput_SDT);
        TextInputLayout textInput_Password = view.findViewById(R.id.textInput_Password);
        AppCompatButton button = view.findViewById(R.id.button_AddNV);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastName = changeType.deleteSpaceText(textInput_LastName.getEditText().getText().toString());
                String firstName = changeType.deleteSpaceText(textInput_FirstName.getEditText().getText().toString());
                String email = changeType.deleteSpaceText(textInput_Email.getEditText().getText().toString());
                String sdt = changeType.deleteSpaceText(textInput_SDT.getEditText().getText().toString());
                String password = changeType.deleteSpaceText(textInput_Password.getEditText().getText().toString());
                String roleNV = spinnerRole.getSelectedItem().toString();
                Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.image_avatar);
                byte[] avatar = changeType.checkByteInput(changeType.bitmapToByte(bitmap));

                if (checkInput(view) == 1){
                    NhanVien nhanVien = new NhanVien("NV" + listNV.size(), lastName, firstName, "No Data",
                            email, password, "No Data", sdt, roleNV, 0, 0, avatar);
                    int check = nhanVienDAO.insertNhanVien(nhanVien);
                    if (check == 1){
                        textInput_LastName.getEditText().setText("");
                        textInput_FirstName.getEditText().setText("");
                        textInput_Email.getEditText().setText("");
                        textInput_SDT.getEditText().setText("");
                        textInput_Password.getEditText().setText("");
                        textInput_LastName.clearFocus();
                        textInput_FirstName.clearFocus();
                        textInput_Email.clearFocus();
                        textInput_SDT.clearFocus();
                        textInput_Password.clearFocus();
                    }
                }
            }
        });
    }

    private int checkInput(View view){
        TextInputLayout textInput_LastName = view.findViewById(R.id.textInput_LastName);
        TextInputLayout textInput_FirstName = view.findViewById(R.id.textInput_FirstName);
        TextInputLayout textInput_Email = view.findViewById(R.id.textInput_Email);
        TextInputLayout textInput_SDT = view.findViewById(R.id.textInput_SDT);
        TextInputLayout textInput_Password = view.findViewById(R.id.textInput_Password);
        TextInputLayout textInput_Role = view.findViewById(R.id.textInput_BoPhan);
        String lastName = changeType.deleteSpaceText(textInput_LastName.getEditText().getText().toString());
        String firstName = changeType.deleteSpaceText(textInput_FirstName.getEditText().getText().toString());
        String email = changeType.deleteSpaceText(textInput_Email.getEditText().getText().toString());
        String sdt = changeType.deleteSpaceText(textInput_SDT.getEditText().getText().toString());
        String password = changeType.deleteSpaceText(textInput_Password.getEditText().getText().toString());
        int check = 1;

        if (lastName.isEmpty()) {
            textInput_LastName.setError("Họ không được bỏ trống!");
            textInput_LastName.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_LastName.setError("");
            textInput_LastName.setErrorEnabled(false);
        }

        if (firstName.isEmpty()) {
            textInput_FirstName.setError("Tên không được bỏ trống!");
            textInput_FirstName.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_FirstName.setError("");
            textInput_FirstName.setErrorEnabled(false);
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()) {
            textInput_Email.setError("Định dạng email không hợp lệ!");
            textInput_Email.setErrorEnabled(true);
            check = -1;
        } else {
            if (nhanVienDAO.selectNhanVien(null, "email=?", new String[]{email}, null).size() != 0){
                textInput_Email.setError("Email đã được sử dụng!");
                textInput_Email.setErrorEnabled(true);
                check = -1;
            } else {
                textInput_Email.setError("");
                textInput_Email.setErrorEnabled(false);
            }
        }

        if (sdt.isEmpty()) {
            textInput_SDT.setError("Số điện thoại không được bỏ trống!");
            textInput_SDT.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_SDT.setError("");
            textInput_SDT.setErrorEnabled(false);
        }

        if (password.isEmpty()) {
            textInput_Password.setError("Mật khẩu không được bỏ trống!");
            textInput_Password.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_Password.setError("");
            textInput_Password.setErrorEnabled(false);
        }

        if (spinnerRole.getSelectedItemPosition() == 0){
            textInput_Role.setError("Bộ phận phải được chọn!");
            textInput_Role.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_Role.setError("");
            textInput_Role.setErrorEnabled(false);
        }

        return check;
    }
}