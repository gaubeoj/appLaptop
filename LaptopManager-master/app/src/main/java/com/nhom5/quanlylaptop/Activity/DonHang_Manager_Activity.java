package com.nhom5.quanlylaptop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.nhom5.quanlylaptop.DAO.DonHangDAO;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.DAO.ThongBaoDAO;
import com.nhom5.quanlylaptop.DAO.VoucherDAO;
import com.nhom5.quanlylaptop.Entity.DonHang;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.Entity.ThongBao;
import com.nhom5.quanlylaptop.Entity.Voucher;
import com.nhom5.quanlylaptop.NAV_Adapter.DH_Manager_Adapter;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DonHang_Manager_Activity extends AppCompatActivity {

    TextInputLayout tilDate, tilNV, tilKH, tilDC, tilLap, tilSL, tilVou, tilTT;
    Spinner hangLapSpinner;
    AppCompatButton addDHButton;
    Context context = this;
    DonHangDAO donHangDAO;
    LaptopDAO laptopDAO;
    KhachHangDAO khachHangDAO;
    NhanVienDAO nhanVienDAO;
    VoucherDAO voucherDAO;
    String roleUser, hangLap;
    NhanVien nhanVien;
    KhachHang khachHang;
    Laptop laptop;
    Voucher voucher;
    ChangeType changeType = new ChangeType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang_manager);
        donHangDAO = new DonHangDAO(context);
        laptopDAO = new LaptopDAO(context);
        khachHangDAO = new KhachHangDAO(context);
        nhanVienDAO = new NhanVienDAO(context);
        voucherDAO = new VoucherDAO(context);

        getUser();
        findViewId();
        setUpListDialog();
        useToolbar();
        Date currentTime = Calendar.getInstance().getTime();
        String dateForm = new SimpleDateFormat("dd/MM/yyyy").format(currentTime);
        tilDate.getEditText().setText(dateForm);
    }

    private void findViewId() {
        tilDate = findViewById(R.id.textInput_Date);
        tilNV = findViewById(R.id.textInput_MaNV);
        tilKH = findViewById(R.id.textInput_MaKH);
        tilDC = findViewById(R.id.textInput_DiaChi);
        tilLap = findViewById(R.id.textInput_MaLaptop);
        tilSL = findViewById(R.id.textInput_SoLuong);
        tilVou = findViewById(R.id.textInput_MaVoucher);
        tilTT = findViewById(R.id.textInput_Total);
        hangLapSpinner = findViewById(R.id.spinner_HangLaptop);
        addDHButton = findViewById(R.id.button_AddDH);

        addDHButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonHang donHang = checkInputDonHang();
                if (donHang != null) {
                    int check = donHangDAO.insertDonHang(donHang);
                    if (check == 1) {
                        Toast.makeText(context, "Thêm đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                        addThongBao(donHang);
                        finish();
                    }
                    if (check == -1) {
                        Toast.makeText(context, "Thêm đơn hàng thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void addThongBao(DonHang donHang) {
        Date currentTime = Calendar.getInstance().getTime();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
        ThongBaoDAO thongBaoDAO = new ThongBaoDAO(context);

        ArrayList<NhanVien> listNV = nhanVienDAO.selectNhanVien(null, "maNV=?", new String[]{donHang.getMaNV()}, null);
        ArrayList<KhachHang> listKH = khachHangDAO.selectKhachHang(null, "maKH=?", new String[]{donHang.getMaKH()}, null);
        ArrayList<Laptop> listLap = laptopDAO.selectLaptop(null, "maLaptop=?", new String[]{donHang.getMaLaptop()}, null);


        if (listNV.size() > 0 && listKH.size() > 0 && listLap.size() > 0) {
            NhanVien getNV = listNV.get(0);
            KhachHang getKH = listKH.get(0);
            Laptop getLap = listLap.get(0);
            ThongBao thongBaoKH = new ThongBao("TB", donHang.getMaKH(), "Quản lý đơn hàng",
                    " Bạn đã được tạo một đơn hàng mới bởi Nhân viên " + changeType.fullNameNhanVien(getNV) + " :\n Đơn hàng " + getLap.getTenLaptop() + "\n Tổng tiền:  " + donHang.getThanhTien(), date);
            thongBaoDAO.insertThongBao(thongBaoKH, "kh");
            ThongBao thongBaoNV = new ThongBao("TB", donHang.getMaNV(), "Quản lý đơn hàng",
                    " Bạn đã tạo một đơn hàng mới cho Khách hàng " + changeType.fullNameKhachHang(getKH) + " :\n Đơn hàng " + getLap.getTenLaptop() + "\n Tổng tiền:  " + donHang.getThanhTien(), date);
            thongBaoDAO.insertThongBao(thongBaoNV, "nv");
            ThongBao thongBaoAD = new ThongBao("TB", "admin", "Quản lý đơn hàng",
                    " Nhân viên " + changeType.fullNameNhanVien(getNV) + " đã tạo một đơn hàng mới cho Khách hàng " + changeType.fullNameKhachHang(getKH) + "\n Đơn hàng " + getLap.getTenLaptop() + "\n Tổng tiền:  " + donHang.getThanhTien(), date);
            thongBaoDAO.insertThongBao(thongBaoAD, "ad");
        }
    }

    private void getUser() {
        SharedPreferences pref = context.getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            roleUser = "null";
        } else {
            roleUser = pref.getString("who", "");
        }
    }

    private DonHang checkInputDonHang() {
        Date currentTime = Calendar.getInstance().getTime();
        String dateSQL = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);

        String dc = tilDC.getEditText().getText().toString();
        String tt = tilTT.getEditText().getText().toString();
        String lap = "", kh = "", nv = "", vou = "";
        int sl;
        if (laptop != null) {
            lap = laptop.getTenLaptop();
        }
        if (khachHang != null) {
            kh = changeType.fullNameKhachHang(khachHang);
        }
        if (nhanVien != null) {
            nv = changeType.fullNameNhanVien(nhanVien);
        }
        if (voucher != null) {
            vou = voucher.getTenVoucher();
        }

        if (nv.equals("")) {
            tilNV.setErrorEnabled(true);
            tilNV.setError("Nhân viên không được bỏ trống!");
            return null;
        } else {
            tilNV.setErrorEnabled(false);
            tilNV.setError("");
        }

        if (kh.equals("")) {
            tilKH.setErrorEnabled(true);
            tilKH.setError("Khách hàng không được bỏ trống!");
            return null;
        } else {
            tilKH.setErrorEnabled(false);
            tilKH.setError("");
        }

        if (dc.equals("")) {
            tilDC.setErrorEnabled(true);
            tilDC.setError("Địa chỉ không được bỏ trống!");
            return null;
        } else {
            tilDC.setErrorEnabled(false);
            tilDC.setError("");
        }

        if (lap.equals("")) {
            tilLap.setErrorEnabled(true);
            tilLap.setError("Laptop không được bỏ trống!");
            return null;
        } else {
            tilLap.setErrorEnabled(false);
            tilLap.setError("");
        }

        try {
            sl = Integer.parseInt(tilSL.getEditText().getText().toString());
            tilSL.setErrorEnabled(false);
            tilSL.setError("");
        } catch (Exception e) {
            e.printStackTrace();
            tilSL.setErrorEnabled(true);
            tilSL.setError("Số lượng phải là số!");
            return null;
        }

        if (sl == 0) {
            tilSL.setErrorEnabled(true);
            tilSL.setError("Số lượng laptop phải lớn hơn 0");
            return null;
        } else {
            tilSL.setErrorEnabled(false);
            tilSL.setError("");
        }

        if (laptop != null) {
            if (sl > laptop.getSoLuong()) {
                tilSL.setErrorEnabled(true);
                tilSL.setError("Số lượng laptop còn lại: " + laptop.getSoLuong());
                return null;
            } else {
                tilSL.setErrorEnabled(false);
                tilSL.setError("");
            }
        }

        if (vou.equals("")) {
            tilVou.setErrorEnabled(true);
            tilVou.setError("Voucher không được bỏ trống!");
            return null;
        } else {
            tilVou.setErrorEnabled(false);
            tilVou.setError("");
        }

        if (tt.equals("")) {
            tilTT.setErrorEnabled(true);
            tilTT.setError("Thành tiền không được bỏ trống!");
            return null;
        } else {
            tilTT.setErrorEnabled(false);
            tilTT.setError("");
        }

        if (laptop != null) {
            laptop.setSoLuong(laptop.getSoLuong() - sl);
            laptopDAO.updateLaptop(laptop);
        }

        return new DonHang("", nhanVien.getMaNV(), khachHang.getMaKH(), laptop.getMaLaptop(),
                voucher.getMaVoucher(), "No Data", dc, dateSQL, "Thanh toán tại cửa hàng",
                "Hoàn thành", "false", changeType.stringToStringMoney(tt), sl);
    }

    private void setUpListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inft = ((Activity) context).getLayoutInflater();
        View view = inft.inflate(R.layout.dialog_list, null);
        TextView title = view.findViewById(R.id.textView_Title_Dialog);
        ListView listView = view.findViewById(R.id.listView_Item_DH);
        builder.setView(view);
        Dialog dialog = builder.create();

        tilNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Danh sách Nhân viên");
                dialog.show();
                ArrayList<NhanVien> list = nhanVienDAO.selectNhanVien(null, null, null, null);
                DH_Manager_Adapter adapter = new DH_Manager_Adapter(null, list, null, null);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        nhanVien = list.get(position);
                        tilNV.getEditText().setText(changeType.fullNameNhanVien(nhanVien));
                        dialog.dismiss();
                    }
                });
            }
        });

        tilNV.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Danh sách Nhân viên");
                dialog.show();
                ArrayList<NhanVien> list = nhanVienDAO.selectNhanVien(null, "roleNV=?", new String[]{"Bán hàng Ofline"}, null);
                DH_Manager_Adapter adapter = new DH_Manager_Adapter(null, list, null, null);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        nhanVien = list.get(position);
                        tilNV.getEditText().setText(changeType.fullNameNhanVien(nhanVien));
                        dialog.dismiss();
                    }
                });
            }
        });

        tilKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Danh sách Khách hàng");
                dialog.show();
                ArrayList<KhachHang> list = khachHangDAO.selectKhachHang(null, null, null, null);
                DH_Manager_Adapter adapter = new DH_Manager_Adapter(null, null, list, null);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        khachHang = list.get(position);
                        tilKH.getEditText().setText(changeType.fullNameKhachHang(khachHang));
                        dialog.dismiss();
                    }
                });
            }
        });

        tilKH.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Danh sách Khách hàng");
                dialog.show();
                ArrayList<KhachHang> list = khachHangDAO.selectKhachHang(null, null, null, null);
                DH_Manager_Adapter adapter = new DH_Manager_Adapter(null, null, list, null);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        khachHang = list.get(position);
                        tilKH.getEditText().setText(changeType.fullNameKhachHang(khachHang));
                        dialog.dismiss();
                    }
                });
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.hang_laptop_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hangLapSpinner.setAdapter(adapter);
        hangLapSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hangLap = hangLapSpinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tilLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hangLap != null){
                    title.setText("Danh sách Laptop " + hangLap);
                    dialog.show();
                    ArrayList<Laptop> list = laptopDAO.selectLaptop(null, "maHangLap=?", new String[]{"L" + hangLap}, null);
                    DH_Manager_Adapter adapter2 = new DH_Manager_Adapter(list, null, null, null);
                    listView.setAdapter(adapter2);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            laptop = list.get(position);
                            tilLap.getEditText().setText(laptop.getTenLaptop());
                            hangLapSpinner.setVisibility(View.GONE);
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        tilLap.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hangLap != null){
                    title.setText("Danh sách Laptop " + hangLap);
                    dialog.show();
                    ArrayList<Laptop> list = laptopDAO.selectLaptop(null, "maHangLap=?", new String[]{"L" + hangLap}, null);
                    DH_Manager_Adapter adapter2 = new DH_Manager_Adapter(list, null, null, null);
                    listView.setAdapter(adapter2);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            laptop = list.get(position);
                            tilLap.getEditText().setText(laptop.getTenLaptop());
                            hangLapSpinner.setVisibility(View.GONE);
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        tilVou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Danh sách Voucher");
                dialog.show();
                ArrayList<Voucher> list = voucherDAO.selectVoucher(null, null, null, null);
                DH_Manager_Adapter adapter = new DH_Manager_Adapter(null, null, null, list);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        voucher = list.get(position);
                        tilVou.getEditText().setText(voucher.getTenVoucher() + " : Sale " + voucher.getGiamGia());
                        dialog.dismiss();
                    }
                });
            }
        });

        tilVou.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Danh sách Voucher");
                dialog.show();
                ArrayList<Voucher> list = voucherDAO.selectVoucher(null, null, null, null);
                DH_Manager_Adapter adapter = new DH_Manager_Adapter(null, null, null, list);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        voucher = list.get(position);
                        tilVou.getEditText().setText(voucher.getTenVoucher() + " : Sale " + voucher.getGiamGia());
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText("Thêm Đơn hàng");
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}