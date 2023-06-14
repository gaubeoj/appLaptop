package com.nhom5.quanlylaptop.Support;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.nhom5.quanlylaptop.DAO.DonHangDAO;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.DAO.UseVoucherDAO;
import com.nhom5.quanlylaptop.DAO.VoucherDAO;
import com.nhom5.quanlylaptop.Entity.DonHang;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.Entity.UseVoucher;
import com.nhom5.quanlylaptop.Entity.Voucher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GetData {
    Context context;
    NhanVienDAO nhanVienDAO;
    DonHangDAO donHangDAO;
    LaptopDAO laptopDAO;
    UseVoucherDAO useVoucherDAO;
    VoucherDAO voucherDAO;
    KhachHangDAO khachHangDAO;
    ChangeType changeType = new ChangeType();
    String TAG = "AddData_____";

    public GetData(Context context) {
        this.context = context;
        nhanVienDAO = new NhanVienDAO(context);
        donHangDAO = new DonHangDAO(context);
        laptopDAO = new LaptopDAO(context);
        useVoucherDAO = new UseVoucherDAO(context);
        khachHangDAO = new KhachHangDAO(context);
        voucherDAO = new VoucherDAO(context);
    }

    public void addDataUseVoucher() {
        ArrayList<KhachHang> listK = khachHangDAO.selectKhachHang(null, null, null, null);
        ArrayList<Voucher> listV = voucherDAO.selectVoucher(null, null, null, null);
        Voucher vou = listV.get(listV.size() - 1);
        if (listK.size() > 0) {
            for (KhachHang kh : listK) {
                useVoucherDAO.insertUseVoucher(new UseVoucher("", vou.getMaVoucher(), kh.getMaKH(), "false"));
            }
        }
    }

    public void addDataDoanhSo(ArrayList<NhanVien> listNV, String[] getDate) {
        if (listNV != null) {
            if (listNV.size() > 0) {
                for (NhanVien nhanVien : listNV) {
                    String[] full = {nhanVien.getMaNV(), getDate[0], getDate[1]};
                    ArrayList<DonHang> listDon = donHangDAO.selectDonHang(null, "maNV=? and ngayMua>=? and ngayMua<?", full, null);

                    int doanhSo = 0;
                    int cout = 0;
                    if (listDon != null) {
                        if (listDon.size() > 0) {
                            for (DonHang donHang : listDon) {
                                int giaTien;
                                if (donHang.getThanhTien().length() < 12) {
                                    giaTien = changeType.stringMoneyToInt(donHang.getThanhTien()) / 1000;
                                } else {
                                    giaTien = changeType.stringMoneyToInt(donHang.getThanhTien());
                                }
                                doanhSo += giaTien;
                            }
                            cout = listDon.size();
                        }
                    }

                    nhanVien.setDoanhSo(doanhSo);
                    nhanVien.setSoSP(cout);
                    nhanVienDAO.updateNhanVien(nhanVien);
                }
            }
        }
    }

    public int tinhTongKhoanThu(ArrayList<DonHang> listDon) {
        int khoanThu = 0;
        if (listDon != null) {
            if (listDon.size() > 0) {
                for (DonHang donHang : listDon) {
                    int giaTien = changeType.stringMoneyToInt(donHang.getThanhTien()) / 1000;
                    khoanThu += giaTien;
                }
            }
        }
        return khoanThu;
    }

    public int tinhTongKhoanChi(ArrayList<Laptop> listLap) {
        int khoanChi = 0;
        if (listLap != null) {
            if (listLap.size() > 0) {
                for (Laptop laptop : listLap) {
                    int giaTien = ((changeType.stringMoneyToInt(laptop.getGiaTien()) / 1000) * laptop.getSoLuong());
                    Log.d(TAG, "tinhTongKhoanChi: giaTien: " + giaTien);
                    khoanChi += giaTien;
                }
            }
        }
        Log.d(TAG, "tinhTongKhoanChi: khoanChi: " + khoanChi);
        return khoanChi;
    }

    public ArrayList get8Laptop(ArrayList<Laptop> listLap, int pos) {
        ArrayList<Laptop> list8 = new ArrayList<>();
        int size = listLap.size();
        if (size > 0) {
            if (size >= 8) {
                if ((pos + 1) * 8 <= size){
                    for (int i = (pos * 8); i < ((pos + 1) * 8); i++) {
                        list8.add(listLap.get(i));
                    }
                } else {
                    for (int i = (pos * 8); i < size; i++) {
                        list8.add(listLap.get(i));
                    }
                }
            } else {
                for (int i = 0; i < size; i++) {
                    list8.add(listLap.get(i));
                }
            }
        }
        return list8;
    }

    public Laptop getTop1DoanhThu(ArrayList<Laptop> listLap) {
        Laptop top1 = null;
        int topDoanhThu = 0;
        if (listLap != null) {
            if (listLap.size() > 0) {
                for (Laptop laptop : listLap) {
                    int doanhThu = 0;
                    ArrayList<DonHang> listDon = donHangDAO.selectDonHang(null, "maLaptop=?",
                            new String[]{laptop.getMaLaptop()}, null);
                    if (listDon != null) {
                        if (listDon.size() > 0) {
                            for (DonHang donHang : listDon) {
                                doanhThu += (changeType.stringMoneyToInt(donHang.getThanhTien()) / 1000);
                            }
                        }
                    }
                    if (doanhThu > topDoanhThu) {
                        topDoanhThu = doanhThu;
                        top1 = laptop;
                    } else {
                        top1 = laptop;
                        topDoanhThu = doanhThu;
                    }
                }
            }
        }
        return top1;
    }

    public Laptop getTop1SoLuong(ArrayList<Laptop> listLap, String sort) {
        Laptop top1 = null;
        int topSoLuong = 0;
        if (listLap != null) {
            if (listLap.size() > 0) {
                for (Laptop laptop : listLap) {
                    if (laptop.getDaBan() > topSoLuong) {
                        top1 = laptop;
                        topSoLuong = laptop.getDaBan();
                    } else if (laptop.getDaBan() == topSoLuong) {
                        topSoLuong = laptop.getDaBan();
                        if (top1 != null) {
                            if (sort.equals("desc")) {
                                //Giảm dần
                                if (changeType.stringMoneyToInt(laptop.getGiaTien()) > changeType.stringMoneyToInt(top1.getGiaTien())) {
                                    top1 = laptop;
                                }
                            }
                            if (sort.equals("asc")) {
                                //Tăng dần
                                if (changeType.stringMoneyToInt(laptop.getGiaTien()) < changeType.stringMoneyToInt(top1.getGiaTien())) {
                                    top1 = laptop;
                                }
                            }
                        } else {
                            top1 = laptop;
                        }
                    }
                }
            }
        }
        return top1;
    }

    public int tinhSoLuongNhapVe(ArrayList<Laptop> listLap) {
        int soLuong = 0;
        if (listLap != null) {
            if (listLap.size() > 0) {
                for (Laptop laptop : listLap) {
                    int nhap = laptop.getSoLuong();
                    soLuong += nhap;
                }
            }
        }
        return soLuong;
    }

    public int tinhSoLuongDaBan(ArrayList<Laptop> listLap) {
        int soLuong = 0;
        if (listLap != null) {
            if (listLap.size() > 0) {
                for (Laptop laptop : listLap) {
                    int ban = laptop.getDaBan();
                    soLuong += ban;
                }
            }
        }
        return soLuong;
    }

    public String getNowDateSQL() {
        Date currentTime = Calendar.getInstance().getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
    }

    public void addDemoLaptopDell() {
        Bitmap bm0 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/260171/dell-gaming-g15-5515-r5-p105f004dgr-291121-114930-600x600.jpg");

        Bitmap bm1 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/264370/dell-inspiron-15-3511-i3-1115g4-4gb-256gb-600x600.jpg");
        if (bm1 != null) {
            Laptop lp1 = new Laptop("1", "LDell", "Laptop Dell Inspiron 15 3511 i3 1115G4"
                    , "RAM 4GB", "12.090.000₫", 3, 0, changeType.checkByteInput(changeType.bitmapToByte(bm1)));
            laptopDAO.insertLaptop(lp1);
        }

        Bitmap bm2 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/271090/dell-gaming-alienware-m15-r6-i7-11800h-32gb-1tb-ssd-8gb-600x600.jpg");
        if (bm2 != null) {
            Laptop lp2 = new Laptop("2", "LDell", "Laptop Dell Gaming Alienware m15 R6 i7 11800H"
                    , "RAM 32GB", "61.640.000₫", 3, 0, changeType.checkByteInput(changeType.bitmapToByte(bm2)));
            laptopDAO.insertLaptop(lp2);
        }

        Bitmap bm4 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/292640/dell-xps-13-plus-9320-i7-5cg56-thumb-600x600.jpg");
        if (bm4 != null) {
            Laptop lp4 = new Laptop("4", "LDell", "Laptop Dell XPS 13 Plus 9320 i7 1260P"
                    , "RAM 16GB", "59.490.000₫", 1, 0, changeType.checkByteInput(changeType.bitmapToByte(bm4)));
            laptopDAO.insertLaptop(lp4);
        }
    }

    public void addDemoLaptopHP() {
        Bitmap bm0 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/288400/hp-victus-16-d0292tx-i5-5z9r3pa-thumb-600x600.jpg");
        if (bm0 != null) {
            Laptop lp0 = new Laptop("5", "LHP", "Laptop HP VICTUS 16 d0292TX i5 11400H"
                    , "RAM 8GB", "26.590.000₫", 6, 0, changeType.checkByteInput(changeType.bitmapToByte(bm0)));
            laptopDAO.insertLaptop(lp0);
        }

        Bitmap bm1 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/296789/hp-envy-x360-13-bf0090tu-i7-76b13pa-101122-093057-600x600.jpg");
        if (bm1 != null) {
            Laptop lp1 = new Laptop("6", "LHP", "Laptop HP Envy X360 13 bf0090TU i7 1250U"
                    , "RAM 16GB", "32.090.000₫", 2, 0, changeType.checkByteInput(changeType.bitmapToByte(bm1)));
            laptopDAO.insertLaptop(lp1);
        }

        Bitmap bm4 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/268676/hp-pavilion-x360-14-dy0171tu-i3-4y1d6pa-170322-015258-600x600.jpg");
        if (bm4 != null) {
            Laptop lp4 = new Laptop("9", "LHP", "Laptop HP Pavilion X360 14 dy0171TU i3 1125G4"
                    , "RAM 4GB", "13.690.000₫", 6, 0, changeType.checkByteInput(changeType.bitmapToByte(bm4)));
            laptopDAO.insertLaptop(lp4);
        }
    }

    public void addDemoLaptopAcer() {
        Bitmap bm1 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/283458/acer-nitro-5-tiger-an515-58-773y-i7-nhqfksv001-thumb-600x600.jpg");
        if (bm1 != null) {
            Laptop lp1 = new Laptop("11", "LAcer", "Laptop Acer Nitro 5 Tiger AN515 58 773Y i7 12700H"
                    , "RAM 8GB", "31.490.000₫", 6, 0, changeType.checkByteInput(changeType.bitmapToByte(bm1)));
            laptopDAO.insertLaptop(lp1);
        }

        Bitmap bm2 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/260058/acer-nitro-5-gaming-an515-57-720a-i7-nhqeqsv004-171121-024959-600x600.jpg");
        if (bm2 != null) {
            Laptop lp2 = new Laptop("12", "LAcer", "Laptop Acer Nitro 5 Gaming AN515 57 720A i7 11800H"
                    , "RAM 8GB", "28.490.000₫", 10, 0, changeType.checkByteInput(changeType.bitmapToByte(bm2)));
            laptopDAO.insertLaptop(lp2);
        }

        Bitmap bm4 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/273432/acer-aprise-a315-57g-32qp-i3-1005g1-4gb-256gb-2gb-mx330-010322-044114-600x600.jpg");
        if (bm4 != null) {
            Laptop lp4 = new Laptop("14", "LAcer", "Laptop Acer Aspire A315 57G 32QP i3 1005G1"
                    , "RAM 4GB", "13.190.000₫", 8, 0, changeType.checkByteInput(changeType.bitmapToByte(bm4)));
            laptopDAO.insertLaptop(lp4);
        }
    }

    public void addDemoLaptopAsus() {
        Bitmap bm1 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/274539/asus-gaming-rog-flow-z13-gz301z-i7-ld110w-160322-120057-600x600.jpg");
        if (bm1 != null) {
            Laptop lp1 = new Laptop("16", "LAsus", "Laptop Asus Gaming ROG Flow Z13 GZ301Z i7 12700H"
                    , "RAM 16GB", "48.690.000₫", 2, 0, changeType.checkByteInput(changeType.bitmapToByte(bm1)));
            laptopDAO.insertLaptop(lp1);
        }

        Bitmap bm2 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/284507/asus-rog-strix-gaming-g513r-r7-hn038w-170822-061143-600x600.jpg");
        if (bm2 != null) {
            Laptop lp2 = new Laptop("17", "LAsus", "Laptop Asus ROG Strix Gaming G513R R7 6800H"
                    , "RAM 8GB", "28.690.000₫", 5, 0, changeType.checkByteInput(changeType.bitmapToByte(bm2)));
            laptopDAO.insertLaptop(lp2);
        }

        Bitmap bm4 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/298373/asus-expertbook-b5402cba-i5-ki0353w-thumb-600x600.jpg");
        if (bm4 != null) {
            Laptop lp4 = new Laptop("19", "LAsus", "Asus ExpertBook B5402CB i5 1240P"
                    , "RAM 16GB", "26.990.000₫", 0, 0, changeType.checkByteInput(changeType.bitmapToByte(bm4)));
            laptopDAO.insertLaptop(lp4);
        }
    }

    public void addDemoLaptopMsi() {
        Bitmap bm2 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/249151/msi-gaming-ge66-raider-11uh-i7-259vn-600x600.jpg");
        if (bm2 != null) {
            Laptop lp2 = new Laptop("22", "LMSi", "Laptop MSI Gaming GE66 Raider 11UH i7 11800H"
                    , "RAM 32GB", "77.490.000₫", 5, 0, changeType.checkByteInput(changeType.bitmapToByte(bm2)));
            laptopDAO.insertLaptop(lp2);
        }

        Bitmap bm3 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/249152/msi-gaming-ge66-raider-11ug-i7-258vn-600x600.jpg");
        if (bm3 != null) {
            Laptop lp3 = new Laptop("23", "LMSi", "Laptop MSI Gaming GE66 Raider 11UG i7 11800H"
                    , "RAM 16GB", "59.490.000₫", 6, 0, changeType.checkByteInput(changeType.bitmapToByte(bm3)));
            laptopDAO.insertLaptop(lp3);
        }

        Bitmap bm4 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/274783/msi-creator-z16-a12uet-i7-036vn-200322-110544-600x600.jpg");
        if (bm4 != null) {
            Laptop lp4 = new Laptop("24", "LMSi", "Laptop MSI Creator Z16 A12UET i7 12700H"
                    , "RAM 16GB", "60.490.000₫", 5, 0, changeType.checkByteInput(changeType.bitmapToByte(bm4)));
            laptopDAO.insertLaptop(lp4);
        }
    }

    public void addDemoLaptopMac() {
        Bitmap bm1 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/253636/apple-macbook-pro-16-m1-pro-2021-10-core-cpu-600x600.jpg");
        if (bm1 != null) {
            Laptop lp1 = new Laptop("26", "LMacBook", "Laptop Apple MacBook Pro 16 M1 Pro 2021 10 core-CPU"
                    , "RAM 16GB", "66.990.000₫", 5, 0, changeType.checkByteInput(changeType.bitmapToByte(bm1)));
            laptopDAO.insertLaptop(lp1);
        }

        Bitmap bm2 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/253582/apple-macbook-pro-16-m1-max-2021-1.jpg");
        if (bm2 != null) {
            Laptop lp2 = new Laptop("27", "LMacBook", "Laptop Apple MacBook Pro 16 M1 Max 2021 10 core-CPU"
                    , "RAM 32GB", "92.990.000₫", 1, 0, changeType.checkByteInput(changeType.bitmapToByte(bm2)));
            laptopDAO.insertLaptop(lp2);
        }

        Bitmap bm4 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/231244/grey-1-org.jpg");
        if (bm4 != null) {
            Laptop lp4 = new Laptop("29", "LMacBook", "Laptop Apple MacBook Air M1 2020 8GB"
                    , "RAM 8GB", "27.490.000₫", 3, 0, changeType.checkByteInput(changeType.bitmapToByte(bm4)));
            laptopDAO.insertLaptop(lp4);
        }
    }

    private void textChange() {
        EditText editText = null;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
