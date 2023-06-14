package com.nhom5.quanlylaptop.KH_Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.quanlylaptop.Activity.Info_Laptop_Activity;
import com.nhom5.quanlylaptop.DAO.GioHangDAO;
import com.nhom5.quanlylaptop.DAO.ThongBaoDAO;
import com.nhom5.quanlylaptop.Entity.GioHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.Entity.ThongBao;
import com.nhom5.quanlylaptop.FragmentKH.KH_GioHang_Fragment;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class KH_GioHang_Adapter extends RecyclerView.Adapter<KH_GioHang_Adapter.AuthorViewHolder> {

    Context context;
    ArrayList<Laptop> listLap;
    ArrayList<GioHang> listGio;
    GioHangDAO gioHangDAO;
    KH_GioHang_Fragment kh_gioHang_fragment;
    ChangeType changeType = new ChangeType();
    TextView totalTV;
    int total;
    String maKH;
    String TAG = "KH_GioHang_Adapter_____";

    public KH_GioHang_Adapter(ArrayList<Laptop> listLap, ArrayList<GioHang> listGio, Context context, KH_GioHang_Fragment kh_gioHang_fragment) {
        this.listLap = listLap;
        this.listGio = listGio;
        this.context = context;
        this.kh_gioHang_fragment = kh_gioHang_fragment;
        gioHangDAO = new GioHangDAO(context);
        getUser();
    }

    @NonNull
    @Override
    public KH_GioHang_Adapter.AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup vGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_gio_hang, vGroup, false);
        return new KH_GioHang_Adapter.AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KH_GioHang_Adapter.AuthorViewHolder author, @SuppressLint("RecyclerView") final int pos) {
        Laptop laptop = setRow(pos, author, "none");

        if (laptop.getSoLuong() == 0) {
            GioHang gio = listGio.get(pos);
            gioHangDAO.deleteGioHang(gio);

            listGio.clear();
            if (maKH != null) {
                listGio.addAll(gioHangDAO.selectGioHang(null, "maKH=?", new String[]{maKH}, null));
                Date currentTime = Calendar.getInstance().getTime();
                String date = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
                ThongBaoDAO thongBaoDAO = new ThongBaoDAO(context);
                ThongBao thongBao = new ThongBao("TB", maKH, "Quản lý giỏ hàng",
                        " Đơn hàng " + laptop.getTenLaptop() + " đã hết hàng\n Đơn hàng này sẽ bị xóa hỏi giỏ hàng của bạn", date);
                thongBaoDAO.insertThongBao(thongBao, "kh");
                if (listGio.size() == 0) {
                    totalTV.setText(changeType.stringToStringMoney("0"));
                }
            }
            notifyDataSetChanged();
        }

        author.giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GioHang gioHang = listGio.get(pos);
                int soLuong = gioHang.getSoLuong();
                if (soLuong > 1) {
                    soLuong--;
                    gioHang.setmaVou("false");
                    gioHang.setSoLuong(soLuong);
                    gioHangDAO.updateGioHang(gioHang);
                    if (maKH != null) {
                        listGio = gioHangDAO.selectGioHang(null, "maKH=?", new String[]{maKH}, null);
                        setRow(pos, author, "down");
                    }
                } else {
                    Toast.makeText(context, "!!!Tối thiểu một sản phẩm trong giỏ hàng!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        author.tang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GioHang gioHang = listGio.get(pos);
                int soLuong = gioHang.getSoLuong();
                if (soLuong < laptop.getSoLuong()) {
                    soLuong++;
                    gioHang.setmaVou("false");
                    gioHang.setSoLuong(soLuong);
                    gioHangDAO.updateGioHang(gioHang);
                    if (maKH != null) {
                        listGio = gioHangDAO.selectGioHang(null, "maKH=?", new String[]{maKH}, null);
                        setRow(pos, author, "up");
                    }
                } else {
                    Toast.makeText(context, "!!!Không thể vượt quá số lượng sản phẩm có sẵn!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        author.delete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                GioHang gio = listGio.get(pos);
                gioHangDAO.deleteGioHang(gio);

                listGio.clear();
                if (maKH != null) {
                    listGio.addAll(gioHangDAO.selectGioHang(null, "maKH=?", new String[]{maKH}, null));
                    Date currentTime = Calendar.getInstance().getTime();
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
                    ThongBaoDAO thongBaoDAO = new ThongBaoDAO(context);
                    ThongBao thongBao = new ThongBao("TB", maKH, "Quản lý giỏ hàng",
                            " Bạn đã xóa Laptop " + laptop.getTenLaptop() + " khỏi giỏ hàng.", date);
                    thongBaoDAO.insertThongBao(thongBao, "kh");
                    if (listGio.size() == 0) {
                        totalTV.setText(changeType.stringToStringMoney("0"));
                    }
                }
                notifyDataSetChanged();
            }
        });

        author.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (laptop != null) {
                    Intent intent = new Intent(context, Info_Laptop_Activity.class);
                    final Bundle bundle = new Bundle();
                    bundle.putBinder("laptop", laptop);
                    Log.d(TAG, "onBindViewHolder: Laptop: " + laptop.toString());
                    intent.putExtras(bundle);
                    intent.putExtra("openFrom", "other");
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Load thông tin sản phẩm lỗi!\nXin vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGio.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLaptop, tang, giam;
        TextView name, gia, soLuong;
        ImageButton delete;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLaptop = itemView.findViewById(R.id.imageView_Laptop);
            name = itemView.findViewById(R.id.textView_TenLaptop);
            gia = itemView.findViewById(R.id.textView_GiaTien);
            soLuong = itemView.findViewById(R.id.textView_Soluong);
            delete = itemView.findViewById(R.id.imageView_Delete);
            tang = itemView.findViewById(R.id.imageButton_Tang);
            giam = itemView.findViewById(R.id.imageButton_Giam);
        }
    }

    private void getUser() {
        SharedPreferences pref = context.getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            maKH = null;
        } else {
            maKH = pref.getString("who", "");
        }
    }

    public String setTotal(int giaTien, String change) {
        if (change.equals("down")) {
            total = total - giaTien;
        } else {
            total = total + giaTien;
        }
        Log.d(TAG, "setTotal: total: " + total);
        Log.d(TAG, "setTotal: total String: " + changeType.stringToStringMoney(total + "000"));
        return changeType.stringToStringMoney(total + "000");
    }

    public Laptop setRow(int pos, @NonNull KH_GioHang_Adapter.AuthorViewHolder author, String change) {
        Log.d(TAG, "setRow: " + pos);
        GioHang gioHang = listGio.get(pos);
        Laptop laptop = new Laptop("No Data", "No Data", "No Data", "No Data", "0", 0, 0, new byte[]{});
        Log.d(TAG, "setRow: GioHang: " + gioHang.toString());

        for (int i = 0; i < listLap.size(); i++) {
            Laptop getLap = listLap.get(i);
            if (gioHang.getMaLaptop().equals(getLap.getMaLaptop())) {
                laptop = getLap;
            }
        }

        Bitmap anhLap = changeType.byteToBitmap(laptop.getAnhLaptop());
        int giaTien;
        if (laptop.getGiaTien().length() < 12){
            giaTien = changeType.stringMoneyToInt(laptop.getGiaTien()) / 1000;
        } else {
            giaTien = changeType.stringMoneyToInt(laptop.getGiaTien());
        }
        int tongTien = giaTien * gioHang.getSoLuong();

        author.imgLaptop.setImageBitmap(anhLap);
        author.name.setText(laptop.getTenLaptop());
        author.gia.setText(changeType.stringToStringMoney(tongTien + "000"));
        author.soLuong.setText(String.valueOf(gioHang.getSoLuong()));

        //Total
        totalTV = kh_gioHang_fragment.getActivity().findViewById(R.id.textView_Total);
        if (pos == 0) {
            totalTV.setText("0₫");
        }

        if (change.equals("none")) {
            totalTV.setText(setTotal(tongTien, change));
        } else {
            totalTV.setText(setTotal(giaTien, change));
        }

        return laptop;
    }

}