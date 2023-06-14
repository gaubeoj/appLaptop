package com.nhom5.quanlylaptop.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom5.quanlylaptop.DAO.DiaChiDAO;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.Entity.DiaChi;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.R;

import java.util.ArrayList;

public class DiaChi_Manager_Activity extends AppCompatActivity {

    Context context = this;
    EditText editTextHoTen, editTextSDT, editTextDiaChi;
    Spinner spinnerThanhPho, spinnerQuanHuyen, spinnerXaPhuong;
    AppCompatButton buttonFinish;
    KhachHang khachHang;
    DiaChiDAO diaChiDAO;
    String maDC;
    int type = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi_manager);
        editTextHoTen = findViewById(R.id.editText_HoTen);
        editTextSDT = findViewById(R.id.editText_SDT);
        editTextDiaChi = findViewById(R.id.editText_DiaChi);

        spinnerThanhPho = findViewById(R.id.spinner_ThanhPho);
        spinnerQuanHuyen = findViewById(R.id.spinner_QuanHuyen);
        spinnerXaPhuong = findViewById(R.id.spinner_PhuongXa);
        buttonFinish = findViewById(R.id.button_Finish);

        diaChiDAO = new DiaChiDAO(context);
        getUser();
        useToolbar();
        getDataIntent();
        setSpinnerThanhPho();
        setSpinnerQuanHuyen();
        if (type != -1) {
            openInsertOrUpdateDiaChi(type);
        }
        if (type == 1){
            setInput();
        }
    }

    private void setSpinnerThanhPho() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, thanhPho);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerThanhPho.setAdapter(adapter);

        spinnerThanhPho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tp = spinnerThanhPho.getItemAtPosition(position).toString();
                setDataSpinnerQH(tp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setDataSpinnerQH(String tp) {
        switch (tp) {
            case "Cần Thơ": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, quanThuocCanTho);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerQuanHuyen.setAdapter(adapter);
                break;
            }
            case "Đà Nẵng": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, quanThuocDaNang);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerQuanHuyen.setAdapter(adapter);
                break;
            }
            case "Hà Nội": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, quanThuocHaNoi);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerQuanHuyen.setAdapter(adapter);
                break;
            }
            case "Thanh Hóa": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, quanThuocThanhHoa);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerQuanHuyen.setAdapter(adapter);
                break;
            }
            case "Ninh Bình": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, quanThuocNinhBinh);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerQuanHuyen.setAdapter(adapter);
                break;
            }
            case "Thái Bình": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, quanThuocThaiBinh);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerQuanHuyen.setAdapter(adapter);
                break;
            }
        }
    }

    private void setSpinnerQuanHuyen() {
        spinnerQuanHuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String qh = adapterView.getItemAtPosition(i).toString();
                setDataSpinnerXP(qh);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setDataSpinnerXP(String qh) {
        switch (qh) {
            case "Phong Điền": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocPhongDien);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Ninh Kiều": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocNinhKieu);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Bình Thủy": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocBinhThuy);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Ô Môn": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocOMon);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Thốt Nốt": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocThotNot);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Cờ Đỏ": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocCoDo);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            //đà nẵng
            case "Hải Châu": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocHaiChau);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Cẩm Lệ": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocCamLe);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Thanh Khê": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocThanhKhe);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Liên Chiểu": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocLienChieu);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Ngũ Hành Sơn": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocNguHanhSon);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            //hà nội
            case "Bắc Từ Liêm": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocBacTuLiem);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Nam Từ Liêm": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocNamTuLiem);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Đống Đa": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocDongDa);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Cầu Giấy": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocCauGiay);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            //thanh hóa
            case "Nga Sơn": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocNgaSon);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Nông Cống": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocNongCong);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Hà Trung": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocHaTrung);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Hậu Lộc": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocHauLoc);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Thọ Xuân": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocThoXuan);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            //ninh bình
            case "Hoa Lư": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocHoaLu);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Kim Sơn": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocKimSon);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Quan Nho": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocQuanNho);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Tam Điệp": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocTamDiep);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            //thái bình
            case "Hưng Hà": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocHungHa);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Thái Thụy": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocThaiThuy);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Đông Hưng": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocDongHung);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Kiến Xương": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocKienXuong);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
        }
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

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText("Địa chỉ nhận hàng");
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void openInsertOrUpdateDiaChi(int type) {
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoTen = editTextHoTen.getText().toString();
                String sdt = editTextSDT.getText().toString();
                String diaChiCT = editTextDiaChi.getText().toString();
                String thanhPho = (String) spinnerThanhPho.getSelectedItem();
                String quanHuyen = (String) spinnerQuanHuyen.getSelectedItem();
                String xaPhuong = (String) spinnerXaPhuong.getSelectedItem();

                if (khachHang != null) {
                    if (checkInput() == 1) {
                        if (type == 0) {
                            DiaChi diaChi = new DiaChi("DC", khachHang.getMaKH(), hoTen, sdt, diaChiCT, thanhPho, quanHuyen, xaPhuong);
                            diaChiDAO.insertDiaChi(diaChi);
                            finish();
                        } else if (type == 1) {
                            if (maDC != null) {
                                DiaChi diaChi = new DiaChi(maDC, khachHang.getMaKH(), hoTen, sdt, diaChiCT, thanhPho, quanHuyen, xaPhuong);
                                diaChiDAO.updateDiaChi(diaChi);
                                finish();
                            }
                        }
                    }
                }
            }
        });
    }

    private void setInput(){
        if (maDC != null){
            DiaChi getDC = null;
            ArrayList<DiaChi> list = diaChiDAO.selectDiaChi(null, "maDC=?", new String[]{maDC}, null);
            if (list.size() > 0){
                getDC = list.get(0);
            }
            if (getDC != null){
                editTextHoTen.setText(getDC.getTenNguoiNhan());
                editTextSDT.setText(getDC.getSDT());
                editTextDiaChi.setText(getDC.getDiaChi());
                int posTp = -1;
                for (int i = 0; i < spinnerThanhPho.getCount(); i++) {
                    if (spinnerThanhPho.getItemAtPosition(i).toString().equals(getDC.getThanhPho())){
                        posTp = i;
                    }
                }
                if (posTp != -1){
                    spinnerThanhPho.setSelection(posTp);
                    setDataSpinnerQH(getDC.getThanhPho());
                    int posQh = -1;
                    for (int i = 0; i < spinnerQuanHuyen.getCount(); i++) {
                        if (spinnerQuanHuyen.getItemAtPosition(i).toString().equals(getDC.getQuanHuyen())){
                            posQh = i;
                        }
                    }
                    setDataSpinnerXP(getDC.getQuanHuyen());
                    for (int i = 0; i < spinnerXaPhuong.getCount(); i++) {
                        if (spinnerXaPhuong.getItemAtPosition(i).toString().equals(getDC.getXaPhuong())){
                            if (posQh != -1){
                                int finalPosQh = posQh;
                                int finalPosXp = i;
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        spinnerQuanHuyen.setSelection(finalPosQh);
                                        new Handler().postDelayed(new Runnable() {
                                            public void run() {
                                                spinnerXaPhuong.setSelection(finalPosXp,true);
                                            }
                                        }, 100);
                                    }
                                }, 100);
                            }
                        }
                    }
                }
            }
        }
    }

    private int checkInput() {
        int check = 1;
        String hoTen = editTextHoTen.getText().toString();
        String sdt = editTextSDT.getText().toString();
        String diaChiCT = editTextDiaChi.getText().toString();

        if (hoTen.isEmpty()) {
            Toast.makeText(context, "Tên người nhận không bỏ trống!", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        if (sdt.isEmpty()) {
            Toast.makeText(context, "Số điện thoại không bỏ trống!", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        if (diaChiCT.isEmpty()) {
            Toast.makeText(context, "Địa chỉ người nhận không bỏ trống!", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        if (spinnerThanhPho != null) {
            if (spinnerThanhPho.getSelectedItemPosition() == 0) {
                Toast.makeText(context, "Tỉnh/ Thành phố phải được chọn!", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }

        if (spinnerQuanHuyen != null) {
            if (spinnerQuanHuyen.getSelectedItemPosition() == 0) {
                Toast.makeText(context, "Quận/ Huyện phải được chọn!", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }

        if (spinnerXaPhuong != null) {
            if (spinnerXaPhuong.getSelectedItemPosition() == 0) {
                Toast.makeText(context, "Xã/ Phường phải được chọn!", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }

        return check;
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            maDC = intent.getStringExtra("maDC");
            type = intent.getIntExtra("typeDC", -1);
        }
    }

    String[] thanhPho = {"Chọn Tỉnh/ Thành phố", "Hà Nội", "Ninh Bình", "Thái Bình", "Đà Nẵng", "Thanh Hóa", "Cần Thơ"};
    //quận / huyện
    String[] quanThuocCanTho = {"Chọn Quận/ Huyện", "Phong Điền", "Ninh Kiều", "Bình Thủy", "Ô Môn", "Thốt Nốt", "Cờ Đỏ"};
    String[] quanThuocDaNang = {"Chọn Quận/ Huyện", "Hải Châu", "Cẩm Lệ", "Thanh Khê", "Liên Chiểu", "Ngũ Hành Sơn"};
    String[] quanThuocHaNoi = {"Chọn Quận/ Huyện", "Bắc Từ Liêm", "Nam Từ Liêm", "Đống Đa", "Cầu Giấy"};
    String[] quanThuocThanhHoa = {"Chọn Quận/ Huyện", "Nga Sơn", "Nông Cống", "Hà Trung", "Hậu Lộc", "Thọ Xuân"};
    String[] quanThuocNinhBinh = {"Chọn Quận/ Huyện", "Hoa Lư", "Kim Sơn", "Quan Nho", "Tam Điệp"};
    String[] quanThuocThaiBinh = {"Chọn Quận/ Huyện", "Hưng Hà", "Thái Thụy", "Đông Hưng", "Kiến Xương"};
    //Phường/ Xã
    String[] xaThuocPhongDien = {"Chọn Phường/ Xã", "Phong Bình", "Phong Hải", "Điền Hương", "Điền Hải", "Điền Hòa", "Điền Môn", "Điền Lộc"};
    String[] xaThuocNinhKieu = {"Chọn Phường/ Xã", "An Hòa", "Thới Bình", "An Nghiệp", "An Cư", "An Hội", "Tân An", "An Phú", "Xuân Khánh", "Hưng Lợi"};
    String[] xaThuocBinhThuy = {"Chọn Phường/ Xã", "Bùi Hữu Nghĩa", "Long Hòa", "Trà An", "Thới An Đông"};
    String[] xaThuocOMon = {"Chọn Phường/ Xã", "Định Môn", "Phước Thới", "Tân Thới", "Thới Đông", "Trường Lạc", "Trường Xuân"};
    String[] xaThuocThotNot = {"Chọn Phường/ Xã", "Trung Kiên", "Thuận Hưng", "Tân Hưng", "Tân Lộc"};
    String[] xaThuocCoDo = {"Chọn Phường/ Xã", "Đông Hiệp", "Đông Thắng", "Trung Hưng", "Thạch Phú", "Trung An"};
    //
    String[] xaThuocHaiChau = {"Chọn Phường/ Xã", "Hải Châu 1", "Hải Châu 2", "Thạch Thang", "Thanh Bình", "Thuận Phước", "Nam Dương", "Phước Ninh", "Bình Hiên", "Hòa Cường Nam", "Hòa Cường Bắc"};
    String[] xaThuocCamLe = {"Chọn Phường/ Xã", "Khuê Trung", "Hòa Thọ Đông", "Hòa Thọ Tây", "Hòa An", "Hòa Phát", "Hòa Xuân"};
    String[] xaThuocThanhKhe = {"Chọn Phường/ Xã", "Vĩnh Trung", "Tân Chính", "Thạc Gián", "Chính Gián", "Tam Thuận", "Xuân Hà"};
    String[] xaThuocLienChieu = {"Chọn Phường/ Xã", "Hòa Minh", "Hòa Khánh Nam", "Hoà Khánh Bắc", "Hòa Hiệp Nam", " Hoà Hiệp Bắc"};
    String[] xaThuocNguHanhSon = {"Chọn Phường/ Xã", "Hòa Hải", "Hòa Quý", "Khuê Mỹ", "Mỹ An"};
    //
    String[] xaThuocBacTuLiem = {"Chọn Phường/ Xã", "Đức Thắng", "Đông Ngạc", "Thụy Phương", "Liên Mạc", "Thượng Cát", "Tây Tựu", "Minh Khai", "Phú Diễn", "Phúc Diễn", "Xuân Đỉnh"};
    String[] xaThuocNamTuLiem = {"Chọn Phường/ Xã", "Xuân Phương", "Phương Canh", "Ngọc Mạch", "Mễ Trì", "Tây Mỗ"};
    String[] xaThuocDongDa = {"Chọn Phường/ Xã", "Cát Linh", "Hàng Bột", "Láng Hạ", "Láng Thượng", "Kim Liên", "Khương Thượng", "Nam Đồng", "Ngã Tư Sở"};
    String[] xaThuocCauGiay = {"Chọn Phường/ Xã", "Nghĩa Đô", "Nghĩa Tân", "Mai Dịch", "Yên Hòa", "Trung Hòa"};
    //
    String[] xaThuocNgaSon = {"Chọn Phường/ Xã", "Nga An", "Nga Bạch", "Nga Điền", "Nga Giáp", "Nga Hải", "Nga Hưng", "Nga Liên", "Nga Lĩnh", "Nga Mỹ", "Nga Nhân", "Nga Phú", "Nga Thạch", "Nga Thái", "Nga Thắng", "Nga Thanh", "Nga Thành", "Nga Thiện", "Nga Thủy", "Nga Trung", "Nga Trường", "Nga Văn", "Nga Vịnh", "Nga Yên"};
    String[] xaThuocNongCong = {"Chọn Phường/ Xã", "Công Bình", "Công Chính", "Hoàng Sơn", "Minh Khôi", "Minh Nghĩa", "Minh Thọ", "Tân Khang", "Tân Phúc"};
    String[] xaThuocHaTrung = {"Chọn Phường/ Xã", "Hà Bắc", "Hà Bình", "Hà Châu", "Hà Giang", "Hà Hải", "Hà Lai", "Yến Sơn", "Lĩnh Toại", "Yên Dương"};
    String[] xaThuocHauLoc = {"Chọn Phường/ Xã", "Cầu Lộc", "Châu Lộc", "Đa Lộc", "Đại Lộc", "Đồng Lộc", "Hải Lộc", "Hoa Lộc", "Hòa Lộc", "Hưng Lộc", "Liên Lộc", "Lộc Sơn", "Lộc Tân"};
    String[] xaThuocThoXuan = {"Chọn Phường/ Xã", "Thọ Bình", "Thọ Cường", "Thọ Dân", "Thọ Diên", "Thọ Hải", "Thọ Lâm", "Thọ Lập", "Thọ Lộc", "Thọ Minh", "Thọ Ngọc"};
    //
    String[] xaThuocHoaLu = {"Chọn Phường/ Xã", "Ninh Giang", "Ninh Khang", "Ninh Mỹ", "Ninh Vân", "Ninh Thắng", "Ninh Hải", "Ninh Xuân"};
    String[] xaThuocKimSon = {"Chọn Phường/ Xã", "Phát Diệm", "Chất Bình", "Yên Mật", "Kim Đông", "Lai Thành", "Yên Lộc", "Tân Thành", "Lưu Phương"};
    String[] xaThuocQuanNho = {"Chọn Phường/ Xã", "Cúc Phương", "Đồng Phong", "Đức Long", "Gia Lâm", "Gia Sơn", "Gia Thủy", "Gia Tường", "Kỳ Phú", "Lạc Vân", "Lạng Phong"};
    String[] xaThuocTamDiep = {"Chọn Phường/ Xã", "Quang Sơn", "Yên Bình", "Yên Sơn", "Đông Sơn", "Tây Sơn", "Tân Bình"};
    //
    String[] xaThuocHungHa = {"Chọn Phường/ Xã", "Canh Tân", "Chí Hòa", "Chi Lăng", "Cộng Hòa", "Dân Chủ", "Điệp Nông", "Đoan Hùng", "Độc Lập"};
    String[] xaThuocThaiThuy = {"Chọn Phường/ Xã", "Thái Dương", "Thái Giang", "Thái Hà", "Thái Hoà", "Thái Học", "Thái Hồng", "Thái Hưng", "Thái Nguyên", "Thái Phúc", "Thái Sơn"};
    String[] xaThuocDongHung = {"Chọn Phường/ Xã", "Đông Dương", "Đông Hoàng", "Đông Á", "Đông Phong", "Đông Huy", "Đông Lĩnh", "Đông Kinh", "Đông Tân"};
    String[] xaThuocKienXuong = {"Chọn Phường/ Xã", "Quang Minh", "Bình Minh", "Thượng Hiền", " Quang Bình", " Quang Lịch", "Vũ Trung", "Vũ Quý"};

}