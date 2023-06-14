package com.nhom5.quanlylaptop.KH_Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.quanlylaptop.DAO.DonHangDAO;
import com.nhom5.quanlylaptop.DAO.GiaoDichDAO;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.DAO.ThongBaoDAO;
import com.nhom5.quanlylaptop.DAO.ViTienDAO;
import com.nhom5.quanlylaptop.Entity.DonHang;
import com.nhom5.quanlylaptop.Entity.GiaoDich;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.Entity.ThongBao;
import com.nhom5.quanlylaptop.Entity.ViTien;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;
import com.nhom5.quanlylaptop.Support.GetData;

import java.util.ArrayList;

public class All_ThongBao_Adapter extends RecyclerView.Adapter<All_ThongBao_Adapter.AuthorViewHolder> {

    Context context;
    ArrayList<ThongBao> listTB;
    ChangeType changeType = new ChangeType();
    DonHangDAO donHangDAO;
    ThongBaoDAO thongBaoDAO;
    LaptopDAO laptopDAO;
    KhachHangDAO khachHangDAO;
    NhanVien nhanVien;
    GetData getData;
    String TAG = "KH_ThongBao_Adapter_____";

    public All_ThongBao_Adapter(ArrayList<ThongBao> listTB, Context context) {
        this.listTB = listTB;
        this.context = context;
        donHangDAO = new DonHangDAO(context);
        thongBaoDAO = new ThongBaoDAO(context);
        laptopDAO = new LaptopDAO(context);
        khachHangDAO = new KhachHangDAO(context);
        getData = new GetData(context);
        getUser();
    }

    @NonNull
    @Override
    public All_ThongBao_Adapter.AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup vGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_thongbao, vGroup, false);
        return new All_ThongBao_Adapter.AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull All_ThongBao_Adapter.AuthorViewHolder author, @SuppressLint("RecyclerView") final int pos) {
        ThongBao thongBao = setRow(pos, author);

        xacNhanNapTien(author, thongBao);
    }

    @Override
    public int getItemCount() {
        return listTB.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {
        TextView title, chiTiet, date;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_Title_Notifi);
            chiTiet = itemView.findViewById(R.id.textView_Content_Notifi);
            date = itemView.findViewById(R.id.textView_Date_Notifi);
        }
    }

    public ThongBao setRow(int pos, @NonNull All_ThongBao_Adapter.AuthorViewHolder author) {
        Log.d(TAG, "setRow: " + pos);
        ThongBao thongBao = listTB.get(pos);
        author.title.setText(thongBao.getTitle());
        author.chiTiet.setText(thongBao.getChiTiet());
        author.date.setText(thongBao.getNgayTB());
        return thongBao;
    }

    private void getUser() {
        SharedPreferences pref = context.getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            nhanVien = null;
        } else {
            String user = pref.getString("who", "");
            NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
            ArrayList<NhanVien> list = nhanVienDAO.selectNhanVien(null, "maNV=?", new String[]{user}, null);
            if (list.size() > 0) {
                nhanVien = list.get(0);
            }
        }
    }

    private void xacNhanNapTien(@NonNull All_ThongBao_Adapter.AuthorViewHolder author, ThongBao thongBao) {
        if (thongBao.getTitle().matches("Yêu cầu nạp tiền.*")) {
            author.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String maKH = String.valueOf(changeType.stringMoneyToInt(thongBao.getTitle()));
                    int soTienNap = changeType.stringMoneyToInt(thongBao.getChiTiet());
                    KhachHang khachHang = null;

                    ArrayList<KhachHang> listKH = khachHangDAO.selectKhachHang(null, "maKH=?", new String[]{maKH}, null);
                    if (listKH.size() > 0) {
                        khachHang = listKH.get(0);
                    }

                    if (khachHang != null) {
                        android.app.AlertDialog.Builder aBuild = new android.app.AlertDialog.Builder(context);
                        aBuild.setTitle(thongBao.getTitle());
                        aBuild.setMessage("Xác nhận nạp " + changeType.stringToStringMoney(soTienNap + "") +
                                " vào ví FPT Pay của khách hàng " + changeType.fullNameKhachHang(khachHang));
                        KhachHang finalKhachHang = khachHang;
                        aBuild.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onClick(DialogInterface dialog, int stt) {
                                ViTienDAO viTienDAO = new ViTienDAO(context);
                                ArrayList<ViTien> listVi = viTienDAO.selectViTien(null, "maKH=?", new String[]{maKH}, null);
                                ViTien viTien = null;
                                if (listVi.size() > 0) {
                                    viTien = listVi.get(0);
                                }

                                if (viTien != null) {
                                    viTien.setSoTien(changeType.stringToStringMoney(String.valueOf(changeType.stringMoneyToInt(viTien.getSoTien()) + soTienNap)));
                                    int check = viTienDAO.updateViTien(viTien);
                                    if (check == 1) {
                                        thongBaoDAO.deleteThongBao(thongBao, "ad");

                                        GiaoDichDAO giaoDichDAO = new GiaoDichDAO(context);
                                        giaoDichDAO.insertGiaoDich(new GiaoDich("", viTien.getMaVi(), "Nạp tiền",
                                                "Nạp tiền vào ví FPT Pay", changeType.stringToStringMoney(soTienNap + ""), getData.getNowDateSQL()));

                                        ThongBao thongBaoAD = new ThongBao("TB", "ad", "Đã xác nhận nạp tiền",
                                                " Bạn đã xác nhận nạp " + changeType.stringToStringMoney(soTienNap + "")
                                                        + " vào ví FPT Pay của khách hàng " + changeType.fullNameKhachHang(finalKhachHang), getData.getNowDateSQL());
                                        thongBaoDAO.insertThongBao(thongBaoAD, "ad");
                                        Toast.makeText(context, "Đã phê duyệt", Toast.LENGTH_SHORT).show();

                                        ThongBao thongBaoKH = new ThongBao("TB", maKH, "Nạp tiền",
                                                " Admin đã xác nhận nạp " + changeType.stringToStringMoney(soTienNap + "")
                                                        + " vào ví FPT Pay của bạn.\n Vào ví FPT Pay để kiểm tra ngay thôi nào!", getData.getNowDateSQL());
                                        thongBaoDAO.insertThongBao(thongBaoKH, "kh");
                                        listTB.clear();
                                        listTB = thongBaoDAO.selectThongBao(null, null, null, "ngayTB DESC", "ad");
                                        notifyDataSetChanged();
                                    }
                                }
                            }
                        });
                        aBuild.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int stt) {

                            }
                        });
                        android.app.AlertDialog alertDialog = aBuild.create();
                        alertDialog.show();
                    }
                }
            });


        }
    }
}