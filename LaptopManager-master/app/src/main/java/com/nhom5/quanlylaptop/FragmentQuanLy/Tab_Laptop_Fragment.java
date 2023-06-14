package com.nhom5.quanlylaptop.FragmentQuanLy;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nhom5.quanlylaptop.ActivityNV_Admin.Laptop_Manager_Activity;
import com.nhom5.quanlylaptop.DAO.HangLaptopDAO;
import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.Entity.HangLaptop;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_KhachHang_Adapter;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_Laptop_Adapter;
import com.nhom5.quanlylaptop.NVA_Loader.QL_KhachHang_Loader;
import com.nhom5.quanlylaptop.NVA_Loader.QL_Laptop_Loader;
import com.nhom5.quanlylaptop.R;

import java.util.ArrayList;

import me.ibrahimsn.lib.CirclesLoadingView;

public class Tab_Laptop_Fragment extends Fragment {

    FloatingActionButton themLaptop;
    String TAG = "Tab_Laptop_Fragment_____";
    QL_Laptop_Loader QL_laptop_loader;
    RecyclerView reView;
    String hangL = "all";
    LinearLayout loadingView, linearLayoutEmpty;
    RelativeLayout relativeLayout, layoutLaptop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_laptop, container, false);
        themLaptop = view.findViewById(R.id.button_Add_Laptop);
        loadingView = view.findViewById(R.id.loadingView);
        relativeLayout = view.findViewById(R.id.layoutView);
        layoutLaptop = view.findViewById(R.id.layoutLaptop);
        reView = view.findViewById(R.id.recyclerView_Laptop);
        linearLayoutEmpty = view.findViewById(R.id.linearLaptopEmpty);

        useToolbar(view);
        getHangLaptop(view);
        setLaptop();

        themLaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Laptop_Manager_Activity.class));
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setLaptop();
    }

    private void useToolbar(View view) {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar_Account);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (toolbar != null) {
            LaptopDAO laptopDAO = new LaptopDAO(getContext());
            HangLaptopDAO hangLaptopDAO = new HangLaptopDAO(getContext());
            ArrayList<Laptop> listLap = laptopDAO.selectLaptop(null, null, null, null);
            ArrayList<HangLaptop> listHang = hangLaptopDAO.selectHangLaptop(null, null, null, null);
            ImageButton btn_search = toolbar.findViewById(R.id.imageButton_Search_Toolbar);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inft = ((Activity) getContext()).getLayoutInflater();
            View dialogView = inft.inflate(R.layout.dialog_text_find, null);
            AppCompatButton buttonSearch = dialogView.findViewById(R.id.button_Search_Dialog);
            EditText editTextSearch = dialogView.findViewById(R.id.editText_Search);
            editTextSearch.setHint("Tên Laptop...");

            builder.setView(dialogView);
            Dialog dialog = builder.create();
            btn_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.show();
                    if (editTextSearch.getHint().equals("Tên Laptop...")){
                        editTextSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                String input = editTextSearch.getText().toString();
                                ArrayList<Laptop> getList = new ArrayList<>();
                                if (!input.equals("")) {
                                    for (Laptop lap : listLap) {
                                        if (lap.getTenLaptop().matches(".*?" + input + ".*")) {
                                            getList.add(lap);
                                        }
                                    }
                                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
                                    reView.setLayoutManager(mLayoutManager);
                                    QL_Laptop_Adapter ql_laptop_adapter = new QL_Laptop_Adapter(getList, listHang, getContext(), Tab_Laptop_Fragment.this);
                                    reView.setAdapter(ql_laptop_adapter);
                                } else {
                                    getHangLaptop(view);
                                    setLaptop();
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

    private void getHangLaptop(View view) {
        AppCompatButton all = view.findViewById(R.id.button_Laptop_All);
        AppCompatButton acer = view.findViewById(R.id.button_Laptop_Acer);
        AppCompatButton asus = view.findViewById(R.id.button_Laptop_Asus);
        AppCompatButton dell = view.findViewById(R.id.button_Laptop_Dell);
        AppCompatButton hp = view.findViewById(R.id.button_Laptop_Hp);
        AppCompatButton msi = view.findViewById(R.id.button_Laptop_Msi);
        AppCompatButton mac = view.findViewById(R.id.button_Laptop_Mac);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangL = "all";
                setLaptop();
            }
        });

        acer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangL = "LAcer";
                setLaptop();
            }
        });

        asus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangL = "LAsus";
                setLaptop();
            }
        });

        dell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangL = "LDell";
                setLaptop();
            }
        });

        hp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangL = "LHP";
                setLaptop();
            }
        });

        msi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangL = "LMSi";
                setLaptop();
            }
        });

        mac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangL = "LMacBook";
                setLaptop();
            }
        });
    }

    private void setLaptop() {
        if ("all".equals(hangL)) {
            QL_laptop_loader = new QL_Laptop_Loader(getContext(), reView, loadingView, linearLayoutEmpty, relativeLayout, layoutLaptop, Tab_Laptop_Fragment.this);
            QL_laptop_loader.execute("all");
        } else {
            QL_laptop_loader = new QL_Laptop_Loader(getContext(), reView, loadingView, linearLayoutEmpty, relativeLayout, layoutLaptop, Tab_Laptop_Fragment.this);
            QL_laptop_loader.execute(hangL);
        }
    }
}