package com.nhom5.quanlylaptop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom5.quanlylaptop.ActivityKH.KH_ThanhToan_Activity;
import com.nhom5.quanlylaptop.DAO.GioHangDAO;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.LaptopRateDAO;
import com.nhom5.quanlylaptop.DAO.ThongBaoDAO;
import com.nhom5.quanlylaptop.DAO.UseVoucherDAO;
import com.nhom5.quanlylaptop.Entity.GioHang;
import com.nhom5.quanlylaptop.Entity.IdData;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.Entity.LaptopRate;
import com.nhom5.quanlylaptop.Entity.ThongBao;
import com.nhom5.quanlylaptop.Entity.UseVoucher;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Info_Laptop_Activity extends AppCompatActivity {

    Context context = this;
    Laptop laptop = null;
    String TAG = "Info_Laptop_Activity_____";
    ImageView imageLaptop;
    TextView tenLaptop, giaLaptop, tsktLaptop, soLuong, ratingTV, textView_CountRating;
    ChangeType changeType = new ChangeType();
    AppCompatButton buyNow, themVaoGio;
    GioHangDAO gioHangDAO;
    ArrayList<GioHang> listGio = new ArrayList<>();
    String openFrom;
    LinearLayout layout;
    KhachHang khachHang;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_laptop);
        imageLaptop = findViewById(R.id.imageView_Laptop);
        tenLaptop = findViewById(R.id.textView_TenLaptop);
        giaLaptop = findViewById(R.id.textView_GiaTien);
        tsktLaptop = findViewById(R.id.textView_TSKT);
        soLuong = findViewById(R.id.textView_Soluong);
        buyNow = findViewById(R.id.button_Mua);
        ratingBar = findViewById(R.id.ratingBar_DanhGia);
        ratingTV = findViewById(R.id.textView_Rating);
        layout = findViewById(R.id.layoutViewer);
        textView_CountRating = findViewById(R.id.textView_CountRating);
        themVaoGio = findViewById(R.id.button_Add_To_GioHang);
        gioHangDAO = new GioHangDAO(context);

        getUser();
        getInfoLaptop();
        setInfoLaptop();
        addToCart();
        buyNowLaptop();

        if (openFrom != null) {
            if (openFrom.equals("viewer")) {
                layout.setVisibility(View.VISIBLE);
                useToolbar(openFrom);
            } else {
                layout.setVisibility(View.GONE);
                useToolbar(openFrom);
            }
        }
    }

    private void buyNowLaptop() {
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (laptop != null) {
                    if (laptop.getSoLuong() != 0) {
                        Intent intent = new Intent(context, KH_ThanhToan_Activity.class);
                        final Bundle bundle = new Bundle();
                        bundle.putBinder("laptop", laptop);
                        Log.d(TAG, "onBindViewHolder: Laptop: " + laptop.toString());
                        intent.putExtras(bundle);
                        intent.putExtra("input", "MuaNgay");
                        IdData.getInstance().setIdDC("");
                        IdData.getInstance().setIdVou("");
                        context.startActivity(intent);
                        UseVoucherDAO useVoucherDAO = new UseVoucherDAO(context);
                        ArrayList<UseVoucher> listUS = useVoucherDAO.selectUseVoucher(null, "maKH=?", new String[]{khachHang.getMaKH()}, null);
                        if (listUS.size() > 0) {
                            for (UseVoucher use : listUS) {
                                if (use.getIsUsed().equals("truen't")) {
                                    use.setIsUsed("false");
                                    useVoucherDAO.updateUseVoucher(use);
                                }
                            }
                        }
                    } else {
                        Toast.makeText(context, "Sản phẩm đang hết hàng!\nXin vui lòng đợi chúng tôi nhập sản phẩm!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Load thông tin sản phẩm lỗi!\nXin vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getUser() {
        SharedPreferences pref = getSharedPreferences("Who_Login", MODE_PRIVATE);
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

    private void addToCart() {
        if (khachHang != null) {
            if (listGio != null) {
                themVaoGio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listGio = gioHangDAO.selectGioHang(null, "maKH=?", new String[]{khachHang.getMaKH()}, null);
                        if (laptop.getSoLuong() != 0) {
                            GioHang getGio = null;
                            for (GioHang gio : listGio) {
                                if (gio.getMaLaptop().equals(laptop.getMaLaptop())) {
                                    getGio = gio;
                                }
                            }
                            if (getGio == null) {
                                GioHang gioHang = new GioHang("GH" + listGio.size(), laptop.getMaLaptop(),
                                        khachHang.getMaKH(), "2022-11-17", "No Data", 1);
                                gioHangDAO.insertGioHang(gioHang);

                                Date currentTime = Calendar.getInstance().getTime();
                                String date = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
                                ThongBaoDAO thongBaoDAO = new ThongBaoDAO(context);
                                ThongBao thongBao = new ThongBao("TB", khachHang.getMaKH(), "Quản lý giỏ hàng",
                                        " Bạn đã thêm Laptop " + laptop.getTenLaptop() + " với giá " + laptop.getGiaTien() + " vào giỏ hàng.", date);
                                thongBaoDAO.insertThongBao(thongBao, "kh");
                            } else {
                                if (getGio.getSoLuong() >= laptop.getSoLuong()) {
                                    Toast.makeText(context, "Số lượng sản phẩm trong giỏ tối đa!", Toast.LENGTH_SHORT).show();
                                } else {
                                    getGio.setSoLuong(getGio.getSoLuong() + 1);
                                    gioHangDAO.updateGioHang(getGio);
                                    Toast.makeText(context, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Sản phẩm đang hết hàng!\n Xin vui lòng đợi chúng tôi nhập sản phẩm!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    private void setInfoLaptop() {
        if (laptop != null) {
            imageLaptop.setImageBitmap(changeType.byteToBitmap(laptop.getAnhLaptop()));
            tenLaptop.setText(laptop.getTenLaptop());
            giaLaptop.setText("Giá tiền: " + laptop.getGiaTien());
            if (laptop.getThongSoKT().equals("RAM 4GB")) {
                tsktLaptop.setText(R.string.tskt_ram_4g);
            }
            if (laptop.getThongSoKT().equals("RAM 8GB")) {
                tsktLaptop.setText(R.string.tskt_ram_8g);
            }
            if (laptop.getThongSoKT().equals("RAM 16GB")) {
                tsktLaptop.setText(R.string.tskt_ram_16g);
            }
            if (laptop.getThongSoKT().equals("RAM 32GB")) {
                tsktLaptop.setText(R.string.tskt_ram_32g);
            }
            soLuong.setText("Số lượng còn lại: " + laptop.getSoLuong() + " sản phẩm");
            LaptopRateDAO laptopRateDAO = new LaptopRateDAO(context);
            ArrayList<LaptopRate> list = laptopRateDAO.selectLaptopRate(null, "maLaptop=?", new String[]{laptop.getMaLaptop()}, null);
            if (list.size() > 0) {
                float rating = 0;
                for (LaptopRate lapRate : list) {
                    rating += lapRate.getRating();
                }
                rating = rating / list.size();
                ratingBar.setRating(changeType.getRatingFloat(rating));
                ratingTV.setText(rating + "/5");
                textView_CountRating.setText(list.size() + " đánh giá:");
            } else {
                ratingBar.setRating(0f);
                ratingTV.setText("0/5");
                textView_CountRating.setText("0 đánh giá:");
            }
        }
    }

    private void getInfoLaptop() {
        Intent intent = getIntent();
        if (intent != null) {
            try {
                laptop = (Laptop) intent.getExtras().getBinder("laptop");
                openFrom = intent.getStringExtra("openFrom");
                Log.d(TAG, "getInfoLaptop: laptop: " + laptop.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void useToolbar(String openFrom) {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Navi));
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        ImageButton home = findViewById(R.id.imageButton_Home);
        ImageButton noti = findViewById(R.id.imageButton_Notifi);
        ImageButton gio = findViewById(R.id.imageButton_GioHang);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
                if (pref != null) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("what", "none");
                    editor.apply();
                }
                finish();
            }
        });

        if (openFrom != null) {
            if (openFrom.equals("other")) {
                gio.setVisibility(View.GONE);
                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
                        if (pref != null) {
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("what", "home");
                            editor.apply();
                        }
                        finish();
                    }
                });
                noti.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
                        if (pref != null) {
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("what", "noti");
                            editor.apply();
                        }
                        finish();
                    }
                });
            } else {
                gio.setVisibility(View.VISIBLE);
                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
                        if (pref != null) {
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("what", "home");
                            editor.apply();
                        }
                        finish();
                    }
                });
                noti.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
                        if (pref != null) {
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("what", "noti");
                            editor.apply();
                        }
                        finish();
                    }
                });
                gio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
                        if (pref != null) {
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("what", "gio");
                            editor.apply();
                        }
                        finish();
                    }
                });
            }
        }
    }

}