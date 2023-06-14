package com.nhom5.quanlylaptop.NAV_Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.DAO.UseVoucherDAO;
import com.nhom5.quanlylaptop.DAO.VoucherDAO;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.Entity.UseVoucher;
import com.nhom5.quanlylaptop.Entity.Voucher;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class QL_Voucher_Adapter extends RecyclerView.Adapter<QL_Voucher_Adapter.AuthorViewHolder> {

    Context context;
    int posVou;
    ArrayList<Voucher> listVou;
    VoucherDAO voucherDAO;
    String TAG = "QL_Voucher_Adapter_____";
    TextInputLayout textInput_Name, textInput_GiamGia, textInput_NBD, textInput_NKT;

    public QL_Voucher_Adapter(ArrayList<Voucher> listVou, Context context) {
        this.listVou = listVou;
        this.context = context;
        voucherDAO = new VoucherDAO(context);
    }

    @NonNull
    @Override
    public QL_Voucher_Adapter.AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup vGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_nva_voucher, vGroup, false);
        return new QL_Voucher_Adapter.AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QL_Voucher_Adapter.AuthorViewHolder author, @SuppressLint("RecyclerView") final int pos) {
        Voucher voucher = setRow(pos, author);

        author.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, voucher.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        author.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosVou(pos);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listVou.size();
    }

    public class AuthorViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView name, date, ma, sale;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_TenVoucher);
            date = itemView.findViewById(R.id.textView_Date_Voucher);
            ma = itemView.findViewById(R.id.textView_MaVoucher);
            sale = itemView.findViewById(R.id.textView_GiamGia);
            itemView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem edit = menu.add(Menu.NONE, R.id.item_CapNhat, Menu.NONE, "Cập nhật");
            MenuItem delete = menu.add(Menu.NONE, R.id.item_Xoa, Menu.NONE, "Xóa");
            edit.setOnMenuItemClickListener(onEditMenu);
            delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Voucher voucher = listVou.get(getPosVou());

                switch (item.getItemId()) {
                    case R.id.item_Xoa:
                        voucherDAO.deleteVoucher(voucher);
                        listVou.remove(getPosVou());
                        UseVoucherDAO useVoucherDAO = new UseVoucherDAO(context);
                        useVoucherDAO.deleteUseVoucher(voucher.getMaVoucher());
                        notifyDataSetChanged();
                        break;
                    case R.id.item_CapNhat:
                        openDialogUpdate(voucher);
                        break;
                }

                return true;
            }
        };
    }

    public Voucher setRow(int pos, @NonNull QL_Voucher_Adapter.AuthorViewHolder author) {
        Log.d(TAG, "setRow: " + pos);
        Voucher voucher = listVou.get(pos);

        author.sale.setText("Giảm giá\n" + voucher.getGiamGia());
        author.name.setText(voucher.getTenVoucher());
        if (voucher.getNgayBD().equals(voucher.getNgayKT())) {
            author.date.setText("Duy nhất trong\n" + voucher.getNgayBD());
        } else {
            author.date.setText("Từ  " + voucher.getNgayBD() + "\nđến " + voucher.getNgayKT());
        }
        return voucher;
    }

    @Override
    public void onViewRecycled(@NonNull QL_Voucher_Adapter.AuthorViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    private void openDialogUpdate(Voucher voucher) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inft = ((Activity) context).getLayoutInflater();
        View view = inft.inflate(R.layout.dialog_add_edit_voucher, null);

        TextView title = view.findViewById(R.id.textView_Title_Dialog);
        textInput_Name = view.findViewById(R.id.textInput_Name);
        textInput_GiamGia = view.findViewById(R.id.textInput_GiamGia);
        textInput_NBD = view.findViewById(R.id.textInput_NBD);
        textInput_NKT = view.findViewById(R.id.textInput_NKT);
        TextView onclick_NBD = view.findViewById(R.id.onlick_NBD);
        TextView onclick_NKT = view.findViewById(R.id.onlick_NKT);
        AppCompatButton button = view.findViewById(R.id.button_Dialog);
        ChangeType changeType = new ChangeType();

        title.setText("Cập nhật Voucher");
        textInput_Name.getEditText().setText(voucher.getTenVoucher());
        textInput_GiamGia.getEditText().setText(voucher.getGiamGia());
        textInput_NBD.getEditText().setText(changeType.norDateToSqlDate(voucher.getNgayBD()));
        textInput_NKT.getEditText().setText(changeType.norDateToSqlDate(voucher.getNgayKT()));
        button.setText("Cập nhật");

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        onclickGetTime(textInput_NBD, onclick_NBD);
        onclickGetTime(textInput_NKT, onclick_NKT);

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                String tenVou = changeType.deleteSpaceText(textInput_Name.getEditText().getText().toString());
                String giamGia = changeType.deleteSpaceText(textInput_GiamGia.getEditText().getText().toString());
                String nbd = textInput_NBD.getEditText().getText().toString();
                String nkt = textInput_NKT.getEditText().getText().toString();
                if (checkInputVoucher() == 1) {
                    voucher.setTenVoucher(tenVou);
                    voucher.setGiamGia(giamGia);
                    voucher.setNgayBD(nbd);
                    voucher.setNgayKT(nkt);

                    voucherDAO.updateVoucher(voucher);
                    listVou.set(getPosVou(), voucher);
                    dialog.dismiss();
                    notifyDataSetChanged();
                }
            }
        });

    }

    public int getPosVou() {
        return posVou;
    }

    public void setPosVou(int posVou) {
        this.posVou = posVou;
    }

    private void onclickGetTime(TextInputLayout textInputLayout, TextView textView) {
        Calendar calendar = Calendar.getInstance();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month += 1;
                        String getDay;
                        String getMonth;
                        if (day < 10) {
                            getDay = "0" + day;
                        } else {
                            getDay = String.valueOf(day);
                        }
                        if (month < 10) {
                            getMonth = "0" + month;
                        } else {
                            getMonth = String.valueOf(month);
                        }
                        textInputLayout.getEditText().setText(year + "-" + getMonth + "-" + getDay);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    private int checkInputVoucher() {
        int check = 1;
        if (textInput_Name.getEditText().getText().toString().isEmpty()) {
            textInput_Name.setError("Mã Voucher không được bỏ trống!");
            textInput_Name.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_Name.setError("");
            textInput_Name.setErrorEnabled(false);
        }

        if (textInput_GiamGia.getEditText().getText().toString().isEmpty()) {
            textInput_GiamGia.setError("Ưu đãi không được bỏ trống!");
            textInput_GiamGia.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_GiamGia.setError("");
            textInput_GiamGia.setErrorEnabled(false);
        }

        if (textInput_NBD.getEditText().getText().toString().isEmpty()) {
            textInput_NBD.setError("Ngày bắt đầu phải được chọn!");
            textInput_NBD.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_NBD.setError("");
            textInput_NBD.setErrorEnabled(false);
        }

        if (textInput_NKT.getEditText().getText().toString().isEmpty()) {
            textInput_NKT.setError("Ngày kết thúc phải được chọn!");
            textInput_NKT.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_NKT.setError("");
            textInput_NKT.setErrorEnabled(false);
        }
        return check;
    }
}