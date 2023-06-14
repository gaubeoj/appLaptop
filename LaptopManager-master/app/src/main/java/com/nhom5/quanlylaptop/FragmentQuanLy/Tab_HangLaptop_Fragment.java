package com.nhom5.quanlylaptop.FragmentQuanLy;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.nhom5.quanlylaptop.NVA_Loader.QL_HangLaptop_Loader;
import com.nhom5.quanlylaptop.R;

public class Tab_HangLaptop_Fragment extends Fragment {

    String TAG = "Tab_HangLaptop_Fragment_____";
    LinearLayout linearLayout;
    GridView gridView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_hang_laptop, container, false);
        linearLayout = view.findViewById(R.id.loadingView);
        gridView = view.findViewById(R.id.gridView_hangLaptop);

        QL_HangLaptop_Loader QL_hangLaptop_loader = new QL_HangLaptop_Loader(getContext(), gridView, linearLayout);
        QL_hangLaptop_loader.execute("");

        return view;
    }

}