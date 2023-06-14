package com.nhom5.quanlylaptop.Support;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.NhanVien;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeType {
    String TAG = "ChangeType_____";

    public Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public Bitmap byteToBitmap(byte[] avatar) {
        if (avatar != null) {
            return BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
        }
        return null;
    }

    public byte[] bitmapToByte(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
            Log.d(TAG, "bitmapToByte: bitmap: " + bitmap + " byte: " + outputStream.toByteArray());
            return outputStream.toByteArray();
        }
        return null;
    }

    public byte[] checkByteInput(byte[] checkByte) {
        while (checkByte.length > 500000) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(checkByte, 0, checkByte.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.8), (int) (bitmap.getHeight() * 0.8), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
            checkByte = stream.toByteArray();
        }
        return checkByte;
    }

    public String norDateToSqlDate(String norDate) {
        String d = norDate.substring(0, 2);
        String m = norDate.substring(3, 5);
        String y = norDate.substring(6);
        return y + "-" + m + "-" + d;
    }

    public long stringToLongDate(String string) {
        long date = 0;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(string).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String longDateToString(long date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date(date));
    }

    public int stringMoneyToInt(String sMoney) {
        int money = 0;
        String afterCheck = "";
        String check = "[0-9]";
        for (int i = 0; i < sMoney.length(); i++) {
            String checkS = sMoney.substring(i, i + 1);
            if (checkS.matches(check)) {
                afterCheck += checkS;
            }
        }
        if (afterCheck.length() >= 9) {
            Log.d(TAG, "setRow: DH Thanh toán: afterCheck: >= 9");
            afterCheck = afterCheck.substring(0, afterCheck.length() - 3);
            Log.d(TAG, "setRow: DH Thanh toán: afterCheck: " + afterCheck);
            money = Integer.parseInt(afterCheck);
            Log.d(TAG, "setRow: DH Thanh toán: money: " + money);
        } else if (afterCheck.length() > 0) {
            money = Integer.parseInt(afterCheck);
        }
        Log.d(TAG, "stringMoneyToInt: Money: " + money);
        return money;
    }

    public String stringToStringMoney(String iMoney) {
        String money = "₫";
        String sMoney = String.valueOf(iMoney);
        int length = sMoney.length() - 1;
        for (int i = length; i >= 0; i--) {
            String iS = sMoney.substring(i, i + 1);
            int check = (length - i) % 3;
            if (i == length) {
                money = iS + money;
            } else {
                if (check == 0) {
                    money = iS + "." + money;
                } else {
                    money = iS + money;
                }
            }
        }
        Log.d(TAG, "stringToStringMoney: Money: " + money);
        return money;
    }

    public String[] intDateToStringDate(int m, int y) {
        String dateStart, dateEnd;
        if (m < 10) {
            if (m == 9) {
                dateStart = y + "-09-01";
                dateEnd = y + "-10-01";
            } else {
                dateStart = y + "-0" + m + "-01";
                dateEnd = y + "-0" + (m + 1) + "-01";
            }
        } else {
            if (m == 12) {
                dateStart = y + "-12-01";
                dateEnd = (y + 1) + "-01-01";
            } else {
                dateStart = y + "-" + m + "-01";
                dateEnd = y + "-" + (m + 1) + "-01";
            }
        }
        Log.d(TAG, "getDatePicker: hope: dateStart " + dateStart);
        Log.d(TAG, "getDatePicker: hope: dateEnd " + dateEnd);
        try {
            return new String[]{String.valueOf(new SimpleDateFormat("yyyy-MM-dd").parse(dateStart).getTime())
                    , String.valueOf(new SimpleDateFormat("yyyy-MM-dd").parse(dateEnd).getTime())};
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String fullNameKhachHang(KhachHang khachHang) {
        return khachHang.getHoKH() + " " + khachHang.getTenKH();
    }

    public String fullNameNhanVien(NhanVien nhanVien) {
        return nhanVien.getHoNV() + " " + nhanVien.getTenNV();
    }

    public float getRatingFloat(float rating) {
        if (rating > 0 && rating < 0.5F) {
            return 0;
        } else if (rating > 0.5F && rating < 1) {
            return 0.5F;
        } else if (rating > 1 && rating < 1.5F) {
            return 1;
        } else if (rating > 1.5F && rating < 2) {
            return 1.5F;
        } else if (rating > 2 && rating < 2.5F) {
            return 2;
        } else if (rating > 2.5F && rating < 3) {
            return 2.5F;
        } else if (rating > 3 && rating < 3.5F) {
            return 3;
        } else if (rating > 3.5F && rating < 4) {
            return 3.5F;
        } else if (rating > 4 && rating < 4.5F) {
            return 4;
        } else if (rating > 4.5F && rating < 5) {
            return 4.5F;
        }

        return rating;
    }

    public Bitmap urlToBitmap(Context context, String imageUrl) {
        Bitmap bitmap = null;
        try {
            bitmap = Glide
                    .with(context)
                    .asBitmap()
                    .load(imageUrl)
                    .submit()
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public int voucherToInt(String voucher) {
        int iVou = 0;
        String afterCheck = "";
        String check = "[0-9]";
        for (int i = 0; i < voucher.length(); i++) {
            String checkS = voucher.substring(i, i + 1);
            if (checkS.matches(check)) {
                afterCheck += checkS;
            }
        }
        if (afterCheck.length() > 0) {
            iVou = Integer.parseInt(afterCheck);
        }
        Log.d(TAG, "voucherToInt: iVou: " + iVou);
        return iVou;
    }

    public String deleteSpaceText(String user) {
        return user.replaceAll("\\s\\s+", " ").trim();
    }
}
