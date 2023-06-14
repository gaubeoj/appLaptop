package com.nhom5.quanlylaptop.FragmentQuanLy;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom5.quanlylaptop.Activity.DonHang_Manager_Activity;
import com.nhom5.quanlylaptop.DAO.DonHangDAO;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.Entity.DonHang;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_DonHang_Adapter;
import com.nhom5.quanlylaptop.NVA_Loader.QL_DonHang_Loader;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class QL_DonHang_Fragment extends Fragment {

    String TAG = "KH_DonHang_Activity_____";
    NhanVien nhanVien;
    AppCompatButton addDHButton;
    RecyclerView recyclerView;
    TextView count, textView_Date;
    RelativeLayout relativeLayout;
    String getMonth;
    String[] getDate;
    ChangeType changeType;
    LinearLayout linearLayout, linearDonHangEmpty, change_Time;
    ArrayList<DonHang> listDon = new ArrayList<>();
    ArrayList<Laptop> listLap = new ArrayList<>();
    ArrayList<KhachHang> listKH = new ArrayList<>();
    LaptopDAO laptopDAO;
    KhachHangDAO khachHangDAO;
    DonHangDAO donHangDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_don_hang, container, false);
        addDHButton = view.findViewById(R.id.button_AddDH);
        recyclerView = view.findViewById(R.id.recyclerView_NVA_DonHang);
        count = view.findViewById(R.id.textView_Soluong);
        relativeLayout = view.findViewById(R.id.layoutView);
        linearLayout = view.findViewById(R.id.loadingView);
        linearDonHangEmpty = view.findViewById(R.id.linearDonHangEmpty);
        change_Time = view.findViewById(R.id.change_Time);
        textView_Date = view.findViewById(R.id.textView_Date);
        laptopDAO = new LaptopDAO(getContext());
        khachHangDAO = new KhachHangDAO(getContext());
        donHangDAO = new DonHangDAO(getContext());

        QL_DonHang_Loader ql_donHang_loader = new QL_DonHang_Loader(getContext(), recyclerView, count, linearLayout, linearDonHangEmpty, relativeLayout);
        ql_donHang_loader.execute("create");
        listLap = laptopDAO.selectLaptop(null, null, null, null);
        listKH = khachHangDAO.selectKhachHang(null, null, null, null);

        getUser();
        if (nhanVien != null) {
            addDonHang();
        } else {
            addDHButton.setVisibility(View.GONE);
        }
        setTimeCreate();
        onclickChangeTime();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        QL_DonHang_Loader ql_donHang_loader = new QL_DonHang_Loader(getContext(), recyclerView, count, linearLayout, linearDonHangEmpty, relativeLayout);
        ql_donHang_loader.execute("create");
    }

    private void addDonHang() {
        addDHButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nhanVien != null) {
                    if (nhanVien.getRoleNV().equals("Bán hàng Ofline")) {
                        Intent intent = new Intent(getContext(), DonHang_Manager_Activity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Bạn không nằm trong bộ phận bán hàng Ofline!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getUser() {
        SharedPreferences pref = getContext().getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            nhanVien = null;
        } else {
            String user = pref.getString("who", "");
            NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
            ArrayList<NhanVien> list = nhanVienDAO.selectNhanVien(null, "maNV=?", new String[]{user}, null);
            if (list.size() > 0) {
                nhanVien = list.get(0);
            }
        }
    }

    private void setTimeCreate() {
        changeType = new ChangeType();
        Date currentTime = Calendar.getInstance().getTime();
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(currentTime));
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(currentTime));
        if (month < 10) {
            getMonth = "0" + (month);
        } else {
            getMonth = String.valueOf(month);
        }
        textView_Date.setText("Tháng " + getMonth + "/" + year);
        setDonHang(changeType.intDateToStringDate(month, year));
    }

    private String[] onclickChangeTime() {
        Calendar calendar = Calendar.getInstance();
        change_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month += 1;
                        if (month < 10) {
                            getMonth = "0" + month;
                        } else {
                            getMonth = String.valueOf(month);
                        }
                        textView_Date.setText("Tháng " + getMonth + "/" + year);
                        setDonHang(changeType.intDateToStringDate(month, year));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        return getDate;
    }

    private void setDonHang(String[] time) {
        if (time != null) {
            listDon = donHangDAO.selectDonHang(null, "trangThai=? and ngayMua>=? and ngayMua<?", new String[]{"Hoàn thành", time[0], time[1]}, "ngayMua");
            if (listDon != null) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                QL_DonHang_Adapter ql_donHang_adapter = new QL_DonHang_Adapter(listLap, listDon, listKH, getContext(), count);
                recyclerView.setAdapter(ql_donHang_adapter);
                count.setText(String.valueOf(listDon.size()));
            }
        }
    }
}
