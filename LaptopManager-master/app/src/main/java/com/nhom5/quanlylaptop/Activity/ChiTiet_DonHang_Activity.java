package com.nhom5.quanlylaptop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.DAO.VoucherDAO;
import com.nhom5.quanlylaptop.Entity.DonHang;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.Entity.Voucher;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class ChiTiet_DonHang_Activity extends AppCompatActivity {

    Context context = this;
    DonHang donHang = null;
    String typeUser = "";
    String TAG = "ChiTiet_DonHang_Activity_____";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);
        useToolbar();
        getInfoDonHang();
        setLaptopView();
        setNhanVien();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
        if (pref != null) {
            String infoWhat = pref.getString("what", "null");
            if (!infoWhat.equals("none")) {
                finish();
            }
        }
    }

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText("Chi tiết sản phẩm");
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getInfoDonHang() {
        Intent intent = getIntent();
        if (intent != null) {
            try {
                donHang = (DonHang) intent.getExtras().getBinder("donhang");
                typeUser = intent.getStringExtra("typeUser");
                Log.d(TAG, "getInfoLaptop: DonHang: " + donHang.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setTrangThai(String trangThai, String typeUser) {
        LinearLayout layoutReview = findViewById(R.id.layout_Review);
        TextView titleReview = findViewById(R.id.textView_TitleReview);
        TextView detailReview = findViewById(R.id.textView_DetailReview);
        ImageView imageReview = findViewById(R.id.imageView_Review);

        if (typeUser.equals("KH")) {
            if (trangThai.equals("Chờ xác nhận")) {
                titleReview.setText("Đơn hàng đang chờ xác nhận!");
                detailReview.setText("Đơn hàng đang chờ được xác nhận. Đơn hàng đang được chờ Nhân viên bán hàng xác nhận đơn hàng để giao cho bạn.");
                layoutReview.setBackgroundColor(Color.parseColor("#FF9800"));
                imageReview.setImageResource(R.drawable.send_icon);
            } else if (trangThai.equals("Đang giao hàng")) {
                titleReview.setText("Đơn hàng đang được giao!");
                detailReview.setText("Đơn hàng đang trên đường giao. Đơn hàng đang được giao đến cho bạn, hãy xác nhận khi bạn nhận được đơn hàng nhé.");
                layoutReview.setBackgroundColor(Color.parseColor("#FF9800"));
                imageReview.setImageResource(R.drawable.icon_delivery_dining);
//            } else if (trangThai.equals("Chưa thanh toán")){
            } else {
                titleReview.setText("Đơn hàng chưa được thanh toán!");
                detailReview.setText("Đơn hàng đang chờ được thanh toán. Bạn hãy sớm thanh toán đơn hàng để chúng tôi giao đơn hàng cho bạn nhé.");
                layoutReview.setBackgroundColor(Color.parseColor("#FF9800"));
                imageReview.setImageResource(R.drawable.waiting_confirm_icon);
            }
        } else {
            if (trangThai.equals("Chờ xác nhận")) {
                titleReview.setText("Đơn hàng đang chờ xác nhận!");
                detailReview.setText("Đơn hàng đang chờ được xác nhận. Đơn hàng đang được chờ Nhân viên bán hàng xác nhận đơn hàng.");
                layoutReview.setBackgroundColor(Color.parseColor("#FF9800"));
                imageReview.setImageResource(R.drawable.send_icon);
            } else if (trangThai.equals("Đang giao hàng")) {
                titleReview.setText("Đơn hàng đang được giao!");
                detailReview.setText("Đơn hàng đang trên đường giao. Đơn hàng đang được giao đến cho khách hàng.");
                layoutReview.setBackgroundColor(Color.parseColor("#FF9800"));
                imageReview.setImageResource(R.drawable.icon_delivery_dining);
            }
        }
    }

    private void setReview(String isRate, String typeUser) {
        LinearLayout layoutReview = findViewById(R.id.layout_Review);
        TextView titleReview = findViewById(R.id.textView_TitleReview);
        TextView detailReview = findViewById(R.id.textView_DetailReview);
        ImageView imageReview = findViewById(R.id.imageView_Review);

        if (typeUser.equals("KH")) {
            if (isRate.equals("true")) {
                titleReview.setText("Đơn hàng đã được đánh giá!");
                detailReview.setText("Cảm ơn bạn đã đánh giá. Chúc bạn có một trải nghiệm tuyệt vời với sản phẩm của chúng tôi.");
                layoutReview.setBackgroundColor(Color.parseColor("#26AB9A"));
                imageReview.setImageResource(R.drawable.star_check_icon);
            } else {
                titleReview.setText("Đơn hàng chưa được đánh giá!");
                detailReview.setText("Cảm ơn bạn đã mua sản phẩm. Bạn hãy sớm đánh giá sản phẩm để chúng tôi biết trải nghiệm của bạn về sản phẩm nhé.");
                layoutReview.setBackgroundColor(Color.parseColor("#F44336"));
                imageReview.setImageResource(R.drawable.star_crossed_icon);
            }
        } else {
            if (isRate.equals("true")) {
                titleReview.setText("Đơn hàng đã được đánh giá!");
                detailReview.setText("Khách hàng đã đánh giá sản phẩm này. Xem chi tiết đánh giá thông qua nút đánh giá của đơn hàng.");
                layoutReview.setBackgroundColor(Color.parseColor("#26AB9A"));
                imageReview.setImageResource(R.drawable.star_check_icon);
            } else {
                titleReview.setText("Đơn hàng chưa được đánh giá!");
                detailReview.setText("Khách hàng chưa đánh giá sản phẩm này. Xem chi tiết đánh giá thông qua nút đánh giá của đơn hàng.");
                layoutReview.setBackgroundColor(Color.parseColor("#F44336"));
                imageReview.setImageResource(R.drawable.star_crossed_icon);
            }
        }
    }

    private void setNhanVien() {
        ImageView ava = findViewById(R.id.imageView_Avatar);
        TextView ten = findViewById(R.id.textView_TenUser);
        TextView email = findViewById(R.id.textView_Email);

        ChangeType changeType = new ChangeType();
        NhanVien nhanVien = null;
        NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
        if (donHang != null) {
            ArrayList<NhanVien> list = nhanVienDAO.selectNhanVien(null, "maNV=?", new String[]{donHang.getMaNV()}, null);
            if (list.size() > 0) {
                nhanVien = list.get(0);
            }
        }
        if (nhanVien != null) {
            ava.setImageBitmap(changeType.byteToBitmap(nhanVien.getAvatar()));
            ten.setText(changeType.fullNameNhanVien(nhanVien));
            email.setText(nhanVien.getEmail());
        }
    }

    private void setLaptopView() {
        ImageView imageLaptop = findViewById(R.id.imageView_Laptop);
        TextView nameLap = findViewById(R.id.textView_TenLaptop);
        TextView soLuong = findViewById(R.id.textView_Soluong);
        TextView thanhTienTV = findViewById(R.id.textView_Total);
        TextView giamTienTV = findViewById(R.id.textView_GiamGia);
        TextView giaTienTV = findViewById(R.id.textView_TienHang);
        TextView nameKH = findViewById(R.id.textView_TenKH);
        TextView phone = findViewById(R.id.textView_SDT);
        TextView diaChi = findViewById(R.id.textView_DiaChi);
        TextView pttt = findViewById(R.id.textView_PTTT);
        TextView maDH = findViewById(R.id.textView_MaDH);
        TextView date = findViewById(R.id.textView_Date);
        Button hoanThanh = findViewById(R.id.button_HoanThanh);
        LinearLayout linearLaptop = findViewById(R.id.linearLaptop);

        Laptop laptop = new Laptop("No Data", "No Data", "No Data", "No Data", "0", 0, 0, new byte[]{});
        Log.d(TAG, "setRow: DonHang: " + donHang.toString());
        LaptopDAO laptopDAO = new LaptopDAO(context);
        ArrayList<Laptop> listLap = laptopDAO.selectLaptop(null, null, null, null);

        for (int i = 0; i < listLap.size(); i++) {
            Laptop getLap = listLap.get(i);
            if (donHang.getMaLaptop().equals(getLap.getMaLaptop())) {
                laptop = getLap;
            }
        }

        KhachHang khachHang = new KhachHang("No Data", "No Data", "No Data", "No Data",
                "No Data", "No Data", "No Data", "No Data", "No Data", new byte[]{});
        Log.d(TAG, "setRow: KhachHang: " + khachHang.toString());
        KhachHangDAO khachHangDAO = new KhachHangDAO(context);
        ArrayList<KhachHang> listKH = khachHangDAO.selectKhachHang(null, null, null, null);

        for (int i = 0; i < listKH.size(); i++) {
            KhachHang getKH = listKH.get(i);
            if (donHang.getMaKH().equals(getKH.getMaKH())) {
                khachHang = getKH;
            }
        }

        Voucher voucher = null;
        ChangeType changeType = new ChangeType();

        VoucherDAO voucherDAO = new VoucherDAO(context);
        ArrayList<Voucher> listV = voucherDAO.selectVoucher(null, "maVoucher=?", new String[]{donHang.getMaVoucher()}, null);
        if (listV.size() > 0) {
            voucher = listV.get(0);
        }

        int giaTien;
        if (laptop.getGiaTien().length() < 12) {
            giaTien = changeType.stringMoneyToInt(laptop.getGiaTien()) / 1000;
        } else {
            giaTien = changeType.stringMoneyToInt(laptop.getGiaTien());
        }
        int soTien = giaTien * donHang.getSoLuong();
        if (voucher != null) {
            int sale = changeType.voucherToInt(voucher.getGiamGia());
            int giamTien = (soTien * sale) / 100;
            giamTienTV.setText("-" + changeType.stringToStringMoney(giamTien + "000"));
        } else {
            giamTienTV.setText("-" + changeType.stringToStringMoney("0"));
        }


        Bitmap anhLap = changeType.byteToBitmap(laptop.getAnhLaptop());
        imageLaptop.setImageBitmap(anhLap);
        nameLap.setText(laptop.getTenLaptop());
        thanhTienTV.setText(donHang.getThanhTien());
        giaTienTV.setText(changeType.stringToStringMoney(soTien + "000"));
        soLuong.setText(String.valueOf(donHang.getSoLuong()));
        nameKH.setText(changeType.fullNameKhachHang(khachHang));
        phone.setText(khachHang.getPhone());
        diaChi.setText(donHang.getDiaChi());
        pttt.setText(donHang.getLoaiThanhToan());
        maDH.setText(donHang.getMaDH());
        date.setText(donHang.getNgayMua());

        hoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Laptop finalLaptop = laptop;
        linearLaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Info_Laptop_Activity.class);
                final Bundle bundle = new Bundle();
                bundle.putBinder("laptop", finalLaptop);
                Log.d(TAG, "onBindViewHolder: Laptop: " + finalLaptop.toString());
                intent.putExtras(bundle);
                intent.putExtra("openFrom", "other");
                context.startActivity(intent);
            }
        });

        if (donHang.getTrangThai().equals("Hoàn thành")) {
            setReview(donHang.getIsDanhGia(), typeUser);
        } else {
            setTrangThai(donHang.getTrangThai(), typeUser);
        }
    }

}