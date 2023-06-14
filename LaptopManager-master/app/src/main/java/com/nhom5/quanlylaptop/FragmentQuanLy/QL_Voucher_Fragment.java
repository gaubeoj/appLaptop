package com.nhom5.quanlylaptop.FragmentQuanLy;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.DAO.VoucherDAO;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.Entity.Voucher;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_Laptop_Adapter;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_NhanVien_Adapter;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_Voucher_Adapter;
import com.nhom5.quanlylaptop.NVA_Loader.QL_Voucher_Loader;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;
import com.nhom5.quanlylaptop.Support.GetData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class QL_Voucher_Fragment extends Fragment {

    AppCompatButton addVoucher;
    ArrayList<Voucher> listVou = new ArrayList<>();
    VoucherDAO voucherDAO;
    String TAG = "QL_Voucher_Fragment_____";
    RecyclerView reView;
    RelativeLayout relativeLayout;
    LinearLayout linearLayout, linearVoucherEmpty;
    ChangeType changeType = new ChangeType();
    TextInputLayout textInput_Name, textInput_GiamGia, textInput_NBD, textInput_NKT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_voucher, container, false);
        addVoucher = view.findViewById(R.id.button_Add_Voucher);
        reView = view.findViewById(R.id.recyclerView_NVA_Voucher);
        relativeLayout = view.findViewById(R.id.layoutView);
        linearLayout = view.findViewById(R.id.loadingView);
        linearVoucherEmpty = view.findViewById(R.id.linearVoucherEmpty);
        voucherDAO = new VoucherDAO(getContext());

        listVou = voucherDAO.selectVoucher(null, null, null, null);
        QL_Voucher_Loader ql_voucher_loader = new QL_Voucher_Loader(getContext(), reView, linearLayout, linearVoucherEmpty, relativeLayout);
        ql_voucher_loader.execute("");

        searchVoucher(view);
        openDialog();
        return view;
    }

    private void searchVoucher(View view) {
        TextView textView_Progress = view.findViewById(R.id.textView_Progress);
        SeekBar seekBar = view.findViewById(R.id.seekBar);
        textView_Progress.setText("Mức giảm giá: < 100%");
        seekBar.setProgress(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ArrayList<Voucher> getList = new ArrayList<>();
                if (progress == 100) {
                    textView_Progress.setText("Mức giảm giá: < 100%");
                    QL_Voucher_Loader ql_voucher_loader = new QL_Voucher_Loader(getContext(), reView, linearLayout, linearVoucherEmpty, relativeLayout);
                    ql_voucher_loader.execute("");
                } else {
                    textView_Progress.setText("Mức giảm giá: < " + progress + "%");
                    for (Voucher vou : listVou) {
                        if (changeType.voucherToInt(vou.getGiamGia()) < progress) {
                            getList.add(vou);
                        }
                    }
                    if (getList.size() == 0) {
                        linearVoucherEmpty.setVisibility(View.VISIBLE);
                    } else {
                        linearVoucherEmpty.setVisibility(View.GONE);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        reView.setLayoutManager(linearLayoutManager);
                        QL_Voucher_Adapter ql_voucher_adapter = new QL_Voucher_Adapter(getList, getContext());
                        reView.setAdapter(ql_voucher_adapter);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inft = ((Activity) getContext()).getLayoutInflater();
        View view = inft.inflate(R.layout.dialog_add_edit_voucher, null);

        TextView title = view.findViewById(R.id.textView_Title_Dialog);
        textInput_Name = view.findViewById(R.id.textInput_Name);
        textInput_GiamGia = view.findViewById(R.id.textInput_GiamGia);
        textInput_NBD = view.findViewById(R.id.textInput_NBD);
        textInput_NKT = view.findViewById(R.id.textInput_NKT);
        TextView onclick_NBD = view.findViewById(R.id.onlick_NBD);
        TextView onclick_NKT = view.findViewById(R.id.onlick_NKT);
        AppCompatButton button = view.findViewById(R.id.button_Dialog);

        title.setText("Thêm Voucher");
        textInput_Name.getEditText().setText("");
        textInput_GiamGia.getEditText().setText("");
        textInput_NBD.getEditText().setText("");
        textInput_NKT.getEditText().setText("");
        button.setText("Thêm mới");

        builder.setView(view);
        Dialog dialog = builder.create();
        addVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                onclickGetTime(textInput_NBD, onclick_NBD);
                onclickGetTime(textInput_NKT, onclick_NKT);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenVou = changeType.deleteSpaceText(textInput_Name.getEditText().getText().toString());
                String giamGia = changeType.deleteSpaceText(textInput_GiamGia.getEditText().getText().toString());
                String nbd = textInput_NBD.getEditText().getText().toString();
                String nkt = textInput_NKT.getEditText().getText().toString();

                if (checkInputVoucher() == 1){
                    Voucher voucher = new Voucher("", tenVou, giamGia + "%", nbd, nkt);
                    int check = voucherDAO.insertVoucher(voucher);

                    if (check == 1){
                        new GetData(getContext()).addDataUseVoucher();
                        QL_Voucher_Loader ql_voucher_loader = new QL_Voucher_Loader(getContext(), reView, linearLayout, linearVoucherEmpty, relativeLayout);
                        ql_voucher_loader.execute("");
                        dialog.dismiss();
                    }
                }
            }
        });
    }

    private void onclickGetTime(TextInputLayout textInputLayout, TextView textView) {
        Calendar calendar = Calendar.getInstance();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month += 1;
                        String getDay;
                        String getMonth;
                        if (day < 10) {
                            getDay = "0" + day;
                        } else {
                            getDay = String.valueOf(day);
                        }
                        if (month < 10) {
                            getMonth = "0" + month;
                        } else {
                            getMonth = String.valueOf(month);
                        }
                        textInputLayout.getEditText().setText(year + "-" + getMonth + "-" + getDay);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    private int checkInputVoucher() {
        int check = 1;
        if (textInput_Name.getEditText().getText().toString().isEmpty()) {
            textInput_Name.setError("Mã Voucher không được bỏ trống!");
            textInput_Name.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_Name.setError("");
            textInput_Name.setErrorEnabled(false);
        }

        if (textInput_GiamGia.getEditText().getText().toString().isEmpty()) {
            textInput_GiamGia.setError("Ưu đãi không được bỏ trống!");
            textInput_GiamGia.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_GiamGia.setError("");
            textInput_GiamGia.setErrorEnabled(false);
        }

        if (textInput_NBD.getEditText().getText().toString().isEmpty()) {
            textInput_NBD.setError("Ngày bắt đầu phải được chọn!");
            textInput_NBD.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_NBD.setError("");
            textInput_NBD.setErrorEnabled(false);
        }

        if (textInput_NKT.getEditText().getText().toString().isEmpty()) {
            textInput_NKT.setError("Ngày kết thúc phải được chọn!");
            textInput_NKT.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_NKT.setError("");
            textInput_NKT.setErrorEnabled(false);
        }
        return check;
    }
}