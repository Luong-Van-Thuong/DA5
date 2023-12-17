package com.example.doan5_02;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerAdapter extends ArrayAdapter<ChamCongNhanVien> {


    Context context;
    List<ChamCongNhanVien> lsObNguoiDung;
    public CustomerAdapter( Context context,  List<ChamCongNhanVien> lsObNguoiDung) {
        super(context, 0, lsObNguoiDung);
        this.context = context;
        this.lsObNguoiDung = lsObNguoiDung;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChamCongNhanVien obccnv = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.hienthi1nguoidung, parent);

        }
        TextView txtID = convertView.findViewById(R.id.idNhanVien);
        TextView txtName = convertView.findViewById(R.id.tenNhanVien);
        TextView thoiGian = convertView.findViewById(R.id.txtTime);
        TextView ngaydilam = convertView.findViewById(R.id.ngaydilam);
        txtID.setText(String.valueOf(obccnv.getId()));
        txtName.setText(String.valueOf(obccnv.getName()));
        thoiGian.setText(String.valueOf(obccnv.getThoiGian()));
        ngaydilam.setText(obccnv.getNgayThangNam());
        return convertView;
    }
}
