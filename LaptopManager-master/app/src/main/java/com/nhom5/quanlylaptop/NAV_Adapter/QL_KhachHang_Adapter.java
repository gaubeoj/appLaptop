package com.nhom5.quanlylaptop.NAV_Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.Patterns;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class QL_KhachHang_Adapter extends RecyclerView.Adapter<QL_KhachHang_Adapter.AuthorViewHolder> {

    Context context;
    int posKH;
    ArrayList<KhachHang> listKH;
    KhachHangDAO khachHangDAO;
    TextInputLayout textInput_LastName, textInput_FirstName, textInput_GioiTinh, textInput_Email, textInput_SDT, textInput_Password;
    Spinner genderSpinner;
    ChangeType changeType = new ChangeType();
    String TAG = "QL_KhachHang_Adapter_____";
    TextView countKH;

    public QL_KhachHang_Adapter(ArrayList<KhachHang> listKH, Context context, TextView countKH) {
        this.countKH = countKH;
        this.listKH = listKH;
        this.context = context;
        khachHangDAO = new KhachHangDAO(context);
    }

    @NonNull
    @Override
    public QL_KhachHang_Adapter.AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup vGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_nva_user, vGroup, false);
        return new QL_KhachHang_Adapter.AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QL_KhachHang_Adapter.AuthorViewHolder author, @SuppressLint("RecyclerView") final int pos) {
        KhachHang khachHang = setRow(pos, author);

        author.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, khachHang.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        author.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosKH(pos);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listKH.size() == 0) {
            countKH.setText(String.valueOf(0));
        }
        return listKH.size();
    }

    public class AuthorViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView avatar;
        TextView name, gender, phone;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.imageView_Avatar);
            name = itemView.findViewById(R.id.textView_TenUser);
            gender = itemView.findViewById(R.id.textView_GioiTinh);
            phone = itemView.findViewById(R.id.textView_SDT);
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
                KhachHang khachHang = listKH.get(getPosKH());

                switch (item.getItemId()) {
                    case R.id.item_Xoa:
                        khachHangDAO.deleteKhachHang(khachHang);
                        listKH.remove(getPosKH());
                        notifyDataSetChanged();
                        break;
                    case R.id.item_CapNhat:
                        openDialogUpdate(khachHang);
                        break;
                }

                return true;
            }
        };
    }

    public KhachHang setRow(int pos, @NonNull QL_KhachHang_Adapter.AuthorViewHolder author) {
        Log.d(TAG, "setRow: " + pos);
        KhachHang kh = listKH.get(pos);
        Log.d(TAG, "setRow: KhachHang: " + kh.toString());

        ChangeType changeType = new ChangeType();
        Bitmap avatar = changeType.byteToBitmap(kh.getAvatar());

        countKH.setText(String.valueOf(listKH.size()));
        author.avatar.setImageBitmap(avatar);
        author.name.setText(kh.getHoKH() + " " + kh.getTenKH());
        author.gender.setText(kh.getGioiTinh());
        author.phone.setText(kh.getPhone());
        return kh;
    }

    @Override
    public void onViewRecycled(@NonNull AuthorViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    private void openDialogUpdate(KhachHang kh) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inft = ((Activity) context).getLayoutInflater();
        View view = inft.inflate(R.layout.dialog_add_edit_sth, null);

        TextView title = view.findViewById(R.id.textView_Title_Dialog);
        textInput_LastName = view.findViewById(R.id.textInput_LastName);
        textInput_FirstName = view.findViewById(R.id.textInput_FirstName);
        textInput_GioiTinh = view.findViewById(R.id.textInput_GioiTinh);
        textInput_Email = view.findViewById(R.id.textInput_Email);
        textInput_SDT = view.findViewById(R.id.textInput_SDT);
        textInput_Password = view.findViewById(R.id.textInput_Password);
        genderSpinner = view.findViewById(R.id.spinner_Gender);
        Button button_Dialog = view.findViewById(R.id.button_Dialog);
        ChangeType changeType = new ChangeType();

        setTextInput(kh);
        title.setText("Cập nhật Khách hàng");
        button_Dialog.setText("Cập nhật");

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        button_Dialog.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                String lastName = changeType.deleteSpaceText(textInput_LastName.getEditText().getText().toString());
                String firstName = changeType.deleteSpaceText(textInput_FirstName.getEditText().getText().toString());
                String email = changeType.deleteSpaceText(textInput_Email.getEditText().getText().toString());
                String sdt = changeType.deleteSpaceText(textInput_SDT.getEditText().getText().toString());
                String gioiTinh = genderSpinner.getSelectedItem().toString();

                if (getTextInput() == 1){
                    kh.setHoKH(lastName);
                    kh.setTenKH(firstName);
                    kh.setEmail(email);
                    kh.setPhone(sdt);
                    kh.setGioiTinh(gioiTinh);

                    khachHangDAO.updateKhachHang(kh);
                    listKH.set(getPosKH(), kh);
                    dialog.dismiss();
                    notifyDataSetChanged();
                }
            }
        });

    }

    private void setTextInput(KhachHang kh){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
        textInput_LastName.getEditText().setText(kh.getHoKH());
        textInput_FirstName.getEditText().setText(kh.getTenKH());
        textInput_GioiTinh.setStartIconDrawable(R.drawable.gender_icon);
        textInput_Email.getEditText().setText(kh.getEmail());
        textInput_SDT.getEditText().setText(kh.getPhone());
        textInput_Password.setVisibility(View.GONE);
        if (kh.getGioiTinh().equals("No Data")) {
            genderSpinner.setSelection(0);
        }
        if (kh.getGioiTinh().equals("Nam")) {
            genderSpinner.setSelection(1);
        }
        if (kh.getGioiTinh().equals("Nữ")) {
            genderSpinner.setSelection(2);
        }
        if (kh.getGioiTinh().equals("Khác")) {
            genderSpinner.setSelection(3);
        }
    }

    private int getTextInput() {
        String lastName = changeType.deleteSpaceText(textInput_LastName.getEditText().getText().toString());
        String firstName = changeType.deleteSpaceText(textInput_FirstName.getEditText().getText().toString());
        String email = changeType.deleteSpaceText(textInput_Email.getEditText().getText().toString());
        String sdt = changeType.deleteSpaceText(textInput_SDT.getEditText().getText().toString());

        int check = 1;

        if (lastName.isEmpty()) {
            textInput_LastName.setError("Họ không được bỏ trống!");
            textInput_LastName.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_LastName.setError("");
            textInput_LastName.setErrorEnabled(false);
        }

        if (firstName.isEmpty()) {
            textInput_FirstName.setError("Tên không được bỏ trống!");
            textInput_FirstName.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_FirstName.setError("");
            textInput_FirstName.setErrorEnabled(false);
        }

        if (genderSpinner.getSelectedItemPosition() == 0) {
            textInput_GioiTinh.setError("Giới tính phải được chọn!");
            textInput_GioiTinh.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_GioiTinh.setError("");
            textInput_GioiTinh.setErrorEnabled(false);
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isEmpty()) {
            textInput_Email.setError("Định dạng email không hợp lệ!");
            textInput_Email.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_Email.setError("");
            textInput_Email.setErrorEnabled(false);
        }

        if (!Patterns.PHONE.matcher(sdt).matches()) {
            textInput_SDT.setError("Định dạng số điện thoại không hợp lệ!");
            textInput_SDT.setErrorEnabled(true);
            check = -1;
        } else {
            textInput_SDT.setError("");
            textInput_SDT.setErrorEnabled(false);
        }

        return check;
    }

    public int getPosKH() {
        return posKH;
    }

    public void setPosKH(int posKH) {
        this.posKH = posKH;
    }
}