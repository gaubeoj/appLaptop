package com.nhom5.quanlylaptop.KH_Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.quanlylaptop.Activity.Info_Laptop_Activity;
import com.nhom5.quanlylaptop.ActivityKH.KH_ThanhToan_Activity;
import com.nhom5.quanlylaptop.ActivityKH.KH_Voucher_Activity;
import com.nhom5.quanlylaptop.DAO.DonHangDAO;
import com.nhom5.quanlylaptop.DAO.GiaoDichDAO;
import com.nhom5.quanlylaptop.DAO.GioHangDAO;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.DAO.ThongBaoDAO;
import com.nhom5.quanlylaptop.DAO.UseVoucherDAO;
import com.nhom5.quanlylaptop.DAO.ViTienDAO;
import com.nhom5.quanlylaptop.DAO.VoucherDAO;
import com.nhom5.quanlylaptop.Entity.DonHang;
import com.nhom5.quanlylaptop.Entity.GiaoDich;
import com.nhom5.quanlylaptop.Entity.GioHang;
import com.nhom5.quanlylaptop.Entity.IdData;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.Entity.ThongBao;
import com.nhom5.quanlylaptop.Entity.UseVoucher;
import com.nhom5.quanlylaptop.Entity.ViTien;
import com.nhom5.quanlylaptop.Entity.Voucher;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;
import com.nhom5.quanlylaptop.Support.GetData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class KH_ThanhToan_Adapter extends RecyclerView.Adapter<KH_ThanhToan_Adapter.AuthorViewHolder> {

    Context context;
    ArrayList<Laptop> listLap;
    ArrayList<GioHang> listGio;
    ArrayList<Voucher> listVou;
    String TAG = "KH_Laptop_Adapter_____";
    ChangeType changeType = new ChangeType();
    KH_ThanhToan_Activity khThanhToanActivity;
    TextView tongTienHang_View, voucherGiamGia_View, tongThanhToan_View, total_View;
    int giaTien, tongTienHang, tienGiamGia;
    KhachHang khachHang;
    ThongBaoDAO thongBaoDAO;
    GioHangDAO gioHangDAO;
    LaptopDAO laptopDAO;
    UseVoucherDAO useVoucherDAO;
    String maVou;
    GetData getData;
    ArrayList<Integer> listTotal = new ArrayList<>();

    public KH_ThanhToan_Adapter(ArrayList<Laptop> listLap, ArrayList<GioHang> listGio, ArrayList<Voucher> listVou,
                                Context context, KH_ThanhToan_Activity khThanhToanActivity) {
        this.context = context;
        this.listLap = listLap;
        this.listGio = listGio;
        this.listVou = listVou;
        this.khThanhToanActivity = khThanhToanActivity;
        thongBaoDAO = new ThongBaoDAO(context);
        gioHangDAO = new GioHangDAO(context);
        laptopDAO = new LaptopDAO(context);
        getData = new GetData(context);
        useVoucherDAO = new UseVoucherDAO(context);
        getUserKH();
    }

    @NonNull
    @Override
    public KH_ThanhToan_Adapter.AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup vGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_kh_thanhtoan, vGroup, false);
        return new KH_ThanhToan_Adapter.AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KH_ThanhToan_Adapter.AuthorViewHolder author, @SuppressLint("RecyclerView") final int pos) {
        setRow(pos, author);

        if (khThanhToanActivity != null) {
            Button datHang = khThanhToanActivity.findViewById(R.id.button_DatHang);
            datHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclickDatHang();
                }
            });
        }

        author.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Info_Laptop_Activity.class);
                Laptop laptop = listLap.get(pos);
                if (laptop != null) {
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
        ImageView imgLaptop;
        TextView name, gia, soLuong, date, sale, total, countSP;
        RelativeLayout clickVoucher;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLaptop = itemView.findViewById(R.id.imageView_Laptop);
            name = itemView.findViewById(R.id.textView_TenLaptop);
            gia = itemView.findViewById(R.id.textView_GiaTien);
            soLuong = itemView.findViewById(R.id.textView_Soluong);
            date = itemView.findViewById(R.id.textView_Date);
            sale = itemView.findViewById(R.id.textView_GiamGia);
            total = itemView.findViewById(R.id.textView_SoTien);
            countSP = itemView.findViewById(R.id.textView_TongSP);
            clickVoucher = itemView.findViewById(R.id.onclick_Doi_Voucher);
        }
    }

    private void getUserKH() {
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

    public Laptop setRow(int pos, @NonNull KH_ThanhToan_Adapter.AuthorViewHolder author) {
        Log.d(TAG, "setRow: " + pos);
        GioHang gioHang = listGio.get(pos);
        Log.d(TAG, "setRow: GioHang: " + gioHang.toString());

        Laptop laptop = new Laptop("No Data", "No Data", "No Data", "No Data", "0", 0, 0, new byte[]{});

        for (int i = 0; i < listLap.size(); i++) {
            Laptop getLap = listLap.get(i);
            if (gioHang.getMaLaptop().equals(getLap.getMaLaptop())) {
                laptop = getLap;
            }
        }

        Voucher voucher = null;

        for (int i = 0; i < listVou.size(); i++) {
            Voucher getVou = listVou.get(i);
            if (gioHang.getMaVou().equals(getVou.getMaVoucher())) {
                voucher = getVou;
            }
        }

        Bitmap anhLap = changeType.byteToBitmap(laptop.getAnhLaptop());
        if (laptop.getGiaTien().length() < 12) {
            giaTien = changeType.stringMoneyToInt(laptop.getGiaTien()) / 1000;
        } else {
            giaTien = changeType.stringMoneyToInt(laptop.getGiaTien());
        }
        giaTien = giaTien * gioHang.getSoLuong();
        Date currentTime = Calendar.getInstance().getTime();
        String date = new SimpleDateFormat("dd/MM/yyyy").format(currentTime);

        author.date.setText(date);
        author.imgLaptop.setImageBitmap(anhLap);
        author.name.setText(laptop.getTenLaptop());
        author.gia.setText(changeType.stringToStringMoney(giaTien + "000"));
        author.soLuong.setText(String.valueOf(gioHang.getSoLuong()));
        author.total.setText(changeType.stringToStringMoney(giaTien + "000"));
        author.countSP.setText("Tổng số tiền: (" + gioHang.getSoLuong() + " sản phẩm)");

        if (voucher != null) {
            int sale = changeType.voucherToInt(voucher.getGiamGia());
            int giamTien = (giaTien * sale) / 100;
            int totalDh = giaTien - giamTien;
            listTotal.add(totalDh);

            Log.d(TAG, "setRow: giaTien = " + giaTien);
            Log.d(TAG, "setRow: giamTien = " + giamTien);
            Log.d(TAG, "setRow: totalDh = " + totalDh);
            author.sale.setText("-" + changeType.stringToStringMoney(giamTien + "000"));
            author.sale.setTextColor(Color.parseColor("#FF5722"));
            author.total.setText(changeType.stringToStringMoney(totalDh + "000"));
            setThanhToan(pos, giaTien, giamTien);
        } else {
            String[] two = getVoucher(author.sale, giaTien);
            maVou = two[0];
            gioHang.setmaVou(maVou);
            int giamTien = changeType.stringMoneyToInt(two[1]);
            int tongTien = giaTien - giamTien;
            listTotal.add(tongTien);

            Log.d(TAG, "setRow: giaTien = " + giaTien);
            Log.d(TAG, "setRow: giamTien = " + giamTien);
            Log.d(TAG, "setRow: tongTien = " + tongTien);
            author.total.setText(changeType.stringToStringMoney(tongTien + "000"));
            setThanhToan(pos, giaTien, giamTien);
        }

        author.clickVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, KH_Voucher_Activity.class);
                intent.putExtra("openFrom", "ThanhToan");
                if (!gioHang.getMaGio().equals("No Data")) {
                    intent.putExtra("posLap", pos);
                    IdData.getInstance().setIdVou(gioHang.getMaVou());
                    ArrayList<UseVoucher> listUS = useVoucherDAO.selectUseVoucher(null, "maKH=?", new String[]{khachHang.getMaKH()}, null);
                    if (listUS.size() > 0) {
                        for (UseVoucher use : listUS) {
                            if (use.getMaVoucher().equals(gioHang.getMaVou())) {
                                use.setIsUsed("truen't");
                            } else {
                                if (use.getIsUsed().equals("truen't")) {
                                    use.setIsUsed("false");
                                }
                            }
                        }
                    }
                } else {
                    intent.putExtra("posLap", -1);
                    IdData.getInstance().setIdVou(maVou);
                    ArrayList<UseVoucher> listUS = useVoucherDAO.selectUseVoucher(null, "maKH=?", new String[]{khachHang.getMaKH()}, null);
                    if (listUS.size() > 0) {
                        for (UseVoucher use : listUS) {
                            if (use.getMaVoucher().equals(maVou)) {
                                use.setIsUsed("truen't");
                                useVoucherDAO.updateUseVoucher(use);
                            } else {
                                if (use.getIsUsed().equals("truen't")) {
                                    use.setIsUsed("false");
                                    useVoucherDAO.updateUseVoucher(use);
                                }
                            }
                        }
                    }
                }
                context.startActivity(intent);
            }
        });

        return laptop;
    }

    private void onclickDatHang() {
        if (listGio.size() > 0) {
            int thToan = 0;
            for (int pos = 0; pos < listGio.size(); pos++) {
                GioHang gio = listGio.get(pos);
                int money = listTotal.get(pos);
                Laptop laptop = null;
                for (int i = 0; i < listLap.size(); i++) {
                    Laptop getLap = listLap.get(i);
                    if (gio.getMaLaptop().equals(getLap.getMaLaptop())) {
                        laptop = getLap;
                    }
                }
                Voucher voucher = null;
                for (int i = 0; i < listVou.size(); i++) {
                    Voucher getVou = listVou.get(i);
                    if (gio.getMaVou().equals(getVou.getMaVoucher())) {
                        voucher = getVou;
                    }
                }

                TextView textViewDC = khThanhToanActivity.findViewById(R.id.textView_DiaChi);
                TextView textViewHTTT = khThanhToanActivity.findViewById(R.id.textView_HinhThucTT);
                String diaChi = textViewDC.getText().toString();
                String httt = textViewHTTT.getText().toString();

                if (diaChi.isEmpty()) {
                    Toast.makeText(context, "Địa chỉ không được để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (httt.isEmpty()) {
                    Toast.makeText(context, "Hình thức thanh toán không được trống!", Toast.LENGTH_SHORT).show();
                    return;
                }

                getUserKH();
                final String[] arrHTTT = context.getResources().getStringArray(R.array.httt_array);
                ViTienDAO viTienDAO = new ViTienDAO(context);
                ArrayList<ViTien> listV = viTienDAO.selectViTien(null, "maKH=?", new String[]{khachHang.getMaKH()}, null);
                ViTien getVi = null;
                if (listV.size() > 0) {
                    getVi = listV.get(0);
                }

                if (laptop != null && voucher != null && khachHang != null) {
                    if (httt.equals(arrHTTT[0])) {
                        DonHang donHang = new DonHang("", "No Data", khachHang.getMaKH(), laptop.getMaLaptop(),
                                voucher.getMaVoucher(), "No Data", diaChi, getData.getNowDateSQL(), httt, "Chờ xác nhận",
                                "false", changeType.stringToStringMoney(money + "000"), gio.getSoLuong());
                        DonHangDAO donHangDAO = new DonHangDAO(context);
                        int check = donHangDAO.insertDonHang(donHang);

                        if (check == 1) {
                            ArrayList<UseVoucher> listUS = useVoucherDAO.selectUseVoucher(null, "maVoucher=? and maKH=?", new String[]{gio.getMaVou(), khachHang.getMaKH()}, null);
                            if (listUS.size() > 0) {
                                UseVoucher useVoucher = listUS.get(0);
                                useVoucher.setIsUsed("true");
                                useVoucherDAO.updateUseVoucher(useVoucher);
                            }

                            ThongBao thongBaoKH = new ThongBao("TB", khachHang.getMaKH(), "Quản lý đơn hàng",
                                    "Bạn đã đặt đơn hàng " + laptop.getTenLaptop() + " với giá " + donHang.getThanhTien(), getData.getNowDateSQL());
                            thongBaoDAO.insertThongBao(thongBaoKH, "kh");

                            ThongBao thongBaoAD = new ThongBao("TB", "admin", "Đặt hàng Online",
                                    " Khách hàng " + changeType.fullNameKhachHang(khachHang) +  " đã đặt đơn hàng " + laptop.getTenLaptop()
                                            + " với giá " + donHang.getThanhTien() + "\n Chi tiết đơn hàng xem ở Màn hình đơn đặt hàng", getData.getNowDateSQL());
                            thongBaoDAO.insertThongBao(thongBaoAD, "ad");

                            ThongBao thongBaoNV = new ThongBao("TB", "Online", "Đặt hàng Online",
                                    " Khách hàng " + changeType.fullNameKhachHang(khachHang) +  " đã đặt đơn hàng " + laptop.getTenLaptop()
                                            + " với giá " + donHang.getThanhTien() + "\n Chi tiết đơn hàng xem ở Màn hình đơn đặt hàng", getData.getNowDateSQL());
                            thongBaoDAO.insertThongBao(thongBaoNV, "nv");
                            thToan = 1;
                        }
                    } else {
                        int tong = 0;
                        for (int tien : listTotal) {
                            tong += tien;
                        }
                        if (getVi != null) {
                            int soDu = changeType.stringMoneyToInt(getVi.getSoTien());
                            if (soDu < (tong * 1000)) {
                                DonHang donHang = new DonHang("", "No Data", khachHang.getMaKH(), laptop.getMaLaptop(),
                                        voucher.getMaVoucher(), "No Data", diaChi, getData.getNowDateSQL(), httt, "Chưa thanh toán",
                                        "false", changeType.stringToStringMoney(money + "000"), gio.getSoLuong());
                                DonHangDAO donHangDAO = new DonHangDAO(context);
                                int check = donHangDAO.insertDonHang(donHang);

                                if (check == 1) {
                                    ArrayList<UseVoucher> listUS = useVoucherDAO.selectUseVoucher(null, "maVoucher=? and maKH=?", new String[]{gio.getMaVou(), khachHang.getMaKH()}, null);
                                    if (listUS.size() > 0) {
                                        UseVoucher useVoucher = listUS.get(0);
                                        useVoucher.setIsUsed("true");
                                        useVoucherDAO.updateUseVoucher(useVoucher);
                                    }

                                    ThongBao thongBaoKH = new ThongBao("TB", khachHang.getMaKH(), "Quản lý đơn hàng",
                                            " Số dư trong ví FPT Pay không đủ để đặt đơn hàng " + laptop.getTenLaptop() + " với giá " + donHang.getThanhTien()
                                                    + "\nĐơn hàng sẽ được chuyển đến khu vực chờ thanh toán trong Ví FPT Pay", getData.getNowDateSQL());
                                    thongBaoDAO.insertThongBao(thongBaoKH, "kh");
                                    thToan++;
                                }
                            } else {
                                DonHang donHang = new DonHang("", "No Data", khachHang.getMaKH(), laptop.getMaLaptop(),
                                        voucher.getMaVoucher(), "No Data", diaChi, getData.getNowDateSQL(), httt, "Chờ xác nhận",
                                        "false", changeType.stringToStringMoney(money + "000"), gio.getSoLuong());
                                DonHangDAO donHangDAO = new DonHangDAO(context);
                                int check = donHangDAO.insertDonHang(donHang);

                                if (check == 1) {
                                    ArrayList<UseVoucher> listUS = useVoucherDAO.selectUseVoucher(null, "maVoucher=? and maKH=?", new String[]{gio.getMaVou(), khachHang.getMaKH()}, null);
                                    if (listUS.size() > 0) {
                                        UseVoucher useVoucher = listUS.get(0);
                                        useVoucher.setIsUsed("true");
                                        useVoucherDAO.updateUseVoucher(useVoucher);
                                    }

                                    setSoDu(getVi, money);
                                    GiaoDichDAO giaoDichDAO = new GiaoDichDAO(context);
                                    giaoDichDAO.insertGiaoDich(new GiaoDich("", getVi.getMaVi(), "Thanh toán đơn hàng",
                                            "Thanh toán đơn hàng " + laptop.getTenLaptop() + " bằng Ví điện tử FPT Pay",
                                            changeType.stringToStringMoney(money + "000"), getData.getNowDateSQL()));

                                    ThongBao thongBaoKH = new ThongBao("TB", khachHang.getMaKH(), "Quản lý đơn hàng",
                                            "Bạn đã đặt đơn hàng " + laptop.getTenLaptop() + " với giá " + donHang.getThanhTien(), getData.getNowDateSQL());
                                    thongBaoDAO.insertThongBao(thongBaoKH, "kh");

                                    ThongBao thongBaoAD = new ThongBao("TB", "admin", "Đặt hàng Online",
                                            " Khách hàng " + changeType.fullNameKhachHang(khachHang) +  " đã đặt đơn hàng " + laptop.getTenLaptop()
                                                    + " với giá " + donHang.getThanhTien() + "\n Chi tiết đơn hàng xem ở Màn hình đơn đặt hàng", getData.getNowDateSQL());
                                    thongBaoDAO.insertThongBao(thongBaoAD, "ad");

                                    ThongBao thongBaoNV = new ThongBao("TB", "Online", "Đặt hàng Online",
                                            " Khách hàng " + changeType.fullNameKhachHang(khachHang) +  " đã đặt đơn hàng " + laptop.getTenLaptop()
                                                    + " với giá " + donHang.getThanhTien() + "\n Chi tiết đơn hàng xem ở Màn hình đơn đặt hàng", getData.getNowDateSQL());
                                    thongBaoDAO.insertThongBao(thongBaoNV, "nv");
                                    thToan = 1;
                                }
                            }
                        } else {
                            Toast.makeText(context, "Bạn chưa tạo ví điện tử FPT Pay!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (laptop != null && khachHang != null) {
                    if (httt.equals(arrHTTT[0])) {
                        Log.d(TAG, "onclickDatHang: Ofline");
                        DonHang donHang = new DonHang("", "No Data", khachHang.getMaKH(), laptop.getMaLaptop(),
                                "Null", "No Data", diaChi, getData.getNowDateSQL(), httt, "Chờ xác nhận",
                                "false", changeType.stringToStringMoney(money + "000"), gio.getSoLuong());
                        DonHangDAO donHangDAO = new DonHangDAO(context);
                        int check = donHangDAO.insertDonHang(donHang);

                        if (check == 1) {
                            ThongBao thongBaoKH = new ThongBao("TB", khachHang.getMaKH(), "Quản lý đơn hàng",
                                    " Bạn đã đặt đơn hàng " + laptop.getTenLaptop() + " với giá " + donHang.getThanhTien(), getData.getNowDateSQL());
                            thongBaoDAO.insertThongBao(thongBaoKH, "kh");

                            ThongBao thongBaoAD = new ThongBao("TB", "admin", "Đặt hàng Online",
                                    " Khách hàng " + changeType.fullNameKhachHang(khachHang) +  " đã đặt đơn hàng " + laptop.getTenLaptop()
                                            + " với giá " + donHang.getThanhTien() + "\n Chi tiết đơn hàng xem ở Màn hình đơn đặt hàng", getData.getNowDateSQL());
                            thongBaoDAO.insertThongBao(thongBaoAD, "ad");

                            ThongBao thongBaoNV = new ThongBao("TB", "Online", "Đặt hàng Online",
                                    " Khách hàng " + changeType.fullNameKhachHang(khachHang) +  " đã đặt đơn hàng " + laptop.getTenLaptop()
                                            + " với giá " + donHang.getThanhTien() + "\n Chi tiết đơn hàng xem ở Màn hình đơn đặt hàng", getData.getNowDateSQL());
                            thongBaoDAO.insertThongBao(thongBaoNV, "nv");
                            thToan = 1;
                        }
                    } else {
                        if (getVi != null) {
                            int soDu = changeType.stringMoneyToInt(getVi.getSoTien());
                            if (soDu < (money * 1000)) {
                                DonHang donHang = new DonHang("", "No Data", khachHang.getMaKH(), laptop.getMaLaptop(),
                                        "Null", "No Data", diaChi, getData.getNowDateSQL(), httt, "Chưa thanh toán",
                                        "false", changeType.stringToStringMoney(money + "000"), gio.getSoLuong());
                                DonHangDAO donHangDAO = new DonHangDAO(context);
                                int check = donHangDAO.insertDonHang(donHang);

                                if (check == 1) {
                                    ThongBao thongBaoKH = new ThongBao("TB", khachHang.getMaKH(), "Quản lý đơn hàng",
                                            " Số dư trong ví FPT Pay không đủ để đặt đơn hàng " + laptop.getTenLaptop() + " với giá " + donHang.getThanhTien()
                                                    + "\nĐơn hàng sẽ được chuyển đến khu vực chờ thanh toán trong Ví FPT Pay", getData.getNowDateSQL());
                                    thongBaoDAO.insertThongBao(thongBaoKH, "kh");
                                    thToan++;
                                }
                            } else {
                                DonHang donHang = new DonHang("", "No Data", khachHang.getMaKH(), laptop.getMaLaptop(),
                                        "Null", "No Data", diaChi, getData.getNowDateSQL(), httt, "Chờ xác nhận",
                                        "false", changeType.stringToStringMoney(money + "000"), gio.getSoLuong());
                                DonHangDAO donHangDAO = new DonHangDAO(context);
                                int check = donHangDAO.insertDonHang(donHang);

                                if (check == 1) {
                                    setSoDu(getVi, money);
                                    GiaoDichDAO giaoDichDAO = new GiaoDichDAO(context);
                                    giaoDichDAO.insertGiaoDich(new GiaoDich("", getVi.getMaVi(), "Thanh toán đơn hàng",
                                            "Thanh toán đơn hàng " + laptop.getTenLaptop() + " bằng Ví điện tử FPT Pay",
                                            changeType.stringToStringMoney(money + "000"), getData.getNowDateSQL()));

                                    ThongBao thongBaoKH = new ThongBao("TB", khachHang.getMaKH(), "Quản lý đơn hàng",
                                            " Bạn đã đặt đơn hàng " + laptop.getTenLaptop() + " với giá " + donHang.getThanhTien(), getData.getNowDateSQL());
                                    thongBaoDAO.insertThongBao(thongBaoKH, "kh");

                                    ThongBao thongBaoAD = new ThongBao("TB", "admin", "Đặt hàng Online",
                                            " Khách hàng " + changeType.fullNameKhachHang(khachHang) +  " đã đặt đơn hàng " + laptop.getTenLaptop()
                                                    + " với giá " + donHang.getThanhTien() + "\n Chi tiết đơn hàng xem ở Màn hình đơn đặt hàng", getData.getNowDateSQL());
                                    thongBaoDAO.insertThongBao(thongBaoAD, "ad");

                                    ThongBao thongBaoNV = new ThongBao("TB", "Online", "Đặt hàng Online",
                                            " Khách hàng " + changeType.fullNameKhachHang(khachHang) +  " đã đặt đơn hàng " + laptop.getTenLaptop()
                                                    + " với giá " + donHang.getThanhTien() + "\n Chi tiết đơn hàng xem ở Màn hình đơn đặt hàng", getData.getNowDateSQL());
                                    thongBaoDAO.insertThongBao(thongBaoNV, "nv");
                                    thToan++;
                                }
                            }
                        } else {
                            Toast.makeText(context, "Bạn chưa tạo ví điện tử FPT Pay!", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }

            if (thToan > 0) {
                for (GioHang gioHang : listGio) {
                    gioHangDAO.deleteGioHang(gioHang);
                }
                khThanhToanActivity.finish();
            }
        }
    }

    private void setSoDu(ViTien viTien, int money) {
        ViTienDAO viTienDAO = new ViTienDAO(context);
        int soDu;
        if (viTien.getSoTien().length() > 12) {
            soDu = changeType.stringMoneyToInt(viTien.getSoTien());
        } else {
            soDu = changeType.stringMoneyToInt(viTien.getSoTien()) / 1000;
        }
        viTien.setSoTien(changeType.stringToStringMoney((soDu - (money)) + "000"));
        viTienDAO.updateViTien(viTien);
    }

    private void setThanhToan(int pos, int tienHang, int giamTien) {
        tongTienHang_View = khThanhToanActivity.findViewById(R.id.textView_TienHang);
        voucherGiamGia_View = khThanhToanActivity.findViewById(R.id.textView_TienGiamGia);
        tongThanhToan_View = khThanhToanActivity.findViewById(R.id.textView_Total);
        total_View = khThanhToanActivity.findViewById(R.id.textView_Total2);

        if (pos == 0) {
            tongTienHang_View.setText("0₫");
            voucherGiamGia_View.setText("0₫");
            tongThanhToan_View.setText("0₫");
            total_View.setText("0₫");
        }

        tongTienHang += tienHang;
        tienGiamGia += giamTien;
        int tien = tongTienHang - tienGiamGia;

        tongTienHang_View.setText(changeType.stringToStringMoney(tongTienHang + "000"));
        voucherGiamGia_View.setText("-" + changeType.stringToStringMoney(tienGiamGia + "000"));
        tongThanhToan_View.setText(changeType.stringToStringMoney(tien + "000"));
        total_View.setText(changeType.stringToStringMoney(tien + "000"));
    }

    private String[] getVoucher(TextView textView, int giaTien) {
        String maVou = IdData.getInstance().getIdVou();
        String after;
        VoucherDAO voucherDAO = new VoucherDAO(context);
        ArrayList<Voucher> list = voucherDAO.selectVoucher(null, "maVoucher=?", new String[]{maVou}, null);

        if (list.size() > 0) {
            Voucher voucher = list.get(0);
            String giamGia = voucher.getGiamGia();
            if (!giamGia.equals("")) {
                int sale = changeType.voucherToInt(giamGia);
                int giamTien = (giaTien * sale) / 100;

                textView.setText("-" + changeType.stringToStringMoney(giamTien + "000"));
                after = changeType.stringToStringMoney(giamTien + "");
                textView.setTextColor(Color.parseColor("#FF5722"));
            } else {
                textView.setText(R.string.thay_i_m);
                after = changeType.stringToStringMoney("0");
            }
        } else {
            textView.setText(R.string.thay_i_m);
            after = changeType.stringToStringMoney("0");
        }

        return new String[]{maVou, after};
    }

}