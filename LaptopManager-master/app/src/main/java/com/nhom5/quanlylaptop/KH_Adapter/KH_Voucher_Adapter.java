package com.nhom5.quanlylaptop.KH_Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.quanlylaptop.DAO.GioHangDAO;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.UseVoucherDAO;
import com.nhom5.quanlylaptop.Entity.GioHang;
import com.nhom5.quanlylaptop.Entity.IdData;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.UseVoucher;
import com.nhom5.quanlylaptop.Entity.Voucher;
import com.nhom5.quanlylaptop.NAV_Adapter.QL_Voucher_Adapter;
import com.nhom5.quanlylaptop.R;

import java.util.ArrayList;

public class KH_Voucher_Adapter extends RecyclerView.Adapter<KH_Voucher_Adapter.AuthorViewHolder> {

    Context context;
    ArrayList<Voucher> listVou;
    GioHangDAO gioHangDAO;
    UseVoucherDAO useVoucherDAO;
    String TAG = "KH_Voucher_Adapter_____";
    GioHang gioHang;
    KhachHang khachHang;
    int selectedPos = -1;
    UseVoucher useVoucher;

    public KH_Voucher_Adapter(ArrayList<Voucher> listVou, Context context, GioHang gioHang) {
        this.listVou = listVou;
        this.context = context;
        this.gioHang = gioHang;
        gioHangDAO = new GioHangDAO(context);
        useVoucherDAO = new UseVoucherDAO(context);
        getUser();
    }

    @NonNull
    @Override
    public KH_Voucher_Adapter.AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup vGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_kh_voucher, vGroup, false);
        return new KH_Voucher_Adapter.AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KH_Voucher_Adapter.AuthorViewHolder author, @SuppressLint("RecyclerView") final int pos) {
        String maVou = IdData.getInstance().getIdVou();
        Voucher getVou = setRow(pos, author, maVou);

        if (pos == selectedPos) {
            author.buttonUse.setVisibility(View.GONE);
            author.radioButton.setVisibility(View.VISIBLE);
        } else {
            author.buttonUse.setVisibility(View.VISIBLE);
            author.radioButton.setVisibility(View.GONE);
        }

        author.buttonUse.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                if (gioHang != null) {
                    selectedPos = pos;
                    if (!gioHang.getMaGio().equals("Null")) {
                        gioHang.setmaVou(getVou.getMaVoucher());
                        gioHangDAO.updateGioHang(gioHang);
                    } else {
                        IdData.getInstance().setIdVou(getVou.getMaVoucher());
                    }
                    setRow(pos, author, getVou.getMaVoucher());
                    notifyDataSetChanged();
                } else {
                    selectedPos = -1;
                    Toast.makeText(context, "Hãy đặt mua sản phẩm để sử dụng Voucher này nhé!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listVou.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, ma, sale;
        Button buttonUse;
        RadioButton radioButton;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_TenVoucher);
            date = itemView.findViewById(R.id.textView_Date_Voucher);
            ma = itemView.findViewById(R.id.textView_MaVoucher);
            sale = itemView.findViewById(R.id.textView_GiamGia);
            buttonUse = itemView.findViewById(R.id.button_Use_Voucher);
            radioButton = itemView.findViewById(R.id.radioButton_Checked);
        }
    }

    public Voucher setRow(int pos, @NonNull KH_Voucher_Adapter.AuthorViewHolder author, String maVou) {
        Log.d(TAG, "setRow: " + pos);
        Voucher voucher = listVou.get(pos);

        ArrayList<UseVoucher> listUS = useVoucherDAO.selectUseVoucher(null, "maVoucher=? and maKH=?", new String[]{voucher.getMaVoucher(), khachHang.getMaKH()}, null);
        if (listUS.size() > 0) {
            useVoucher = listUS.get(0);
            if (useVoucher.getIsUsed().equals("false")) {
                author.buttonUse.setEnabled(true);
                author.buttonUse.setText("Sử dụng");
            } else {
                author.buttonUse.setEnabled(false);
                author.buttonUse.setText("Đã hết");
            }
        }

        if (maVou != null) {
            if (maVou.equals(voucher.getMaVoucher())) {
                selectedPos = pos;
            }
        }

        author.sale.setText("Giảm giá\n" + voucher.getGiamGia());
        author.name.setText(voucher.getTenVoucher());
        if (voucher.getNgayBD().equals(voucher.getNgayKT())) {
            author.date.setText("Duy nhất trong\n" + voucher.getNgayBD());
        } else {
            author.date.setText("Từ  " + voucher.getNgayBD() + "\nđến " + voucher.getNgayKT());
        }
        return voucher;
    }

    private void getUser() {
        SharedPreferences pref = context.getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            khachHang = null;
        } else {
            String user = pref.getString("who", "");
            KhachHangDAO khachHangDAO = new KhachHangDAO(context);
            ArrayList<KhachHang> list = khachHangDAO.selectKhachHang(null, "maKH=?", new String[]{user}, null);
            if (list.size() > 0) {
                khachHang = list.get(0);
            }
        }
    }
}