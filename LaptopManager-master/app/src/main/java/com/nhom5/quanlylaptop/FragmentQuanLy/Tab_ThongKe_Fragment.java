package com.nhom5.quanlylaptop.FragmentQuanLy;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhom5.quanlylaptop.NVA_Loader.QL_ThongKe_Loader;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tab_ThongKe_Fragment extends Fragment {

    String TAG = "Tab_ThongKe_Fragment_____";
    String getMonth;
    LinearLayout changeTime;
    TextView textView_Date;
    RecyclerView reView;
    LinearLayout linearLayout;
    String[] getDate;
    ChangeType changeType = new ChangeType();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_thong_ke, container, false);
        reView = view.findViewById(R.id.recyclerView_ThongKe);
        linearLayout = view.findViewById(R.id.loadingView);
        changeTime = view.findViewById(R.id.change_Time);
        textView_Date = view.findViewById(R.id.textView_Date);

        setTimeCreate();
        onclickChangeTime();
        QL_ThongKe_Loader qlThongKeLoader = new QL_ThongKe_Loader(getContext(), reView, linearLayout, getDate);
        qlThongKeLoader.execute("");
        return view;
    }

    private void setTimeCreate() {
        Date currentTime = Calendar.getInstance().getTime();
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(currentTime));
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(currentTime));
        if (month < 10) {
            getMonth = "0" + (month);
        } else {
            getMonth = String.valueOf(month);
        }
        textView_Date.setText("Tháng " + getMonth + "/" + year);
        getDate = changeType.intDateToStringDate(month, year);
    }

    private String[] onclickChangeTime() {
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
                        getDate = changeType.intDateToStringDate(month, year);
                        QL_ThongKe_Loader qlThongKeLoader = new QL_ThongKe_Loader(getContext(), reView, linearLayout, getDate);
                        qlThongKeLoader.execute("");
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        return getDate;
    }

}