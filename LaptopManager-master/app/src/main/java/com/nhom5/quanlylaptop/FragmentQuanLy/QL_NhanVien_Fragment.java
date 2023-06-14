package com.nhom5.quanlylaptop.FragmentQuanLy;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_KhachHang_Adapter;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_NhanVien_Adapter;
import com.nhom5.quanlylaptop.NVA_Loader.QL_KhachHang_Loader;
import com.nhom5.quanlylaptop.NVA_Loader.QL_NhanVien_Loader;
import com.nhom5.quanlylaptop.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QL_NhanVien_Fragment extends Fragment {

    String TAG = "QL_NhanVien_Fragment_____";
    RecyclerView reView;
    TextView countNV;
    RelativeLayout relativeLayout;
    Spinner spinnerRole;
    LinearLayout linearLayout, linearNhanVienEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_nhan_vien, container, false);
        spinnerRole = view.findViewById(R.id.spinner_Role);
        countNV = view.findViewById(R.id.textView_Soluong);
        reView = view.findViewById(R.id.recyclerView_NVA_NhanVien);
        linearLayout = view.findViewById(R.id.loadingView);
        relativeLayout = view.findViewById(R.id.layoutView);
        linearNhanVienEmpty = view.findViewById(R.id.linearNhanVienEmpty);

        useToolbar();
        spinnerRole.setSelection(0);
        autoSetRole();
        selectRoleNV();
        return view;
    }

    private void useToolbar() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar_Account);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (toolbar != null) {
            NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
            ArrayList<NhanVien> listNV = nhanVienDAO.selectNhanVien(null, null, null, null);
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
                                ArrayList<NhanVien> getList = new ArrayList<>();
                                if (!input.equals("")) {
                                    for (NhanVien nv : listNV) {
                                        if (nv.getEmail().matches(".*?" + input + ".*")) {
                                            getList.add(nv);
                                        }
                                    }
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                    reView.setLayoutManager(linearLayoutManager);
                                    QL_NhanVien_Adapter ql_nhanVien_adapter = new QL_NhanVien_Adapter(getList, getContext(), countNV);
                                    reView.setAdapter(ql_nhanVien_adapter);
                                } else {
                                    autoSetRole();
                                    selectRoleNV();
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

    @Override
    public void onResume() {
        super.onResume();
        autoSetRole();
    }

    private void autoSetRole() {
        if (spinnerRole.getSelectedItem() != null) {
            String role = spinnerRole.getSelectedItem().toString();
            switch (role) {
                case "Tất cả": {
                    QL_NhanVien_Loader ql_nhanVien_loader = new QL_NhanVien_Loader(getContext(), reView, countNV, linearLayout, linearNhanVienEmpty, relativeLayout);
                    ql_nhanVien_loader.execute("all");
                    break;
                }
                case "Bán hàng Online": {
                    QL_NhanVien_Loader ql_nhanVien_loader = new QL_NhanVien_Loader(getContext(), reView, countNV, linearLayout, linearNhanVienEmpty, relativeLayout);
                    ql_nhanVien_loader.execute("saleol");
                    break;
                }
                case "Bán hàng Ofline": {
                    QL_NhanVien_Loader ql_nhanVien_loader = new QL_NhanVien_Loader(getContext(), reView, countNV, linearLayout, linearNhanVienEmpty, relativeLayout);
                    ql_nhanVien_loader.execute("saleof");
                    break;
                }
            }
        }
    }

    private void selectRoleNV() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.select_role_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);

        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String role = spinnerRole.getItemAtPosition(position).toString();
                switch (role) {
                    case "Tất cả": {
                        QL_NhanVien_Loader ql_nhanVien_loader = new QL_NhanVien_Loader(getContext(), reView, countNV, linearLayout, linearNhanVienEmpty, relativeLayout);
                        ql_nhanVien_loader.execute("all");
                        break;
                    }
                    case "Bán hàng Online": {
                        QL_NhanVien_Loader ql_nhanVien_loader = new QL_NhanVien_Loader(getContext(), reView, countNV, linearLayout, linearNhanVienEmpty, relativeLayout);
                        ql_nhanVien_loader.execute("saleol");
                        break;
                    }
                    case "Bán hàng Ofline": {
                        QL_NhanVien_Loader ql_nhanVien_loader = new QL_NhanVien_Loader(getContext(), reView, countNV, linearLayout, linearNhanVienEmpty, relativeLayout);
                        ql_nhanVien_loader.execute("saleof");
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}