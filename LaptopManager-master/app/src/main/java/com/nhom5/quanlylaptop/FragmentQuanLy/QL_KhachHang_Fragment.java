package com.nhom5.quanlylaptop.FragmentQuanLy;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.nhom5.quanlylaptop.DAO.DonHangDAO;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_DonHang_Adapter;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_KhachHang_Adapter;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_Laptop_Adapter;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_NhanVien_Adapter;
import com.nhom5.quanlylaptop.NVA_Loader.QL_KhachHang_Loader;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QL_KhachHang_Fragment extends Fragment {


    AppCompatButton addKH;
    KhachHangDAO khachHangDAO;
    ArrayList<KhachHang> listKH = new ArrayList<>();
    ChangeType changeType = new ChangeType();
    String TAG = "QL_KhachHang_Fragment_____";
    TextView countKH;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    LinearLayout linearLayout;
    LinearLayout linearKhachHangEmpty;
    TextInputLayout textInput_LastName, textInput_FirstName, textInput_GioiTinh, textInput_Email, textInput_SDT, textInput_Password;
    Spinner genderSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_khach_hang, container, false);
        addKH = view.findViewById(R.id.button_AddKH);
        countKH = view.findViewById(R.id.textView_Soluong);
        recyclerView = view.findViewById(R.id.recyclerView_NVA_KhachHang);
        relativeLayout = view.findViewById(R.id.layoutView);
        linearLayout = view.findViewById(R.id.loadingView);
        linearKhachHangEmpty = view.findViewById(R.id.linearKhachHangEmpty);

        khachHangDAO = new KhachHangDAO(getContext());
        useToolbar();
        QL_KhachHang_Loader qlKhachHangLoader = new QL_KhachHang_Loader(getContext(), recyclerView, countKH, linearLayout, linearKhachHangEmpty, relativeLayout);
        qlKhachHangLoader.execute("");
        openDialog();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        QL_KhachHang_Loader qlKhachHangLoader = new QL_KhachHang_Loader(getContext(), recyclerView, countKH, linearLayout, linearKhachHangEmpty, relativeLayout);
        qlKhachHangLoader.execute("");
    }

    private void useToolbar() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar_Account);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (toolbar != null) {
            khachHangDAO = new KhachHangDAO(getContext());
            ArrayList<KhachHang> listKH = khachHangDAO.selectKhachHang(null, null, null, null);
            ImageButton btn_search = toolbar.findViewById(R.id.imageButton_Search_Toolbar);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inft = ((Activity) getContext()).getLayoutInflater();
            View dialogView = inft.inflate(R.layout.dialog_text_find, null);
            AppCompatButton buttonSearch = dialogView.findViewById(R.id.button_Search_Dialog);
            EditText editTextSearch = dialogView.findViewById(R.id.editText_Search);
            editTextSearch.setHint("Email khách hàng...");

            builder.setView(dialogView);
            Dialog dialog = builder.create();
            btn_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.show();
                    if (editTextSearch.getHint().equals("Email khách hàng...")){
                        editTextSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                String input = editTextSearch.getText().toString();
                                ArrayList<KhachHang> getList = new ArrayList<>();
                                if (!input.equals("")) {
                                    for (KhachHang kh : listKH) {
                                        if (kh.getEmail().matches(".*?" + input + ".*")) {
                                            getList.add(kh);
                                        }
                                    }
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    QL_KhachHang_Adapter ql_khachHang_adapter = new QL_KhachHang_Adapter(getList, getContext(), countKH);
                                    recyclerView.setAdapter(ql_khachHang_adapter);
                                } else {
                                    QL_KhachHang_Loader qlKhachHangLoader = new QL_KhachHang_Loader(getContext(), recyclerView, countKH, linearLayout, linearKhachHangEmpty, relativeLayout);
                                    qlKhachHangLoader.execute("");
                                }
                            }
                        });
                    }
                }
            });

            buttonSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }

    private void openDialog() {
        listKH = khachHangDAO.selectKhachHang(null, null, null, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inft = ((Activity) getActivity()).getLayoutInflater();
        View view = inft.inflate(R.layout.dialog_add_edit_sth, null);

        TextView titleDialog = view.findViewById(R.id.textView_Title_Dialog);
        textInput_LastName = view.findViewById(R.id.textInput_LastName);
        textInput_FirstName = view.findViewById(R.id.textInput_FirstName);
        textInput_GioiTinh = view.findViewById(R.id.textInput_GioiTinh);
        textInput_Email = view.findViewById(R.id.textInput_Email);
        textInput_SDT = view.findViewById(R.id.textInput_SDT);
        textInput_Password = view.findViewById(R.id.textInput_Password);
        genderSpinner = view.findViewById(R.id.spinner_Gender);
        AppCompatButton button = view.findViewById(R.id.button_Dialog);

        builder.setView(view);
        Dialog dialog = builder.create();
        addKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.gender_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                genderSpinner.setAdapter(adapter);
                clearTextDialog();
                titleDialog.setText("Thêm Khách Hàng");
                button.setText("Tạo mới");
                dialog.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastName = changeType.deleteSpaceText(textInput_LastName.getEditText().getText().toString());
                String firstName = changeType.deleteSpaceText(textInput_FirstName.getEditText().getText().toString());
                String email = changeType.deleteSpaceText(textInput_Email.getEditText().getText().toString());
                String sdt = changeType.deleteSpaceText(textInput_SDT.getEditText().getText().toString());
                String password = changeType.deleteSpaceText(textInput_Password.getEditText().getText().toString());
                String gioiTinh = genderSpinner.getSelectedItem().toString();
                Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.image_avatar);
                byte[] avatar = changeType.checkByteInput(changeType.bitmapToByte(bitmap));
                if (getTextInput() == 1) {
                    KhachHang khachHang = new KhachHang("KH", lastName, firstName, gioiTinh,
                            email, password, "No Data", sdt, "false", avatar);
                    khachHangDAO.insertKhachHang(khachHang);
                    dialog.cancel();
                    QL_KhachHang_Loader qlKhachHangLoader = new QL_KhachHang_Loader(getContext(), recyclerView, countKH, linearLayout, linearKhachHangEmpty, relativeLayout);
                    qlKhachHangLoader.execute("");
                }
            }
        });
    }

    private void clearTextDialog() {
        textInput_LastName.getEditText().setText("");
        textInput_LastName.clearFocus();
        textInput_FirstName.getEditText().setText("");
        textInput_FirstName.clearFocus();
        textInput_Email.getEditText().setText("");
        textInput_Email.clearFocus();
        textInput_SDT.getEditText().setText("");
        textInput_SDT.clearFocus();
        textInput_Password.getEditText().setText("");
        textInput_Password.clearFocus();
    }

    private int getTextInput() {
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

        if (genderSpinner.getSelectedItemPosition() == 0) {
            textInput_GioiTinh.setError("Giới tính phải được chọn!");
            textInput_GioiTinh.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_GioiTinh.setError("");
            textInput_GioiTinh.setErrorEnabled(false);
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInput_Email.setError("Định dạng email không hợp lệ!");
            textInput_Email.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_Email.setError("");
            textInput_Email.setErrorEnabled(false);
        }

        if (!Patterns.PHONE.matcher(sdt).matches()) {
            textInput_SDT.setError("Định dạng số điện thoại không hợp lệ!");
            textInput_SDT.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_SDT.setError("");
            textInput_SDT.setErrorEnabled(false);
        }

        if (password.isEmpty()) {
            textInput_Password.setError("Quê quán không được bỏ trống!");
            textInput_Password.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_Password.setError("");
            textInput_Password.setErrorEnabled(false);
        }

        return check;
    }
}