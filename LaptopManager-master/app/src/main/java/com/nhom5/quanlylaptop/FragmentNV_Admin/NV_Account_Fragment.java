package com.nhom5.quanlylaptop.FragmentNV_Admin;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.nhom5.quanlylaptop.Activity.Account_Manager_Activity;
import com.nhom5.quanlylaptop.Activity.HDSD_Activity;
import com.nhom5.quanlylaptop.Activity.PickRole_Activity;
import com.nhom5.quanlylaptop.Activity.Webview_Activity;
import com.nhom5.quanlylaptop.ActivityKH.KH_DonHang_Activity;
import com.nhom5.quanlylaptop.ActivityKH.KH_Voucher_Activity;
import com.nhom5.quanlylaptop.ActivityNV_Admin.NV_DonHang_Activity;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.DAO.ThongBaoDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.Entity.ThongBao;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NV_Account_Fragment extends Fragment {

    LinearLayout cdnv, hdsd, qldh, meoBH, doiMatKhau, thietLapTaiKhoan;
    TextInputLayout til_mk, til_mkmoi, til_confirmmkmoi;
    AppCompatButton logOut;
    NhanVien nhanVien;
    ChangeType changeType = new ChangeType();
    ImageView imageView;
    TextView ten, sdt, email;
    String TAG = "KH_Account_Fragment_____";
    int REQUEST_CODE = 1;
    String currentPhotoPath;
    private static final int SELECT_PHOTO = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nv_account, container, false);
        qldh = view.findViewById(R.id.onclick_Don_Hang_Da_Ban);
        cdnv = view.findViewById(R.id.onclick_CD_NV);
        meoBH = view.findViewById(R.id.onclick_Meo_Ban_Hang);
        hdsd = view.findViewById(R.id.onclick_Huong_Dan_Su_Dung);
        doiMatKhau = view.findViewById(R.id.onclick_Doi_Mat_Khau);
        thietLapTaiKhoan = view.findViewById(R.id.onclick_Thiet_Lap_Tai_Khoan);
        imageView = view.findViewById(R.id.imageView_Avatar);
        ten = view.findViewById(R.id.textView_HoTen);
        sdt = view.findViewById(R.id.textView_SDT);
        email = view.findViewById(R.id.textView_Email);
        logOut = view.findViewById(R.id.button_LogOut);

        getUser();
        setInfoUser();
        clickQuanLyDonHang();
        clickCongDongNhanVien();
        clickMeoBanHang();
        clickHuongDanSuDung();
        clickDoiMatKhau();
        clickThietLapTaiKhoan();
        thayAvt();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getContext().getSharedPreferences("Who_Login", MODE_PRIVATE);
                if (pref != null) {
                    if (pref.getString("isLogin", "").equals("true")){
                        getActivity().finish();
                        startActivity(new Intent(getContext(), PickRole_Activity.class));
                    } else {
                        getActivity().finish();
                    }
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("isLogin", "false");
                    editor.apply();
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUser();
        setInfoUser();
    }

    private void setInfoUser() {
        if (nhanVien != null) {
            imageView.setImageBitmap(changeType.byteToBitmap(nhanVien.getAvatar()));
            ten.setText(changeType.fullNameNhanVien(nhanVien));
            sdt.setText(nhanVien.getPhone());
            email.setText(nhanVien.getEmail());
        }
    }

    private void getUser() {
        SharedPreferences pref = getContext().getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            nhanVien = null;
        } else {
            String user = pref.getString("who", "");
            NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
            ArrayList<NhanVien> list = nhanVienDAO.selectNhanVien(null, "maNV=?", new String[]{user}, null);
            if (list.size() > 0) {
                nhanVien = list.get(0);
            }
        }
    }

    private void clickQuanLyDonHang() {
        qldh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NV_DonHang_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void clickCongDongNhanVien() {
        cdnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Webview_Activity.class);
                intent.putExtra("titleWeb", "Cộng đồng nhân viên");
                intent.putExtra("urlWeb", "https://www.facebook.com/groups/500323005383352");
                startActivity(intent);
            }
        });
    }

    private void clickMeoBanHang() {
        meoBH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Webview_Activity.class);
                intent.putExtra("titleWeb", "Mẹo bán hàng");
                intent.putExtra("urlWeb", "https://www.youtube.com/watch?v=DR0K1LYZ5FQ");
                startActivity(intent);
            }
        });
    }

    private void clickHuongDanSuDung() {
        hdsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HDSD_Activity.class));
            }
        });
    }

    private void clickDoiMatKhau() {
        View view = getLayoutInflater().inflate(R.layout.dialog_doi_pass, null);
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(view);
        doiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        Button change = view.findViewById(R.id.button_Change_Pass);
        til_mk = view.findViewById(R.id.textInput_Current_Password);
        til_mkmoi = view.findViewById(R.id.textInput_New_Password);
        til_confirmmkmoi = view.findViewById(R.id.textInput_Confirm_Password);
        Button cancel = view.findViewById(R.id.button_Cancel_Dialog);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currPass = changeType.deleteSpaceText(til_mk.getEditText().getText().toString());
                String newPass = changeType.deleteSpaceText(til_mkmoi.getEditText().getText().toString());
                String firmPass = changeType.deleteSpaceText(til_confirmmkmoi.getEditText().getText().toString());

                if (checkInputPass(currPass, newPass, firmPass) == 1){
                    NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
                    nhanVien.setMatKhau(newPass);
                    int check = nhanVienDAO.updateNhanVien(nhanVien);
                    if (check == 1){
                        clearDialogChangePass(view, dialog);
                        Toast.makeText(getContext(), "Thay đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        Date currentTime = Calendar.getInstance().getTime();
                        String date = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
                        ThongBaoDAO thongBaoDAO = new ThongBaoDAO(getContext());
                        ThongBao thongBao = new ThongBao("TB", nhanVien.getMaNV(), "Thiết lập tài khoản",
                                "Bạn đã thay đổi mật khẩu.\n Đừng quên mật khẩu mới nhé!", date);
                        thongBaoDAO.insertThongBao(thongBao, "nv");
                    } else {
                        clearDialogChangePass(view, dialog);
                        Toast.makeText(getContext(), "Thay đổi mật khẩu thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDialogChangePass(view, dialog);
            }
        });
    }

    private void clearDialogChangePass(View view, BottomSheetDialog dialog){
        TextInputLayout text1 = view.findViewById(R.id.textInput_Current_Password);
        TextInputLayout text2 = view.findViewById(R.id.textInput_New_Password);
        TextInputLayout text3 = view.findViewById(R.id.textInput_Confirm_Password);
        text1.getEditText().setText("");
        text2.getEditText().setText("");
        text3.getEditText().setText("");
        text1.getEditText().clearFocus();
        text2.getEditText().clearFocus();
        text3.getEditText().clearFocus();
        dialog.dismiss();
    }

    private void clickThietLapTaiKhoan() {
        thietLapTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Account_Manager_Activity.class);
                intent.putExtra("role", "nv");
                startActivity(intent);
            }
        });
    }

    private int checkInputPass(String currPass, String newPass, String firmPass) {
        int check = 1;
        String pass = nhanVien.getMatKhau();

        if (!pass.equals(currPass)) {
            til_mk.setError("Mật khẩu cũ không đúng");
            til_mk.setErrorEnabled(true);
            check = -1;
        } else {
            til_mk.setErrorEnabled(false);
            til_mk.setError("");
        }

        if (newPass.isEmpty()) {
            til_mkmoi.setErrorEnabled(true);
            til_mkmoi.setError("Mật khẩu mới không được trống");
            check = -1;
        } else {
            til_mkmoi.setErrorEnabled(false);
            til_mkmoi.setError("");
        }

        if (!newPass.equals(firmPass)) {
            til_mkmoi.setErrorEnabled(true);
            til_confirmmkmoi.setError("Mật khẩu nhập lại không trùng khớp");
            check = -1;
        } else {
            til_mkmoi.setErrorEnabled(false);
            til_confirmmkmoi.setError("");
        }

        return check;
    }

    private void openDialog() {
        Dialog dialog = new Dialog(getContext());
        View dialogV = LayoutInflater.from(getContext()).inflate(R.layout.dialog_pick_image, null);
        dialog.setContentView(dialogV);
        dialog.show();

        Button camera = dialogV.findViewById(R.id.openCamera);
        Button gallery = dialogV.findViewById(R.id.openGallery);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "cameraOnClick: Đã cấp quyền chụp ảnh");
                    openCamera();
                    dialog.cancel();
                } else {
                    Log.d(TAG, "cameraOnClick: Chưa cấp quyền chụp ảnh");
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
                    Toast.makeText(getContext(), "Cần cấp quyền để thực hiện", Toast.LENGTH_LONG).show();
                }
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "galleryOnClick: Đã cấp quyền đọc bộ nhớ");
                    openGallery();
                    dialog.cancel();
                } else {
                    Log.d(TAG, "galleryOnClick: Chưa cấp quyền đọc bộ nhớ");
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
                    Toast.makeText(getContext(), "Cần cấp quyền để thực hiện", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void thayAvt() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = imageReturnedIntent.getData();
                InputStream imageStream = null;
                try {
                    imageStream = getContext().getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                if (bitmap != null) {
                    NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
                    nhanVien.setAvatar(changeType.checkByteInput(changeType.bitmapToByte(bitmap)));
                    nhanVienDAO.updateNhanVien(nhanVien);

                    Date currentTime = Calendar.getInstance().getTime();
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
                    ThongBaoDAO thongBaoDAO = new ThongBaoDAO(getContext());
                    ThongBao thongBao = new ThongBao("TB", nhanVien.getMaNV(), "Thiết lập tài khoản",
                            "Bạn đã thay đổi ảnh đại diện.\n Khi nào chán thì đổi ảnh mới nhé!", date);
                    thongBaoDAO.insertThongBao(thongBao, "nv");
                }
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            if (bitmap != null){
                Bitmap afterCheck = rotateBitmapInput(bitmap, currentPhotoPath);
                if (afterCheck != null){
                    NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
                    nhanVien.setAvatar(changeType.checkByteInput(changeType.bitmapToByte(afterCheck)));
                    nhanVienDAO.updateNhanVien(nhanVien);

                    Date currentTime = Calendar.getInstance().getTime();
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
                    ThongBaoDAO thongBaoDAO = new ThongBaoDAO(getContext());
                    ThongBao thongBao = new ThongBao("TB", nhanVien.getMaNV(), "Thiết lập tài khoản",
                            "Bạn đã thay đổi ảnh đại diện.\n Khi nào chán thì đổi ảnh mới nhé!", date);
                    thongBaoDAO.insertThongBao(thongBao, "nv");
                }
            }
        }
    }

    public void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.nhom5.quanlylaptop.fileprovider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    private Bitmap rotateBitmapInput(Bitmap bitmap, String photoPath) {
        ExifInterface ei = null;
        try {
            ei = new ExifInterface(photoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ei != null){
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            Bitmap rotatedBitmap;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = changeType.rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = changeType.rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = changeType.rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }
            return rotatedBitmap;
        }
        return null;
    }
}