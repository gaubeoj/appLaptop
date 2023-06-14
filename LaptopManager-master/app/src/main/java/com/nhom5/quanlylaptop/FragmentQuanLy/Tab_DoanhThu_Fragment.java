package com.nhom5.quanlylaptop.FragmentQuanLy;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.nhom5.quanlylaptop.DAO.DonHangDAO;
import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.Entity.DonHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.GetData;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Tab_DoanhThu_Fragment extends Fragment implements OnChartValueSelectedListener {

    PieChart mPieChart;
    GetData getData;
    DonHangDAO donHangDAO;
    LaptopDAO laptopDAO;
    String getMonth;
    LinearLayout changeTime;
    TextView textView_Date, textView_GiaTien;
    ArrayList<DonHang> listDon = new ArrayList<>();
    ArrayList<Laptop> listLap = new ArrayList<>();
    String getDate;
    int khoanThu, khoanChi, slNhap, slBan;
    ChangeType changeType = new ChangeType();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_doanh_thu, container, false);
        mPieChart = view.findViewById(R.id.pieChart);
        changeTime = view.findViewById(R.id.change_Time);
        textView_Date = view.findViewById(R.id.textView_Date);
        textView_GiaTien = view.findViewById(R.id.textView_GiaTien);
        getData = new GetData(getContext());
        donHangDAO = new DonHangDAO(getContext());
        laptopDAO = new LaptopDAO(getContext());

        setDoanhThuCreate();
        onclickChangeTime();
        setUpPieChart();
        return view;
    }

    private void tinhThuChi() {
        listDon = donHangDAO.selectDonHang(null, "trangThai=?", new String[]{"Hoàn thành"}, null);
        listLap = laptopDAO.selectLaptop(null, null, null, null);

        khoanThu = getData.tinhTongKhoanThu(listDon);
        khoanChi = getData.tinhTongKhoanChi(listLap);
        slNhap = getData.tinhSoLuongNhapVe(listLap);
        slBan = getData.tinhSoLuongDaBan(listLap);
    }

    private void setUpPieChart() {
        Description desc = new Description();
        desc.setText("");
        mPieChart.setRotationEnabled(true);
        mPieChart.setDescription(desc);
        mPieChart.getDescription().setPosition(200, 100);
        mPieChart.getDescription().setTextSize(23);
        mPieChart.setHoleRadius(15f);
        mPieChart.setTransparentCircleAlpha(0);
        mPieChart.setDrawEntryLabels(false);
        addDataSet(mPieChart);
        mPieChart.setOnChartValueSelectedListener(this);
    }

    private void addDataSet(PieChart pieChart) {
        tinhThuChi();
        float khoanThuPer = khoanThu * 100;
        khoanThuPer = khoanThuPer / (khoanChi + khoanThu);
        float khoanChiPer = khoanChi * 100;
        khoanChiPer = khoanChiPer / (khoanChi + khoanThu);
        Log.d("TAG", "addDataSet: thu per " + khoanThuPer);
        Log.d("TAG", "addDataSet: chi per " + khoanChiPer);
        String khoanThuString = changeType.stringToStringMoney(khoanThu + "000");
        String khoanChiString = changeType.stringToStringMoney(khoanChi + "000");


        float[] yData = {khoanThuPer, khoanChiPer};
        String[] xData = {"Khoản thu: " + khoanThuString, "Khoản chi: " + khoanChiString};
        ArrayList<PieEntry> entrys = new ArrayList<>();
        for (int i = 0; i < yData.length; i++) {
            entrys.add(new PieEntry(yData[i], xData[i]));
        }
        PieDataSet pieDataSet = new PieDataSet(entrys, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(23);
        pieDataSet.setValueTextColor(Color.WHITE);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#ef5261"));
        colors.add(Color.parseColor("#f6c863"));
        pieDataSet.setColors(colors);
        Legend legend = pieChart.getLegend();
        legend.setTextSize(20);
        legend.setYOffset(5f);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setXEntrySpace(17);
        legend.setDrawInside(false);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void setDoanhThuCreate() {
        Date currentTime = Calendar.getInstance().getTime();
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(currentTime));
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(currentTime));
        if (month < 10) {
            getMonth = "0" + (month);
        } else {
            getMonth = String.valueOf(month);
        }
        textView_Date.setText("Tháng " + getMonth + "/" + year);
        setDoanhThu(changeType.intDateToStringDate(month, year));
    }

    private void setDoanhThu(String[] time) {
        if (time != null) {
            listDon = donHangDAO.selectDonHang(null, "ngayMua>=? and ngayMua<? and trangThai=?", new String[]{time[0], time[1], "Hoàn thành"}, null);
            if (listDon != null) {
                if (listDon.size() > 0) {
                    int doanhThu = getData.tinhTongKhoanThu(listDon) * 1000;
                    textView_GiaTien.setText(changeType.stringToStringMoney(doanhThu + ""));
                } else {
                    textView_GiaTien.setText(changeType.stringToStringMoney("0"));
                }
            } else {
                textView_GiaTien.setText(changeType.stringToStringMoney("0"));
            }
        }
    }

    private String onclickChangeTime() {
        Calendar calendar = Calendar.getInstance();
        changeTime.setOnClickListener(new View.OnClickListener() {
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
                        setDoanhThu(changeType.intDateToStringDate(month, year));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        return getDate;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        PieEntry pe = (PieEntry) e;
        if (pe.getLabel().matches("^Khoản thu:.*")) {
            Toast.makeText(getContext(), "Số sản phẩm đã bán:\n" + slBan + " sản phẩm", Toast.LENGTH_SHORT).show();
        }
        if (pe.getLabel().matches("^Khoản chi:.*")) {
            Toast.makeText(getContext(), "Số sản phẩm đã nhập:\n" + slNhap + " sản phẩm", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected() {

    }
}