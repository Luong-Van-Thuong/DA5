package com.example.doan5_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    Button btnDangNhap, btnDangKy, btnNext;
    ObjectTaiKhoan objectTaiKhoan;
    SqlTaiKhoan sqlTaiKhoan;
    EditText edtTaiKhoan, edtMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setID();
        setOneClickBtnDangKy();
        setOneClickNext();

    }

    private void setOneClickNext() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                startActivity(intent);
            }
        });
    }

    private void setOneClickBtnDangKy() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectTaiKhoan = new ObjectTaiKhoan(-1, edtTaiKhoan.getText().toString(), edtMatKhau.getText().toString());
                sqlTaiKhoan = new SqlTaiKhoan(MainActivity2.this);
                sqlTaiKhoan.addTaiKhoan(objectTaiKhoan);
                Toast.makeText(MainActivity2.this, "Thêm dữ liệu thành công", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setID() {
        btnDangKy = findViewById(R.id.btnDangKy);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnNext = findViewById(R.id.btnNext);
    }
}