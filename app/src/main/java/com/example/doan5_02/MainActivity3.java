package com.example.doan5_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity3 extends AppCompatActivity {
    ImageView imgNhanVien, imgTinhLuong, imgSuaVaXoa, imgXemTongNgay_GioLam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        setUpID();
        btnImage();
    }

    private void btnImage() {
        imgNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                startActivity(intent);
            }
        });

        imgTinhLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity3.this, MainActivity5.class);
                startActivity(intent1);
            }
        });
        imgXemTongNgay_GioLam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity3.this, MainActivity6.class);
                startActivity(intent2);
            }
        });
        imgSuaVaXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MainActivity3.this, MainActivity7.class);
                startActivity(intent3);
            }
        });
    }

    private void setUpID() {
        imgNhanVien = findViewById(R.id.imgNhanVien);
        imgTinhLuong = findViewById(R.id.imgTinhLuong);
        imgSuaVaXoa = findViewById(R.id.imgUpdateAndDekete);
        imgXemTongNgay_GioLam = findViewById(R.id.imgXemNgayGio);
    }
}